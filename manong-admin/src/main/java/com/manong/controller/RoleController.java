package com.manong.controller;

import com.manong.domain.DTO.ChangeRoleStatusDto;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Role;
import com.manong.domain.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/system/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult page(Role role, Integer pageNum, Integer pageSize){

        return roleService.page(role,pageNum,pageSize);
    }

    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto roleStatusDto){

        Role role = new Role();
        role.setId(roleStatusDto.getId());
        role.setStatus(roleStatusDto.getStatus());

        roleService.updateById(role);
        return ResponseResult.okResult();
    }


}
