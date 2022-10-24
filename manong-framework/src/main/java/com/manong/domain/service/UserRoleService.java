package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.entity.User;
import com.manong.domain.entity.UserRole;

public interface UserRoleService extends IService<UserRole> {

    void insertUserRole(User user);

}
