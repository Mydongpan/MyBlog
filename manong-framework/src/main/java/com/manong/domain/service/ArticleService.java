package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {

    ResponseResult getHotArticleList();


}
