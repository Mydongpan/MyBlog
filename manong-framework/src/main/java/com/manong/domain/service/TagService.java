package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Tag;
import com.manong.domain.vo.PageVo;
import com.manong.domain.vo.TagVo;

public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> getTagListPage(Integer pageNum, Integer pageSize, TagVo tagVo);

    ResponseResult saveTag(Tag tag);

    ResponseResult<TagVo> getAllTag();
//    ResponseResult updateTag(Tag tag);
}
