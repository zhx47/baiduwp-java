<template>
  <div>
    <!-- 修改头像弹出框 -->
    <Dialog
        :show="dialogConfig.show"
        :title="dialogConfig.title"
        :buttons="dialogConfig.buttons"
        width="60%"
        :showCancel="true"
        @close="dialogConfig.show = false"
    >
      <el-form
          :model="formData"
          ref="formDataRef"
          label-width="80px"
      >
        <el-form-item label="文件名称">
          <span>{{ formData.server_filename }}</span>
        </el-form-item>
        <el-form-item label="文件大小">
          <span>{{ proxy.Utils.size2Str(formData.size) }}</span>
        </el-form-item>
        <el-form-item label="MD5">
          <span>{{ formData.md5 }}</span>
        </el-form-item>
        <el-form-item label="上传时间">
          <span>{{ proxy.Utils.timestampToDateStr(formData.server_mtime) }}</span>
        </el-form-item>
        <el-form-item label="UA">
          <span>netdisk</span>
        </el-form-item>
        <el-alert title="点击复制下载链接，使用IDM等下载软件需要手动设置以上UA" type="warning" show-icon
                  :closable="false"/>
        <el-form-item label="下载地址" @click="copy">
          <el-input v-model="formData.dlink" readonly></el-input>
        </el-form-item>
        <el-alert title="RPC地址"
                  description="推送aria2默认配置: ws://localhost:6800/jsonrpc  推送Motrix默认配置: ws://localhost:16800/jsonrpc"
                  type="info" show-icon :closable="false"/>
        <el-form-item label="RPC地址">
          <el-input v-model="aria2.rpc"></el-input>
        </el-form-item>
        <el-form-item label="Token">
          <el-input v-model="aria2.token"></el-input>
        </el-form-item>
      </el-form>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
import {getCurrentInstance, nextTick, ref} from "vue";
import useClipboard from "vue-clipboard3";
import {sendToAria2} from "@/api/AdminApi.ts";

const {proxy} = getCurrentInstance();
const {toClipboard} = useClipboard();

const aria2 = ref({})

const formData = ref({});
const formDataRef = ref();

const show = (downloadInfo) => {
  dialogConfig.value.show = true;
  nextTick(() => {
    formDataRef.value.resetFields();
    formData.value = downloadInfo;
    let aria2Setting = localStorage.getItem('aria2Setting')
    if (!aria2Setting) {
      aria2.value = {rpc: 'ws://localhost:16800/jsonrpc', token: ''}
    } else {
      aria2.value = JSON.parse(aria2Setting)
    }
  });
};
// 子组件暴露自己的属性
// 父组件需要调用子组件的方法父组件需要调用子组件的方法，
// 或者访问子组件的变量
defineExpose({show});

const dialogConfig = ref({
  show: false,
  title: "开始下载",
  buttons: [
    {
      type: "primary",
      text: "发送到 Aria2 / Motrix Json-RPC",
      click: async (e) => {
        await fetchDownload();
      },
    },
  ],
});

const fetchDownload = async () => {
  let downloadInfo = formData.value;
  localStorage.setItem('aria2Setting', JSON.stringify(aria2.value))
  sendToAria2(aria2.value.rpc, aria2.value.token, downloadInfo.server_filename, downloadInfo.dlink)
};

const copy = async () => {
  await toClipboard(formData.value.dlink);
  proxy.Message.success("复制成功");
};
</script>

<style>
</style>