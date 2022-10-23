package com.manong.domain.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色和菜单关联表(RoleMenu)表实体类
 *
 * @author makejava
 * @since 2022-10-23 10:39:41
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RoleMenu {
    //角色ID
    private Long roleId;
    //菜单ID
    private Long menuId;

}

