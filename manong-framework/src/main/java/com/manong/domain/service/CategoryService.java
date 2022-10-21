package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Category;

public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    ResponseResult getAllCategory();
}
