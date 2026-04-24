import { defineStore } from "pinia";
import { getCurrentUser, login as loginApi, logout as logoutApi } from "../api/auth";
import { clearToken, getToken, setToken } from "../utils/auth";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    token: getToken(),
    userInfo: null
  }),
  getters: {
    isLoggedIn: (state) => Boolean(state.token)
  },
  actions: {
    async login(formData) {
      const response = await loginApi(formData);
      const payload = response.data;
      this.token = payload.token;
      this.userInfo = payload.userInfo;
      setToken(payload.token);
      return payload;
    },
    async fetchCurrentUser() {
      if (!this.token) {
        return null;
      }
      const response = await getCurrentUser();
      this.userInfo = response.data;
      return this.userInfo;
    },
    async logout() {
      if (this.token) {
        try {
          await logoutApi();
        } catch (error) {
          // 退出登录接口失败时，仍然清理本地登录态，避免页面卡住。
        }
      }
      this.reset();
    },
    reset() {
      this.token = "";
      this.userInfo = null;
      clearToken();
    }
  }
});
