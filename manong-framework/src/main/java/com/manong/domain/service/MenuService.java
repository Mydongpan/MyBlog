package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.DTO.MenuDto;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Menu;
import com.manong.domain.vo.MenuTreeVo;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuDto> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getList(Menu menu);

    ResponseResult delete(Long menuId);

    List<MenuTreeVo> treeSelect();

    List<Long> selectMenuListByRoleId(Long roleid);


}
