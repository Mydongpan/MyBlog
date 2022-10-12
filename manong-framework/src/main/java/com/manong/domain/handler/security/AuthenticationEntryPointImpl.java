package com.manong.domain.handler.security;

import com.alibaba.fastjson.JSON;
import com.manong.domain.ResponseResult;
import com.manong.domain.enums.AppHttpCodeEnum;
import com.manong.domain.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        e.printStackTrace();

        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        //响应给前端
        WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));

    }
}
