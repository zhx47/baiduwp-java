<template>
  <div>
    <div class="top">
      <!-- 头部按钮处 -->
      <div class="top-op">
<!--        <el-button-->
<!--            @click="moveFolderBatch"-->
<!--            type="warning"-->
<!--            :disabled="selectFileIdList.length === 0"-->
<!--        >-->
<!--          <span class="iconfont icon-move"></span>-->
<!--          &nbsp批量下载-->
<!--        </el-button>-->
        <div class="search-panel">
          <el-input
              clearable
              placeholder="请输入文件名搜索"
              v-model="key"
              @keyup.enter="search"
          >
            <template #suffix>
              <i class="iconfont icon-search" @click="search"></i>
            </template>
          </el-input>
        </div>
      </div>
      <!-- 导航 -->
      <Navigation ref="navigationRef" @navChange="navChange" />
    </div>

    <!-- 文件列表 -->
    <div class="file-list" v-if="tableData.list && tableData.list.length > 0">
      <Table
          ref="dataTableRef"
          :columns="columns"
          :showPagination="true"
          :dataSource="tableData"
          :fetch="loadDataList"
          :initFetch="false"
          :options="tableOptions"
          @rowSelected="rowSelected"
      >
        <!-- 文件名 -->
        <template #server_filename="{ index, row }">
          <div
              class="file-item"
              @mouseenter="showOp(row)"
              @mouseleave="cancelShowOp(row)"
          >
            <Icon :category="row.category" :isdir="row.isdir" :filename="row.server_filename"/>

            <span class="file-name" :title="row.server_filename">
              <span @click="dumpDir(row)">{{ row.server_filename }}</span>
            </span>

            <!-- 当鼠标放在当前行时显示 -->
            <span class="op">
              <template v-if="row.showOp && !row.isdir">
                <span class="iconfont icon-link" @click="fetchDownload(row)">
                  下载
                </span>
              </template>
            </span>
          </div>
        </template>

        <!-- 修改时间 -->
        <template #server_mtime="{ index, row }">
          <span v-if="row.server_mtime">{{ proxy.Utils.timestampToDateStr(row.server_mtime) }}</span>
        </template>

        <!-- 文件大小 -->
        <template #size="{ index, row }">
          <span>{{ proxy.Utils.size2Str(row.size) }}</span>
        </template>
      </Table>
    </div>
    <div class="no-data" v-else>
      <div class="no-data-inner">
      </div>
    </div>
    <Download ref="downloadRef"></Download>
  </div>
</template>

<script setup lang="ts">
import {ref, getCurrentInstance} from "vue";
import {useRoute, useRouter} from "vue-router";
import Icon from "@/components/Icon.vue";
import Navigation from "@/components/Navigation.vue";
import {download} from "@/api/AdminApi";
import Download from "@/views/main/Download.vue";

const {proxy} = getCurrentInstance();
const router = useRouter();
const route = useRoute();

const downloadRef = ref();

// 添加文件回调
const reload = () => {
  showLoading.value = false;
  key.value = ''
  loadDataList();
};
defineExpose({reload});

const api = {
  loadDataList: "/getFileList",
};

// 列表头信息
const columns = [
  {
    label: "文件名",
    prop: "server_filename",
    scopedSlots: "server_filename",
  },
  {
    label: "修改时间",
    prop: "server_mtime",
    scopedSlots: "server_mtime",
    width: 200,
  },
  {
    label: "文件大小",
    prop: "size",
    scopedSlots: "size",
    width: 200,
  },
];

// 搜索功能
const search = () => {
  showLoading.value = true;
  loadDataList();
};

// 数据源
const tableData = ref({});
// 表格选项
const tableOptions = {
  extHeight: 50,
  selectType: "checkbox",
};
// 文件名
const key = ref();

const showLoading = ref(true);
// 分类
const category = ref();
// 当前文件夹
const currentFolder = ref({fileId: 0});

// 获得数据;
const loadDataList = async () => {
  const queryParams = route.query;

  let params = {
    // 文件名（模糊）
    key: key.value,
    // 分类
    category: category.value,
    // 文件父id
    dir: '/' + (queryParams.path || '')
  };

  let result = await proxy.Request({
    url: api.loadDataList,
    showLoading: showLoading,
    params,
  });
  if (!result) {
    return;
  }
  tableData.value.list = result.data;
};

// 跳转目录
const dumpDir = (row) => {
  if (row.isdir === 1) {
    navigationRef.value.openFolder(row);
  }
};

// 当鼠标放在当前行时,分享下载等图标出现
const showOp = (row) => {
  // 关闭所有的显示
  tableData.value.list.forEach((element) => {
    element.showOp = false;
  });
  // 只开启当前显示
  row.showOp = true;
};

const cancelShowOp = (row) => {
  row.showOp = false;
};

// 行选中
// 多选 批量选中
const selectFileIdList = ref([]);
const rowSelected = (rows) => {
  selectFileIdList.value = [];
  rows.forEach((item) => {
    selectFileIdList.value.push(item.fileId);
  });
};

// 绑定导航栏
const navigationRef = ref();

// 目录
const navChange = (data) => {
  const {curFolder, categoryId} = data;
  currentFolder.value = curFolder;
  showLoading.value = true;
  category.value = categoryId;
  loadDataList();
};

// 下载文件
const fetchDownload = async (row) => {
  let result = await download({
    path: [row.path]
  })
  downloadRef.value.show(result[0]);
};
</script>

<style lang="scss" scoped>
@import "@/assets/file.list.scss";
</style>
