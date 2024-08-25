<template>
  <div class="login-body">
    <div class="bg"></div>
    <div class="login-panel">
      <el-form
          class="login-register"
          :model="formData"
          :rules="rules"
          ref="formDataRef"
      >
        <!--   rules属性传入验证规则
               prop属性设置需要校验的字段名
               model属性是用来指定表单使用的数据
        -->
        <div class="login-title">Easy云盘</div>
        <!--input输入-->
        <el-form-item prop="username">
          <el-input
              size="large"
              clearable
              placeholder="请输入用户名"
              v-model.trim="formData.username"
              maxLength="150"
          >
            <template #prefix>
              <span class="iconfont icon-account"></span>
            </template>
          </el-input>
        </el-form-item>

        <!-- 登录密码 -->
        <el-form-item prop="password">
          <el-input
              type="password"
              size="large"
              placeholder="请输入密码"
              v-model.trim="formData.password"
              show-password
          >
            <template #prefix>
              <span class="iconfont icon-password"></span>
            </template>
          </el-input>
        </el-form-item>

        <!-- 登录 -->
        <el-form-item>
          <div class="rememberme-panel">
            <el-checkbox v-model="formData.rememberMe">记住我</el-checkbox>
          </div>
        </el-form-item>

        <!-- 登录按钮 -->
        <el-form-item>
          <el-button
              type="primary"
              class="op-btn"
              @click="doSubmit('login')"
              size="large"
          >
            <span>登录</span>
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              class="op-btn"
              @click="doSubmit('register')"
              size="large"
          >
            <span>注册</span>
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>


<script setup lang="ts">
// nextTick指定的回调在DOM更新之后再执行
// ref 应用在html标签上获取真实的DOM元素。  应用在组件标签上获取组件实例对象
// getCurrentInstance 获取当前组件的实例、上下文来操作router和vuex
import {getCurrentInstance, nextTick, onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import type {LoginCommandType} from "@/types/AdminTypes.js";
import {getUserInfo, login} from "@/api/AdminApi.ts";

const {proxy} = getCurrentInstance();

const router = useRouter();
const route = useRoute();

onMounted(() => {
  resetForm();
});

// 登陆界面
const formData = ref<LoginCommandType>({});
const formDataRef = ref();
// 校验规则（all）
const rules = {
  username: [
    {required: true, message: "请输入用户名"},
    {min: 5, max: 30, message: '长度3~30'},
  ],
  password: [{required: true, message: "请输入密码"}],
};

// 重置表单
const resetForm = () => {
  nextTick(() => {
    formDataRef.value.resetFields();
    formData.value = {};

    // 登录
    const cookieLoginInfo = proxy.VueCookies.get("loginInfo");
    if (cookieLoginInfo) {
      formData.value = cookieLoginInfo;
    }
  });
};

// 登录、注册、重置密码、提交表单
const doSubmit = (option) => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) {
      return;
    }
    let params = {};
    Object.assign(params, formData.value);

    let token = await login({
      ...params,
      option
    });

    if (!token) {
      return;
    }

    // 检查是否点击 “记住我”
    if (params.rememberMe) {
      const loginInfo = {
        username: params.username,
        password: params.password,
        rememberMe: params.rememberMe,
      };
      // 将存储七天
      proxy.VueCookies.set("loginInfo", loginInfo, "7d");
    } else {
      proxy.VueCookies.remove("loginInfo");
    }
    proxy.Message.success("登录成功");
    // 存储cookie
    proxy.VueCookies.set("token", token, "1d");
    await getUserInfo();
    // 重定向到原始页面
    if (route.query.redirectUrl && route.query.redirectUrl !== '/login') {
      await router.push(route.query.redirectUrl);
    } else {
      await router.push("/");
    }
  });
};
</script>

<style lang="scss" scoped>
.login-body {
  height: calc(100vh);
  // 把背景图像扩展至足够大，以使背景图像完全覆盖背景区域。
  background-size: cover;
  background: url("../assets/login_bg.jpg");
  display: flex;

  .bg {
    flex: 1;
    background-position: center;
    background-size: 800px;
    background-repeat: no-repeat;
    background-image: url("../assets/login_img.png");
  }

  .login-panel {
    width: 430px;
    margin-right: 15%;
    margin-top: calc((100vh - 500px) / 2);

    .login-register {
      padding: 25px;
      background: #fff;
      border-radius: 5px;

      .login-title {
        text-align: center;
        font-size: 18px;
        font-weight: bold;
        margin-bottom: 20px;
      }

      .rememberme-panel {
        width: 100%;
      }

      .op-btn {
        width: 100%;
      }
    }
  }
}
</style>
