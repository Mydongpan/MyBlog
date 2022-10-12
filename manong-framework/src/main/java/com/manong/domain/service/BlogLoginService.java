package com.manong.domain.service;

import com.manong.domain.ResponseResult;
import com.manong.domain.entity.User;

public interface BlogLoginService {

    ResponseResult login(User user);
}
