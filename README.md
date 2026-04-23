# 学习型技术博客系统

一个以 Java 学习为主线、以个人博客为载体、以知识沉淀和面试展示为结果的学习型技术项目。

## 项目目标

- 搭建一个可运行、可上线、可持续扩展的个人技术博客
- 在开发过程中系统练习 Java 后端项目能力
- 沉淀 Java / Spring Boot / Redis / RocketMQ 等学习内容
- 形成一个可用于面试展示和项目讲述的完整作品

## 项目定位

这个项目同时承担 4 个目标：

- 博客系统：用于发布和展示技术文章
- Java 学习项目：用于练习真实后端开发能力
- 知识库：用于长期沉淀学习内容
- 面试项目：用于展示工程能力和技术成长

## 默认技术栈

### 后端技术

- Spring Boot
- MyBatis-Plus
- MySQL
- JWT
- Redis（第二阶段引入）

### 前端技术

- Vue 3
- Vite
- Element Plus

## 第一阶段范围

- 后台登录
- 文章管理
- 分类管理
- 标签管理
- 前台文章列表
- 前台文章详情
- Markdown 渲染
- 搜索功能

## 推荐目录

```text
blog/
├── README.md
├── AGENTS.md
├── docs/
├── sql/
├── blog-server/
└── blog-web/
```

## 开发原则

- 先完成 P0 闭环，再做增强功能
- 默认由 Codex 负责实现、联调、自测、总结
- 每天开发结束后更新日报并提交 Git
- 优先保证代码可运行、结构清晰、方便扩展

## 文档说明

- [项目需求概览](/D:/project/blog/docs/requirements.md)
- [项目需求说明书](/D:/project/blog/docs/project-requirements.md)
- [项目架构说明](/D:/project/blog/docs/architecture.md)
- [数据库设计说明](/D:/project/blog/docs/db-design.md)
- [内容体系与选题规划](/D:/project/blog/docs/content-plan.md)
- [第一批内容建设计划](/D:/project/blog/docs/first-batch-content-plan.md)
- [第一阶段任务清单](/D:/project/blog/docs/stage-1-task-list.md)
- [迭代计划](/D:/project/blog/docs/iteration-plan.md)
- [Git 工作流说明](/D:/project/blog/docs/git-workflow.md)
- [Codex 提示词清单](/D:/project/blog/docs/codex-prompts.md)

## 当前启动状态

当前已完成：

- 项目基线文档
- 首批内容规划
- 数据库初始化脚本
- 前后端工程骨架
- 第一阶段任务清单

当前下一步：

- 初始化本地数据库
- 启动前后端工程
- 进入后台登录模块开发
