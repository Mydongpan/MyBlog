package com.manong.controller;

import com.manong.domain.DTO.MenuDto;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.LoginUser;
import com.manong.domain.entity.User;
import com.manong.domain.enums.AppHttpCodeEnum;
import com.manong.domain.exception.SystemException;
import com.manong.domain.service.LoginService;
import com.manong.domain.service.MenuService;
import com.manong.domain.service.RoleService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.utils.RedisCache;
import com.manong.domain.utils.SecurityUtils;
import com.manong.domain.vo.AdminUserInfoVo;
import com.manong.domain.vo.RoutersVo;
import com.manong.domain.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录功能
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){

        if (!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }


    /**
     * 获取用户的权限和角色信息
     * @return
     */
    @GetMapping("/getInfo")
    public ResponseResult getInfo(){
        //获取当前用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        User user = loginUser.getUser();
        Long id = user.getId();

        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(id);
        //根据用户id查询角色信息
        List<String> keys = roleService.selectRoleKeyByUserId(id);

        //将数据封装到指定对象中
        UserInfoVo userInfoVo = BeanCopyUtil.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, keys, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();

        List<MenuDto> menuDtos = menuService.selectRouterMenuTreeByUserId(userId);

        return ResponseResult.okResult(menuDtos);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){

        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject("login" + userId);

        return ResponseResult.okResult();
    }
}
