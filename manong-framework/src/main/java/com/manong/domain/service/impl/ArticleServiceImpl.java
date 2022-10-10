package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Article;
import com.manong.domain.mapper.ArticleMapper;
import com.manong.domain.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    /**
     * 根据浏览量获取热度最高的文章
     * @return
     */
    @Override
    public ResponseResult getHotArticleList() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus,0);
        queryWrapper.orderByDesc(Article::getViewCount);

        Page<Article> pageInfo = new Page<>(1,10);
        List<Article> records = page(pageInfo, queryWrapper).getRecords();


        return ResponseResult.okResult(records);
    }
}
