package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.entity.Menu;
import com.manong.domain.entity.Role;
import com.manong.domain.entity.RoleMenu;

public interface RoleMenuService extends IService<RoleMenu> {

    void updateByMenu(Role role);
}
