package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult page(Role role, Integer pageNum, Integer pageSize);

    void insertRole(Role role);

    List<Role> selectAllRole();

    List<Long> selectRoleIdsByUserId(Long id);


}
