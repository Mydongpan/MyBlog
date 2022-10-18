package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.DAO.MenuDao;
import com.manong.domain.contants.SystemContants;
import com.manong.domain.entity.Menu;
import com.manong.domain.mapper.MenuMapper;
import com.manong.domain.service.MenuService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    /**
     * 根据id查询用户权限
     * @param id
     * @return
     */
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果时管理员，返回所有权限
        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(Menu::getMenuType, SystemContants.MENU,SystemContants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemContants.STATUS_NORMAL);
            List<Menu> menuList = list(queryWrapper);
            List<String> perms = menuList.stream().map(Menu::getPerms).collect(Collectors.toList());

            return perms;
        }
        //否则返回所具有的权限
        List<String> perms = getBaseMapper().selectPermsByUserId(id);
        return perms;
    }


    @Override
    public List<MenuDao> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper baseMapper = getBaseMapper();
        List<Menu> menuList = null;
        //判断是否是管理员
        if (SecurityUtils.isAdmin()){
            //如果是管理员，就返回所有的
            menuList = baseMapper.selectAllRouterMenu();
        }else{
            //否则返回该用户的menu
            menuList = baseMapper.selectRouterMenuTreeByUserId(userId);
        }

        List<MenuDao> menuDaoList = BeanCopyUtil.copyBeanList(menuList, MenuDao.class);
        //构建tree

        List<MenuDao> menuTree = builderMenuTree(menuDaoList, SystemContants.parentId);
        return menuTree;
    }

    /**
     * 进行Tree构造
     * @param menuDaoList
     * @param parentId
     * @return
     */
    private List<MenuDao> builderMenuTree(List<MenuDao> menuDaoList, Long parentId){
        List<MenuDao> menuTree = menuDaoList.stream()
                .filter(menuDao -> menuDao.getParentId().equals(parentId))
                .map((item) -> {
                    item.setChildren(getChildren(item, menuDaoList));
                    return item;
                })
                .collect(Collectors.toList());

        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menuDao
     * @param menuDaoList
     * @return
     */
    private List<MenuDao> getChildren(MenuDao menuDao, List<MenuDao> menuDaoList) {
        List<MenuDao> menuDaos = menuDaoList.stream().filter((item) -> {
            return item.getParentId().equals(menuDao.getId());
        }).collect(Collectors.toList());

        List<MenuDao> childrenList = menuDaos.stream().map((item) -> {
            item.setChildren(getChildren(menuDao,menuDaoList));
            return item;
        }).collect(Collectors.toList());

        return childrenList;
    }


}
