package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.DAO.MenuDao;
import com.manong.domain.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<MenuDao> selectRouterMenuTreeByUserId(Long userId);
}
