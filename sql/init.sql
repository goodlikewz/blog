CREATE DATABASE IF NOT EXISTS `learning_blog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `learning_blog`;

CREATE TABLE IF NOT EXISTS `blog_user` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1启用 0禁用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_blog_user_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台管理员表';

CREATE TABLE IF NOT EXISTS `blog_category` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(100) NOT NULL COMMENT '分类名称',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '分类描述',
  `sort` INT NOT NULL DEFAULT 0 COMMENT '排序值',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_blog_category_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章分类表';

CREATE TABLE IF NOT EXISTS `blog_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(100) NOT NULL COMMENT '标签名称',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_blog_tag_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章标签表';

CREATE TABLE IF NOT EXISTS `blog_article` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` VARCHAR(200) NOT NULL COMMENT '标题',
  `summary` VARCHAR(500) DEFAULT NULL COMMENT '摘要',
  `content` LONGTEXT COMMENT '正文内容',
  `cover_image` VARCHAR(255) DEFAULT NULL COMMENT '封面图',
  `category_id` BIGINT DEFAULT NULL COMMENT '分类ID',
  `article_type` VARCHAR(50) NOT NULL DEFAULT '学习笔记' COMMENT '文章类型',
  `series_name` VARCHAR(100) DEFAULT NULL COMMENT '系列名称或专题标识',
  `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0草稿 1已发布 2下线',
  `is_top` TINYINT NOT NULL DEFAULT 0 COMMENT '是否置顶',
  `is_recommend` TINYINT NOT NULL DEFAULT 0 COMMENT '是否推荐',
  `view_count` BIGINT NOT NULL DEFAULT 0 COMMENT '阅读量',
  `like_count` BIGINT NOT NULL DEFAULT 0 COMMENT '点赞数',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_blog_article_category_id` (`category_id`),
  KEY `idx_blog_article_status` (`status`),
  KEY `idx_blog_article_article_type` (`article_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='博客文章表';

CREATE TABLE IF NOT EXISTS `blog_article_tag` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `article_id` BIGINT NOT NULL COMMENT '文章ID',
  `tag_id` BIGINT NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_blog_article_tag_relation` (`article_id`, `tag_id`),
  KEY `idx_blog_article_tag_article_id` (`article_id`),
  KEY `idx_blog_article_tag_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章标签关联表';

CREATE TABLE IF NOT EXISTS `blog_config` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
  `config_key` VARCHAR(100) NOT NULL COMMENT '配置键',
  `config_value` VARCHAR(1000) DEFAULT NULL COMMENT '配置值',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '配置说明',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_blog_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

INSERT INTO `blog_user` (`username`, `password`, `nickname`, `status`)
VALUES ('admin', '$2a$10$LxRehIGTMUupPPu9AZFt9uxxrB/oCDyHCzU2uuoePq3Jw3Pj4X2Zy', '管理员', 1)
ON DUPLICATE KEY UPDATE `password` = VALUES(`password`), `nickname` = VALUES(`nickname`), `status` = VALUES(`status`);

INSERT INTO `blog_config` (`config_key`, `config_value`, `description`)
VALUES
('blog.title', '学习型技术博客系统', '博客标题'),
('blog.subtitle', '记录 Java 学习、项目实战与面试复盘', '博客副标题'),
('blog.welcome', '欢迎来到我的学习型技术博客', '首页欢迎语'),
('blog.about', '这是一个用于沉淀 Java 学习内容和项目实战经验的个人博客。', '关于我')
ON DUPLICATE KEY UPDATE `config_value` = VALUES(`config_value`), `description` = VALUES(`description`);

INSERT INTO `blog_category` (`name`, `description`, `sort`)
VALUES
('Java', 'Java 基础与进阶学习', 1),
('Spring Boot', 'Spring Boot 学习与实战', 2),
('Redis', 'Redis 学习与缓存实践', 3),
('项目实战', '项目开发与设计实现', 4),
('面试复盘', '场景题与面试总结', 5)
ON DUPLICATE KEY UPDATE `description` = VALUES(`description`), `sort` = VALUES(`sort`);

INSERT INTO `blog_tag` (`name`)
VALUES
('多线程'),
('事务'),
('缓存'),
('分布式锁'),
('SQL优化'),
('面试')
ON DUPLICATE KEY UPDATE `name` = VALUES(`name`);
