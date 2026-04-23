# 数据库设计说明

## 一、设计目标

数据库设计需要满足以下要求：

- 支撑博客系统第一阶段核心功能
- 表结构清晰，便于理解和维护
- 字段设计尽量为后续统计、评论、缓存等功能预留空间
- 适合学习 Java 项目中的实体建模、关系设计和 CRUD 开发

## 二、核心表清单

第一阶段核心表：

- `blog_user`
- `blog_article`
- `blog_category`
- `blog_tag`
- `blog_article_tag`
- `blog_config`

第二阶段可扩展表：

- `blog_article_stats`
- `blog_comment`

## 三、表设计说明

### 3.1 用户表 `blog_user`

作用：

- 存储后台管理员账号

核心字段：

- `id`：主键
- `username`：用户名
- `password`：密码
- `nickname`：昵称
- `status`：状态，1 启用，0 禁用
- `create_time`：创建时间
- `update_time`：更新时间

设计说明：

- 第一阶段只考虑管理员账号
- 暂不做前台注册用户体系

### 3.2 文章表 `blog_article`

作用：

- 存储文章主信息

核心字段：

- `id`：主键
- `title`：标题
- `summary`：摘要
- `content`：正文内容
- `cover_image`：封面图
- `category_id`：分类 ID
- `article_type`：文章类型
- `series_name`：系列名称或专题标识
- `status`：状态，0 草稿，1 已发布，2 下线
- `is_top`：是否置顶
- `is_recommend`：是否推荐
- `view_count`：阅读量
- `like_count`：点赞数
- `create_time`：创建时间
- `update_time`：更新时间

设计说明：

- 第一阶段阅读量和点赞数先直接放在文章表中，便于快速实现
- 后续若统计需求复杂，可拆分到独立统计表
- 增加 `article_type` 有助于区分学习总结、项目实战、面试复盘等内容
- `series_name` 可先作为轻量级专题聚合方案，后续再升级为独立专栏表

### 3.3 分类表 `blog_category`

作用：

- 存储文章分类

核心字段：

- `id`：主键
- `name`：分类名称
- `description`：分类描述
- `sort`：排序值
- `create_time`：创建时间
- `update_time`：更新时间

设计说明：

- 一篇文章只属于一个分类
- 分类需要支持排序与描述

### 3.4 标签表 `blog_tag`

作用：

- 存储文章标签

核心字段：

- `id`：主键
- `name`：标签名称
- `create_time`：创建时间
- `update_time`：更新时间

设计说明：

- 标签用于实现文章主题维度的灵活归类

### 3.5 文章标签关联表 `blog_article_tag`

作用：

- 实现文章与标签的多对多关系

核心字段：

- `id`：主键
- `article_id`：文章 ID
- `tag_id`：标签 ID

设计说明：

- 一篇文章可以对应多个标签
- 一个标签也可以被多篇文章使用

### 3.6 配置表 `blog_config`

作用：

- 存储博客基础配置信息

核心字段：

- `id`：主键
- `config_key`：配置键
- `config_value`：配置值
- `description`：配置说明
- `update_time`：更新时间

设计说明：

- 用于维护博客标题、副标题、关于我、页脚版权等配置项

### 3.7 可选扩展表 `blog_article_stats`

作用：

- 独立维护文章统计信息

核心字段：

- `id`：主键
- `article_id`：文章 ID
- `view_count`：阅读量
- `like_count`：点赞数
- `comment_count`：评论数
- `update_time`：更新时间

设计说明：

- 第一阶段可以不落表
- 第二阶段如统计逻辑复杂，可从文章表中拆分出来

### 3.8 可选扩展表 `blog_comment`

作用：

- 存储文章评论

核心字段：

- `id`：主键
- `article_id`：文章 ID
- `nickname`：评论人昵称
- `content`：评论内容
- `status`：状态，0 待审核，1 通过，2 拒绝
- `create_time`：创建时间

设计说明：

- 评论功能第二阶段实现

## 四、表关系说明

### 4.1 分类与文章

- 一个分类下有多篇文章
- 一篇文章属于一个分类

关系：

- `blog_category` 1 对 N `blog_article`

### 4.2 文章与标签

- 一篇文章可绑定多个标签
- 一个标签可被多篇文章绑定

关系：

- `blog_article` N 对 N `blog_tag`
- 通过 `blog_article_tag` 实现关联

### 4.3 文章与评论

- 一篇文章可有多条评论

关系：

- `blog_article` 1 对 N `blog_comment`

## 五、第一阶段建表建议

第一阶段优先创建以下表：

- `blog_user`
- `blog_article`
- `blog_category`
- `blog_tag`
- `blog_article_tag`
- `blog_config`

原因：

- 先支持登录、文章、分类、标签、配置等核心功能
- 评论和独立统计表可在后续阶段按需补充
- 文章类型和系列标识优先直接加在文章表中，避免第一阶段引入过多新表

## 六、字段设计原则

- 主键统一使用 `bigint`
- 状态字段统一使用数字编码
- 所有核心表保留创建时间和更新时间
- 适合后续与实体类、DTO、VO 一一对应
- 字段命名尽量简洁明确，符合 Java 项目常见习惯

## 七、后续扩展方向

- 阅读量可拆到独立统计表
- 热门文章可结合 Redis 做缓存
- 搜索可从数据库模糊查询升级到 Elasticsearch
- 用户体系可在后续扩展为前台访客账户体系
- 文章系列可拆为独立专栏表
- 场景面试专题可升级为独立内容聚合模块
