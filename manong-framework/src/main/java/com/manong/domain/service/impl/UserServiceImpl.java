package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.User;
import com.manong.domain.entity.UserRole;
import com.manong.domain.enums.AppHttpCodeEnum;
import com.manong.domain.exception.SystemException;
import com.manong.domain.mapper.UserMapper;
import com.manong.domain.service.RoleMenuService;
import com.manong.domain.service.UserRoleService;
import com.manong.domain.service.UserService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.utils.SecurityUtils;
import com.manong.domain.vo.PageVo;
import com.manong.domain.vo.UserInfoVo;
import com.manong.domain.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleService userRoleService;


    /**
     * 查询个人信息
     * @return
     */
    @Override
    public ResponseResult getUserInfo() {

        Long userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(user, UserInfoVo.class);

        return ResponseResult.okResult(userInfoVo);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ResponseResult updateUser(User user) {

        updateById(user);
        userRoleService.insertUserRole(user);
        return ResponseResult.okResult();
    }

    /**
     * 新增用户信息
     * @param user
     * @return
     */
    @Override
    public ResponseResult saveUser(User user) {
        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //等等
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
        return ResponseResult.okResult();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 获取用户列表
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseResult userPage(User user, Integer pageNum, Integer pageSize) {
        Page<User> pageInfo = new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //根据添加能进行查询
        queryWrapper.like(StringUtils.hasText(user.getUserName()),User::getUserName,user.getUserName());
        queryWrapper.eq(StringUtils.hasText(user.getStatus()),User::getStatus,user.getStatus());
        queryWrapper.eq(StringUtils.hasText(user.getPhonenumber()),User::getPhonenumber,user.getPhonenumber());
        //封装数据
        Page<User> userPage = page(pageInfo, queryWrapper);
        List<UserVo> userVos = BeanCopyUtil.copyBeanList(userPage.getRecords(), UserVo.class);
        PageVo pageVo = new PageVo(userVos, pageInfo.getTotal());

        return ResponseResult.okResult(pageVo);

    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @Override
    @Transactional
    public ResponseResult addUser(User user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        save(user);
        //如果关联了其他角色
        if (user.getRoleIds() != null && user.getRoleIds().length > 0){
            List<UserRole> userRoleList = Arrays.stream(user.getRoleIds()).map((item) -> {
                UserRole userRole = new UserRole(user.getId(), item);
                return userRole;
            }).collect(Collectors.toList());

            userRoleService.saveBatch(userRoleList);
        }
        return ResponseResult.okResult();
    }
}
