package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.ResponseResult;
import com.manong.domain.contants.SystemContants;
import com.manong.domain.entity.Link;
import com.manong.domain.mapper.LinkMapper;
import com.manong.domain.service.LinkService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.vo.LinkVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {


    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        //进行校验，是否通过审核
        queryWrapper.eq(Link::getStatus, SystemContants.LINK_STATUS_NORMAL);

        List<Link> linkList = list(queryWrapper);

        List<LinkVo> linkVos = BeanCopyUtil.copyBeanList(linkList, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}
