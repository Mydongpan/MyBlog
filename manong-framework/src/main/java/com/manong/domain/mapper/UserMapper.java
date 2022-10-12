package com.manong.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.manong.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
