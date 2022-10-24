package com.manong.domain.vo;

import com.manong.domain.entity.Role;
import com.manong.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoAndRoleIdsVo {

    private User user;
    private List<Role> roleList;
    private List<Long> roleIds;
}
