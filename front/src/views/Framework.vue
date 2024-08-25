<template>
  <div class="framework">
    <!-- 头部 -->
    <div class="header">
      <!-- 左上角logo -->
      <div class="logo">
        <span class="iconfont icon-pan"></span>
        <span class="name">Easy云盘</span>
      </div>

      <!-- 右侧消息弹框 -->
      <div class="right-panel">
        <!-- 用户信息下拉菜单 -->
        <el-dropdown>
          <!-- 用户信息 -->
          <div class="user-info">
            <!-- 头像 -->
            <div class="avatar">
              <Avatar/>
            </div>
            <!-- 昵称 -->
            <span class="nick-name">{{ userInfo.baiduPanUserInfo?.baiduName }}</span>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="updatePassword">
                修改密码
              </el-dropdown-item>
              <el-dropdown-item @click="logout"> 退出</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>

    <!-- 主体 -->
    <div class="body">
      <!-- 最左侧菜单栏 一级目录-->
      <div class="left-sider">
        <div class="menu-sub-list">
          <div
              @click="jump(sub)"
              :class="['menu-item-sub', currentPath == sub.path ? 'active' : '']"
              v-for="sub in currentMenu"
          >
            <span
                :class="['iconfont', 'icon-' + sub.icon]"
                v-if="sub.icon"
            ></span>
            <span class="text">{{ sub.name }}</span>
          </div>
          <div class="tips" v-if="currentMenu && currentMenu.tips">
            {{ currentMenu.tips }}
          </div>
          <div class="space-info">
            <div>空间使用</div>
            <div class="percent">
              <el-progress
                  :percentage="
                  Math.floor(
                    (useSpaceInfo.useSpace / useSpaceInfo.totalSpace) * 10000
                  ) / 100
                "
                  color="#409eff"
              />
            </div>

            <div class="space-use">
              <div class="use">
                {{ proxy.Utils.size2Str(useSpaceInfo.useSpace) }}/
                {{ proxy.Utils.size2Str(useSpaceInfo.totalSpace) }}
              </div>
              <div class="iconfont icon-refresh" @click="getUseSpace"></div>
            </div>
          </div>
        </div>
      </div>
      <!-- 中间主题内容 -->
      <div class="body-content">
        <!-- v-slot="{ Component } 解构插槽 -->
        <!-- 让router-view的插槽能够访问子组件中的数据 -->
        <!-- 访问的数据就是Component -->
        <router-view v-slot="{ Component }">
          <!-- 调用Main子组件 将Main中的数据接收到Framework中 -->
          <component ref="routerViewRef" :is="Component"/>
        </router-view>
      </div>
    </div>

    <!-- 修改密码 -->
    <UpdatePassword ref="updatePasswordRef"></UpdatePassword>
    <!--  绑定百度网盘Token  -->
    <BindBaiduPan ref="bindBaiduPanRef"></BindBaiduPan>
  </div>
</template>

<script setup lang="ts">
import UpdatePassword from "./UpdatePassword.vue";
import {getCurrentInstance, nextTick, ref} from "vue";
import {useRouter} from "vue-router";
import Avatar from "@/components/Avatar.vue";
import {SystemLoginUserType} from "@/types/AdminTypes.ts";
import BindBaiduPan from "@/views/BindBaiduPan.vue";

const api = {
  getUseSpace: "/getUseSpace",
  logout: "/logout",
};

const {proxy} = getCurrentInstance();
const router = useRouter();

// 上传文件后的刷新列表(调用Uploader子组件中的函数)
const routerViewRef = ref();

const userInfo = ref<SystemLoginUserType>(proxy.VueCookies.get("userInfo"));
const bindBaiduPanRef = ref();
nextTick(() => {
  if (!proxy.Utils.isEmpty(userInfo.value.baiduPanUserInfo)) {
    // 没有绑定百度账号或者token已经失效
    bindBaiduPanRef.value.close()
  }
})

// 菜单栏
const menus = [
  {
    icon: "all",
    name: "全部",
    category: "",
    path: "/main/all",
  },
  {
    icon: "video",
    name: "视频",
    category: "video",
    path: "/main/video",
  },
  {
    icon: "music",
    name: "音频",
    category: "music",
    path: "/main/music",
  },
  {
    icon: "image",
    name: "图片",
    category: "image",
    path: "/main/image",
  },
  {
    icon: "doc",
    name: "文档",
    category: "doc",
    path: "/main/doc",
  },
  {
    icon: "more",
    name: "其他",
    category: "others",
    path: "/main/others",
  },
];
const currentMenu = ref(menus);
const currentPath = ref();

// 菜单栏选项跳转
const jump = (data) => {
  if (!data.path) {
    return;
  }
  router.push(data.path);
};

// 修改密码
const updatePasswordRef = ref();
const updatePassword = () => {
  updatePasswordRef.value.show();
};

// 退出登录
const logout = () => {
  proxy.Confirm(`你确定要删除退出吗`, async () => {
    let result = await proxy.Request({
      url: api.logout,
    });
    if (!result) {
      return;
    }
    proxy.VueCookies.remove("userInfo");
    router.push("/login");
  });
};

// 使用空间
const useSpaceInfo = ref({useSpace: 0, totalSpace: 1});
const getUseSpace = async () => {
  let result = await proxy.Request({
    url: api.getUseSpace,
    showLoading: false,
  });
  if (!result) {
    return;
  }
  useSpaceInfo.value = {useSpace: result.data.used, totalSpace: result.data.total};
};
getUseSpace();
</script>

<style scoped>
.header {
  box-shadow: 0 3px 10px 0 rgb(0 0 0 / 6%);
  height: 56px;
  padding-left: 24px;
  padding-right: 24px;
  position: relative;
  z-index: 200;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.header .logo {
  display: flex;
  align-items: center;
}

.header .logo .icon-pan {
  font-size: 40px;
  color: #1296db;
}

.header .logo .name {
  font-weight: bold;
  margin-left: 5px;
  font-size: 25px;
  color: #05a1f5;
}

.header .right-panel {
  display: flex;
  align-items: center;
}

.header .right-panel .icon-transfer {
  cursor: pointer;
}

.header .right-panel .user-info {
  margin-right: 10px;
  display: flex;
  align-items: center;
  cursor: pointer;
}

.header .right-panel .user-info .avatar {
  margin: 0px 5px 0px 15px;
}

.header .right-panel .user-info .nick-name {
  color: #05a1f5;
}

.body {
  display: flex;
}

.body .left-sider {
  border-right: 1px solid #f1f2f4;
  display: flex;
}

.body .left-sider .menu-list {
  height: calc(100vh - 56px);
  width: 80px;
  box-shadow: 0 3px 10px 0 rgb(0 0 0 / 6%);
  border-right: 1px solid #f1f2f4;
}

.body .left-sider .menu-list .menu-item {
  text-align: center;
  font-size: 14px;
  font-weight: bold;
  padding: 20px 0px;
  cursor: pointer;
}

.body .left-sider .menu-list .menu-item:hover {
  background: #f3f3f3;
}

.body .left-sider .menu-list .menu-item .iconfont {
  font-weight: normal;
  font-size: 28px;
}

.body .left-sider .menu-list .active .iconfont {
  color: #06a7ff;
}

.body .left-sider .menu-list .active .text {
  color: #06a7ff;
}

.body .left-sider .menu-sub-list {
  width: 200px;
  padding: 20px 10px 0px;
  position: relative;
}

.body .left-sider .menu-sub-list .menu-item-sub {
  text-align: center;
  line-height: 40px;
  border-radius: 5px;
  cursor: pointer;
}

.body .left-sider .menu-sub-list .menu-item-sub:hover {
  background: #f3f3f3;
}

.body .left-sider .menu-sub-list .menu-item-sub .iconfont {
  font-size: 14px;
  margin-right: 20px;
}

.body .left-sider .menu-sub-list .menu-item-sub .text {
  font-size: 13px;
}

.body .left-sider .menu-sub-list .active {
  background: #eef9fe;
}

.body .left-sider .menu-sub-list .active .iconfont {
  color: #05a1f5;
}

.body .left-sider .menu-sub-list .active .text {
  color: #05a1f5;
}

.body .left-sider .menu-sub-list .tips {
  margin-top: 10px;
  color: #888888;
  font-size: 13px;
}

.body .left-sider .menu-sub-list .space-info {
  position: absolute;
  bottom: 10px;
  width: 100%;
  padding: 0px 5px;
}

.body .left-sider .menu-sub-list .space-info .percent {
  padding-right: 10px;
}

.body .left-sider .menu-sub-list .space-info .space-use {
  margin-top: 5px;
  color: #7e7e7e;
  display: flex;
  justify-content: space-around;
}

.body .left-sider .menu-sub-list .space-info .space-use .use {
  flex: 1;
}

.body .left-sider .menu-sub-list .space-info .space-use .iconfont {
  cursor: pointer;
  margin-right: 20px;
  color: #05a1f5;
}

.body .body-content {
  flex: 1;
  width: 0;
  padding-left: 20px;
}
</style>
