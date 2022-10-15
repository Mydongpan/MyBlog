package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.User;
import com.manong.domain.enums.AppHttpCodeEnum;
import com.manong.domain.exception.SystemException;
import com.manong.domain.mapper.UserMapper;
import com.manong.domain.service.UserService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.utils.SecurityUtils;
import com.manong.domain.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
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
    public ResponseResult updateUser(User user) {

        updateById(user);

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
}
