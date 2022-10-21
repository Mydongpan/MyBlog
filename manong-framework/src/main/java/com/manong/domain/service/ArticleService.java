package com.manong.domain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.manong.domain.DTO.AddArticleVo;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Article;
import com.manong.domain.vo.ArticleVo;

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

    /**
     * 更新文章浏览数
     * @param id
     * @return
     */
    ResponseResult updateViewCount(Long id);

    /**
     * 新增博文
     * @param addarticleVo
     * @return
     */
    ResponseResult addArticle(AddArticleVo addarticleVo);

    /**
     * 获取后台文章列表
     * @param article
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult selectArticleList(Article article,Integer pageNum, Integer pageSize);

    /**
     * 回显文章
     * @param id
     * @return
     */
    ResponseResult getInfo(Long id);

    ResponseResult update(ArticleVo articleVo);
}
