package com.manong.controller;

import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Menu;
import com.manong.domain.service.MenuService;
import com.manong.domain.vo.MenuTreeVo;
import com.manong.domain.vo.RoleMenuTreeSelectVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单列表
     * @param menu
     * @return
     */
    @GetMapping("/list")
    public ResponseResult menuList(Menu menu){

        return menuService.getList(menu);
    }

    /**
     * 新增菜单
     * @param menu
     * @return
     */
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){

        menuService.save(menu);

        return ResponseResult.okResult();
    }

    /**
     * 修改时回显到页面上
     * @param menuId
     * @return
     */
    @GetMapping("/{menuId}")
    public ResponseResult getInfo(@PathVariable Long menuId){

        Menu menu = menuService.getById(menuId);

        return ResponseResult.okResult(menu);
    }

    /**
     * 修改菜单
     * @param menu
     * @return
     */
    @PutMapping
    public ResponseResult edit(@RequestBody Menu menu){

        if (menu.getId().equals(menu.getParentId())){
            return  ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }

        menuService.updateById(menu);

        return ResponseResult.okResult();
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @DeleteMapping("/{menuId}")
    public ResponseResult delete(@PathVariable Long menuId){

        return  menuService.delete(menuId);
    }

    /**
     * 获取菜单下拉列表
     * @return
     */
    @GetMapping("/treeselect")
    public ResponseResult treeselect(){
        List<MenuTreeVo> menuTreeVos = menuService.treeSelect();
        return ResponseResult.okResult(menuTreeVos);

    }

    @GetMapping("/roleMenuTreeselect/{roleId}")
    public ResponseResult roleMenuTreeselect(@PathVariable Long roleId){
        List<Long> checkedKeys = menuService.selectMenuListByRoleId(roleId);
        List<MenuTreeVo> menuTreeVos = menuService.treeSelect();
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(menuTreeVos,checkedKeys);

        return ResponseResult.okResult(vo);

    }


}
