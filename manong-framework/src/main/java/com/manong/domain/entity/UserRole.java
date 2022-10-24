package com.manong.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author makejava
 * @since 2022-10-24 12:56:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_role")
public class UserRole {
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;



}

