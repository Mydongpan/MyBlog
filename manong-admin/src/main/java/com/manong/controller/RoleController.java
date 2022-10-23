package com.manong.controller;

import com.manong.domain.DTO.ChangeRoleStatusDto;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Role;
import com.manong.domain.entity.RoleMenu;
import com.manong.domain.service.RoleMenuService;
import com.manong.domain.service.RoleService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/system/role")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

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

    /**
     * 新增用户
     * @param role
     * @return
     */
    @PostMapping
    public ResponseResult addRole(@RequestBody Role role){
        roleService.insertRole(role);
        return ResponseResult.okResult();
    }

    /**
     * 页面回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult selectById(@PathVariable Long id){
        Role role = roleService.getById(id);
        return ResponseResult.okResult(role);
    }

    /**
     * 保存修改数据
     * @param role
     * @return
     */
    @PutMapping
    public ResponseResult edit(@RequestBody Role role){

        roleService.updateById(role);
        if (role.getMenuIds() != null && role.getMenuIds().length > 0){
            roleMenuService.updateByMenu(role);
        }
        return ResponseResult.okResult();
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
        roleService.removeById(id);
        return ResponseResult.okResult();
    }
}
