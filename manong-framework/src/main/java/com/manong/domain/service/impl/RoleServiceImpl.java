package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Role;
import com.manong.domain.mapper.RoleMapper;
import com.manong.domain.service.RoleService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.utils.SecurityUtils;
import com.manong.domain.vo.PageVo;
import com.manong.domain.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    /**
     * 获取角色分页列表
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseResult<PageVo> page(Role role, Integer pageNum, Integer pageSize) {
        Page<Role> pageInfo = new Page<>(pageNum,pageSize);

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        //模糊查询
        queryWrapper.like(StringUtils.hasText(role.getRoleKey()),Role::getRoleKey,role.getRoleKey());
        queryWrapper.like(StringUtils.hasText(role.getRoleName()),Role::getRoleName,role.getRoleName());
        queryWrapper.orderByAsc(Role::getRoleSort);
        Page<Role> page = page(pageInfo, queryWrapper);

        List<RoleVo> roleVos = BeanCopyUtil.copyBeanList(pageInfo.getRecords(), RoleVo.class);

        PageVo pageVo = new PageVo(roleVos, pageInfo.getTotal());

        return ResponseResult.okResult(pageVo);
    }
}
