package com.manong.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.manong.domain.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRolekeysById(Long id);

    List<Long> selectRoleIdByUserId(Long userId);

}
