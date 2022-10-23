package com.manong.domain.vo;

import com.manong.domain.DTO.MenuDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuTreeSelectVo {

    private List<MenuTreeVo> menuTreeVos;
    private List<Long> checkedKeys;
}
