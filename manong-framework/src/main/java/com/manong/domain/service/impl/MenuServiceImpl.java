package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.DTO.MenuDto;
import com.manong.domain.ResponseResult;
import com.manong.domain.contants.SystemContants;
import com.manong.domain.entity.Menu;
import com.manong.domain.mapper.MenuMapper;
import com.manong.domain.service.MenuService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.utils.SecurityUtils;
import com.manong.domain.utils.SystemConverter;
import com.manong.domain.vo.MenuTreeVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public List<MenuDto> selectRouterMenuTreeByUserId(Long userId) {
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

        List<MenuDto> menuDtoList = BeanCopyUtil.copyBeanList(menuList, MenuDto.class);

        //构建tree
        List<MenuDto> menuTree = builderMenuTree(menuDtoList, SystemContants.parentId);
        return menuTree;
    }

    /**
     * 进行Tree构造
     * @param menuDtoList
     * @param parentId
     * @return
     */
    private List<MenuDto> builderMenuTree(List<MenuDto> menuDtoList, Long parentId){
        List<MenuDto> menuTree = menuDtoList.stream()
                .filter(menuDao -> menuDao.getParentId().equals(parentId))
                .map((item) -> {
                    item.setChildren(getChildren(item, menuDtoList));
                    return item;
                })
                .collect(Collectors.toList());

        return menuTree;
    }

    /**
     * 获取存入参数的 子Menu集合
     * @param menuDto
     * @param menuDtoList
     * @return
     */
    private List<MenuDto> getChildren(MenuDto menuDto, List<MenuDto> menuDtoList) {
        List<MenuDto> menuDtos = menuDtoList.stream().filter((item) -> {
            return item.getParentId().equals(menuDto.getId());
        }).collect(Collectors.toList());

        List<MenuDto> childrenList = menuDtos.stream().map((item) -> {
            item.setChildren(getChildren(menuDto, menuDtoList));
            return item;
        }).collect(Collectors.toList());

        return childrenList;
    }


    @Override
    public ResponseResult getList(Menu menu) {
        //查询所有的菜单
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //根据menuName和status进行模糊查询
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,SystemContants.STATUS_NORMAL);
        //排序 parent_id和order_num
        queryWrapper.orderByDesc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menuList = list(queryWrapper);
        //如果有子菜单，将子菜单添加进去
        List<MenuDto> menuDtos = BeanCopyUtil.copyBeanList(menuList, MenuDto.class);

        return ResponseResult.okResult(menuDtos);
    }

    /**
     * 获取菜单树
     * @return
     */
    @Override
    public List<MenuTreeVo> treeSelect() {

        List<Menu> menuList = list();
        //如果有子菜单，将子菜单添加进去
        List<MenuDto> menuDtos = BeanCopyUtil.copyBeanList(menuList, MenuDto.class);
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menuDtos);

        return menuTreeVos;
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @Override
    public ResponseResult delete(Long menuId) {

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Menu::getId,menuId);
        Menu menu = getOne(queryWrapper);

        if (menuId != menu.getParentId()){
            removeById(menuId);
            return ResponseResult.okResult();
        }else {
            //如果有子菜单，提示错误
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
    }

    public List<Long> selectMenuListByRoleId(Long roleId){
       return getBaseMapper().selectMenuListByRoleId(roleId);
    }
}
