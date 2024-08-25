<template>
  <!-- 头像 -->
  <span class="avatar">
    <img :src="userInfo.baiduPanUserInfo?.avatarUrl" alt="头像"/>
  </span>
</template>

<script setup lang="ts">
import {getCurrentInstance, ref} from "vue";
import type {SystemLoginUserType} from "@/types/AdminTypes.js";
// 获取当前组件实例
const { proxy } = getCurrentInstance();
const userInfo = ref<SystemLoginUserType>(proxy.VueCookies.get("userInfo"));
</script>

<style lang="scss" scoped>
.avatar {
  display: flex;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  img {
    width: 100%;
    object-fit: cover;
  }
}
</style>
