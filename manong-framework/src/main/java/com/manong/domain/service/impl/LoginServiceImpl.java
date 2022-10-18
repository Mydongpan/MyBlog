package com.manong.domain.service.impl;

import com.manong.domain.ResponseResult;
import com.manong.domain.entity.LoginUser;
import com.manong.domain.entity.User;
import com.manong.domain.service.LoginService;
import com.manong.domain.utils.JwtUtil;
import com.manong.domain.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //判断是否通过
        if (Objects.isNull(authentication)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userId，生成token
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        String userId = loginUser.getUser().getId().toString();

        //生成token
        String token = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject("login" + userId,user.getPassword());

        Map<String,String> map = new HashMap<>();
        map.put("token",token);

        return ResponseResult.okResult(map);
    }
}
