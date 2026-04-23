import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/portal/HomeView.vue";
import LoginView from "../views/admin/LoginView.vue";

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
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;

