<template>
  <div class="login-page">
    <div class="login-card">
      <h1>后台登录</h1>
      <p>当前为工程初始化阶段，下一步将接入真实登录接口。</p>
      <el-form label-position="top">
        <el-form-item label="用户名">
          <el-input placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        <el-button type="primary" class="full-width">登录</el-button>
      </el-form>
    </div>
  </div>
</template>

