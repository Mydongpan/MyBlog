package com.manong.domain.utils;

import com.manong.domain.DTO.MenuDto;
import com.manong.domain.contants.SystemContants;
import com.manong.domain.vo.MenuTreeVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SystemConverter {

    private SystemConverter(){

    }

    public static List<MenuTreeVo> buildMenuSelectTree(List<MenuDto> menuDtos){

        List<MenuTreeVo> menuTreeVos = menuDtos.stream()
                .map(menuDto -> new MenuTreeVo(menuDto.getId(), menuDto.getMenuName(), menuDto.getParentId(), null))
                .collect(Collectors.toList());
        List<MenuTreeVo> treeVos = menuTreeVos.stream().filter(menuTreeVo -> menuTreeVo.getParentId().equals(SystemContants.parentId))
                .map(menuTreeVo -> menuTreeVo.setChildren(getChildList(menuTreeVos, menuTreeVo)))
                .collect(Collectors.toList());

        return treeVos;
    }


    /**
     * 得到子节点列表
     */
    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> list, MenuTreeVo option) {
        List<MenuTreeVo> options = list.stream()
                .filter(o -> Objects.equals(o.getParentId(), option.getId()))
                .map(o -> o.setChildren(getChildList(list, o)))
                .collect(Collectors.toList());
        return options;

    }
}
