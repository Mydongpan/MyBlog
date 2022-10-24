package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.entity.User;
import com.manong.domain.entity.UserRole;
import com.manong.domain.mapper.UserRoleMapper;
import com.manong.domain.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {


    /**
     * 更新用户和角色id关系
     * @param user
     */
    @Override
    public void insertUserRole(User user) {
        //删除原先的数据
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        remove(queryWrapper);

        List<UserRole> userRoleList = Arrays.stream(user.getRoleIds()).map((item) -> {
            UserRole userRole = new UserRole(user.getId(), item);
            return userRole;
        }).collect(Collectors.toList());
        saveBatch(userRoleList);
    }
}
