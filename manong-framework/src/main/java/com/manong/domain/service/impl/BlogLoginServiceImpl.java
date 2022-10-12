package com.manong.domain.service.impl;

import com.manong.domain.ResponseResult;
import com.manong.domain.entity.LoginUser;
import com.manong.domain.entity.User;
import com.manong.domain.service.BlogLoginService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.utils.JwtUtil;
import com.manong.domain.utils.RedisCache;
import com.manong.domain.vo.BlogUserLoginVo;
import com.manong.domain.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticationToken)){
            throw new RuntimeException("用户名或密码错误");
        }

        //获取userId生成token
        LoginUser loginUser = (LoginUser)authenticationToken.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //把用户信息存入redis
        String jwt = JwtUtil.createJWT(userId);
        redisCache.setCacheObject("bloglogin:" + userId,loginUser);
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(user, UserInfoVo.class);

        //把token和UserInfo封装返回
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);

        return ResponseResult.okResult(vo);
    }
}
