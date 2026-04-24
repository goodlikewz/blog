<template>
  <div class="page-shell">
    <section class="hero-card dashboard-header">
      <div>
        <p class="eyebrow">后台控制台</p>
        <h1>欢迎回来，{{ displayName }}</h1>
        <p class="description">
          后台登录模块已经打通，下一步将继续接入分类、标签和文章管理。
        </p>
      </div>
      <div class="dashboard-actions">
        <el-button @click="refreshCurrentUser">刷新信息</el-button>
        <el-button type="danger" plain @click="handleLogout">退出登录</el-button>
      </div>
    </section>

    <section class="grid-panel">
      <article class="panel-card">
        <h2>当前账号</h2>
        <p>用户名：{{ authStore.userInfo?.username || "-" }}</p>
        <p>昵称：{{ authStore.userInfo?.nickname || "-" }}</p>
      </article>
      <article class="panel-card">
        <h2>当前进度</h2>
        <p>已完成后台登录、Token 校验、退出登录和登录态守卫。</p>
      </article>
      <article class="panel-card">
        <h2>下一步建议</h2>
        <p>继续实现分类管理、标签管理和文章管理，逐步形成完整后台。</p>
      </article>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { useAuthStore } from "../../store/auth";

const router = useRouter();
const authStore = useAuthStore();

const displayName = computed(() => authStore.userInfo?.nickname || authStore.userInfo?.username || "管理员");

async function refreshCurrentUser() {
  try {
    await authStore.fetchCurrentUser();
    ElMessage.success("用户信息已刷新");
  } catch (error) {
    ElMessage.error(error.response?.data?.message || "获取用户信息失败");
  }
}

async function handleLogout() {
  await authStore.logout();
  ElMessage.success("已退出登录");
  router.push("/admin/login");
}

onMounted(async () => {
  if (!authStore.userInfo) {
    await refreshCurrentUser();
  }
});
</script>
