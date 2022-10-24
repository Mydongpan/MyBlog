package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.User;

public interface UserService extends IService<User> {

    ResponseResult getUserInfo();

    ResponseResult updateUser(User user);

    ResponseResult saveUser(User user);

    ResponseResult userPage(User user,Integer pageNum, Integer pageSize);

    ResponseResult addUser(User user);


}
