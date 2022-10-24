package com.manong.controller;

import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Role;
import com.manong.domain.entity.User;
import com.manong.domain.service.RoleService;
import com.manong.domain.service.UserRoleService;
import com.manong.domain.service.UserService;
import com.manong.domain.utils.SecurityUtils;
import com.manong.domain.vo.UserInfoAndRoleIdsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 获取用户列表
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public ResponseResult page(User user,Integer pageNum, Integer pageSize){
        return userService.userPage(user,pageNum,pageSize);

    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public ResponseResult addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    /**
     * 根据id删除用户
     * @param userIds
     * @return
     */
    @DeleteMapping("/{userIds}")
    public ResponseResult remove(@PathVariable List<Long> userIds) {
        if(userIds.contains(SecurityUtils.getUserId())){
            return ResponseResult.errorResult(500,"不能删除当前你正在使用的用户");
        }
        userService.removeByIds(userIds);
        return ResponseResult.okResult();
    }

    /**
     * 根据用户id获取详细信息
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseResult getUserInfoAndRoleIds(@PathVariable Long userId){
        List<Role> roleList = roleService.selectAllRole();
        User user = userService.getById(userId);
        List<Long> roleIds = roleService.selectRoleIdsByUserId(userId);
        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user, roleList, roleIds);
        return ResponseResult.okResult(vo);
    }

    @PutMapping
    public ResponseResult update(@RequestBody User user){

        userService.updateUser(user);


        return ResponseResult.okResult();
    }
}
