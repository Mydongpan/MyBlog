package com.manong.controller;

import com.manong.domain.ResponseResult;
import com.manong.domain.annotation.SystemLog;
import com.manong.domain.entity.User;
import com.manong.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){

        return userService.getUserInfo();
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUser(@RequestBody User user){

        return userService.updateUser(user);

    }

    @PostMapping("/register")
    public ResponseResult saveUser(@RequestBody User user){

        return userService.saveUser(user);
    }

}
