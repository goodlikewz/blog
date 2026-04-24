package com.learning.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learning.blog.entity.BlogUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BlogUserMapper extends BaseMapper<BlogUser> {
}
