package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Link;

public interface LinkService extends IService<Link> {

    /**
     * 获取所有友链
     * @return
     */
    ResponseResult getAllLink();
}
