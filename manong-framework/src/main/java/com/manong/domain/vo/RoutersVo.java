package com.manong.domain.vo;

import com.manong.domain.DAO.MenuDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVo {

    private List<MenuDao> menus;

}
