# 后端模块规则

## 后端规则

- 后端使用 Spring Boot + MyBatis-Plus + MySQL + JWT
- 保持清晰分层：controller / service / service.impl / mapper / entity / dto / vo / config / common / utils
- 接口优先使用 RESTful 风格
- 第一阶段不做权限细分，只做管理员登录态校验
- 统一处理返回结构、异常处理、参数校验
- 修改表结构时同步更新 SQL 文档
- 开发完成后优先进行接口自测
- Java 注释、类说明、方法说明、业务备注默认使用中文
- 实体字段说明、DTO/VO 业务语义说明优先使用中文表达
- SQL 表注释、字段注释、初始化说明默认使用中文
- 后端规则文档标题、说明文字、设计备注默认使用中文
- 标识符遵循 Java 常见英文命名规范，不强行改为中文命名

## 后端目标

后端不仅要实现博客业务，也要体现 Java 项目的工程化能力，便于学习和面试表达。
