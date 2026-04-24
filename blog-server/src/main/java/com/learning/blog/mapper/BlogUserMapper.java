package com.learning.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learning.blog.entity.BlogUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 后台管理员 Mapper。
 */
@Mapper
public interface BlogUserMapper extends BaseMapper<BlogUser> {
}
