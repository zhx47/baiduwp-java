<template>
  <div>
    <!-- 修改头像弹出框 -->
    <Dialog
        :show="dialogConfig.show"
        :title="dialogConfig.title"
        :buttons="dialogConfig.buttons"
        :show-close="false"
        :showCancel="false"
    >
      <el-form
          :model="formData"
          :rules="rules"
          ref="formDataRef"
          @submit.prevent
      >
        <el-space fill>
          <el-alert type="info" show-icon :closable="false">
            <p><a href="https://openapi.baidu.com/oauth/2.0/authorize?response_type=code&client_id=iYCeC9g08h5vuP9UqvPHKKSVrKFXGa1v&redirect_uri=https://alist.nn.ci/tool/baidu/callback&scope=basic,netdisk&qrcode=1" target="_blank">点击此处</a> 获取您的 refresh_token。</p>
          </el-alert>
          <el-form-item label="token" prop="token">
            <el-input
                placeholder="请输入 refresh_token"
                v-model.trim="formData.token"
            >
              <template #prefix>
                <span class="iconfont"></span>
              </template>
            </el-input>
          </el-form-item>
        </el-space>
      </el-form>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
import {getCurrentInstance, ref} from "vue";
import {bindToken, getUserInfo} from "@/api/AdminApi.js";

const {proxy} = getCurrentInstance();

const formData = ref({});
const formDataRef = ref();

const rules = {
  token: [
    {required: true, message: "请输入 refresh_token"},
  ]
};

const close = () => {
  dialogConfig.value.show = false;
};
// 子组件暴露自己的属性
// 父组件需要调用子组件的方法父组件需要调用子组件的方法，
// 或者访问子组件的变量
defineExpose({close});

const dialogConfig = ref({
  show: true,
  title: "绑定百度 token",
  buttons: [
    {
      type: "primary",
      text: "确定",
      click: (e) => {
        submitForm();
      },
    },
  ],
});

const submitForm = async () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) {
      return;
    }
    let result = await bindToken(formData.value)
    if (!result) {
      return;
    }
    dialogConfig.value.show = false;
    proxy.Message.success(result);
    await getUserInfo();
    window.location.reload();
  });
};
</script>

<style>
</style>