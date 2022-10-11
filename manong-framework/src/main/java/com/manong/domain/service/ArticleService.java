package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {

    /**
     * 获取热门文章列表
     * @return
     */
    ResponseResult getHotArticleList();

    /**
     * 首页和分类页面查询文章列表
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    ResponseResult getArticleList(Integer pageNum, Integer pageSize, Long categoryId);

    /**
     * 获取文章主体
     * @param id
     * @return
     */
    ResponseResult getArticleDetail(Long id);
}
