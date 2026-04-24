import request from "../utils/request";

export function login(data) {
  return request.post("/api/admin/auth/login", data);
}

export function logout() {
  return request.post("/api/admin/auth/logout");
}

export function getCurrentUser() {
  return request.get("/api/admin/auth/me");
}
