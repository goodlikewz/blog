import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/portal/HomeView.vue";
import LoginView from "../views/admin/LoginView.vue";
import DashboardView from "../views/admin/DashboardView.vue";
import { getToken } from "../utils/auth";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView
  },
  {
    path: "/admin/login",
    name: "admin-login",
    component: LoginView
  },
  {
    path: "/admin/dashboard",
    name: "admin-dashboard",
    component: DashboardView,
    meta: {
      requiresAuth: true
    }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to) => {
  if (to.meta.requiresAuth && !getToken()) {
    return { name: "admin-login", query: { redirect: to.fullPath } };
  }

  if (to.name === "admin-login" && getToken()) {
    return { name: "admin-dashboard" };
  }

  return true;
});

export default router;
