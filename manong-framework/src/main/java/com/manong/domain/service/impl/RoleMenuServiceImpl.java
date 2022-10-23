package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.entity.Menu;
import com.manong.domain.entity.Role;
import com.manong.domain.entity.RoleMenu;
import com.manong.domain.mapper.RoleMenuMapper;
import com.manong.domain.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {


//    @Override
//    public void updateByMenu(Menu menu) {
//        //将原来的数据删除
//        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(RoleMenu::getMenuId,menu.getId());
//        remove(queryWrapper);
//
//    }

    private List<RoleMenu> getRoleMenuList(Role role){

        List<RoleMenu> roleMenuList = new ArrayList<>();

        for (Long menuId : role.getMenuIds()) {
            RoleMenu roleMenu = new RoleMenu(role.getId(), menuId);
            roleMenuList.add(roleMenu);
        }
        return roleMenuList;
    }

    @Override
    public void updateByMenu(Role role) {
        //删除原来对应的信息
        Long[] menuIds = role.getMenuIds();
        List<Long> menuIdList = Arrays.stream(menuIds).collect(Collectors.toList());
        LambdaQueryWrapper<RoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        removeByIds(menuIdList);

        saveBatch(getRoleMenuList(role));
    }
}
