package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Tag;
import com.manong.domain.mapper.TagMapper;
import com.manong.domain.service.TagService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.vo.PageVo;
import com.manong.domain.vo.TagVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    /**
     * 获取标签列表
     * @param pageNum
     * @param pageSize
     * @param tagVo
     * @return
     */
    @Override
    public ResponseResult<PageVo> getTagListPage(Integer pageNum, Integer pageSize, TagVo tagVo) {

        Page<Tag> pageInfo = new Page(pageNum,pageSize);

        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        //如果tagVo不为空
        queryWrapper.eq(StringUtils.hasText(tagVo.getName()),Tag::getName,tagVo.getName());
        queryWrapper.eq(StringUtils.hasText(tagVo.getRemark()),Tag::getRemark,tagVo.getRemark());

        Page<Tag> tagPage = page(pageInfo, queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(tagPage.getRecords(), tagPage.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 新增用户
     * @param tag
     * @return
     */
    @Override
    public ResponseResult saveTag(Tag tag) {
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<TagVo> getAllTag() {

        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getName);
        List<Tag> tagList = list(queryWrapper);

        List<TagVo> tagVoList = BeanCopyUtil.copyBeanList(tagList, TagVo.class);

        return ResponseResult.okResult(tagVoList);
    }

    /**
     * 修改用户信息
     * @param tag
     * @return
     */
//    @Override
//    public ResponseResult updateTag(Tag tag) {
//        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Tag::getName,tag.getName());
//        queryWrapper.eq(Tag::getRemark,tag.getRemark());
//
//        update(tag,queryWrapper);
//
//        return null;
//    }
}
