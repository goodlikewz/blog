<template>
  <div class="login-page">
    <div class="login-card">
      <h1>后台登录</h1>
      <p>请输入管理员账号密码，登录后进入博客后台。</p>
      <el-alert
        title="默认初始化账号：admin，默认密码：123456"
        type="info"
        :closable="false"
        class="login-tip"
      />
      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-position="top"
        @keyup.enter="handleLogin"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="formData.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-button type="primary" class="full-width" :loading="loading" @click="handleLogin">
          登录
        </el-button>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import { useRoute, useRouter } from "vue-router";
import { useAuthStore } from "../../store/auth";

const router = useRouter();
const route = useRoute();
const authStore = useAuthStore();
const formRef = ref();
const loading = ref(false);
const formData = reactive({
  username: "admin",
  password: "123456"
});

const rules = {
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }]
};

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false);
  if (!valid) {
    return;
  }

  loading.value = true;
  try {
    await authStore.login(formData);
    ElMessage.success("登录成功");
    router.push(route.query.redirect || "/admin/dashboard");
  } catch (error) {
    ElMessage.error(error.response?.data?.message || "登录失败，请检查账号密码");
  } finally {
    loading.value = false;
  }
}
</script>
