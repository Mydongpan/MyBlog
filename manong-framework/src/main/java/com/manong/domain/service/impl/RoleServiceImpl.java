package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.entity.Role;
import com.manong.domain.mapper.RoleMapper;
import com.manong.domain.service.RoleService;
import com.manong.domain.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleService roleService;

    /**
     * 根据id查询用户角色信息
     * @param id
     * @return
     */
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //如果是管理员，只需要返回admin
        if (SecurityUtils.isAdmin()){
            List<String> rolekeys = new ArrayList<>();
            rolekeys.add("admin");
        }
        //否则查询用户所具有的角色信息,并返回
        List<String> rolekeys = getBaseMapper().selectRolekeysById(id);
        return rolekeys;
    }
}
