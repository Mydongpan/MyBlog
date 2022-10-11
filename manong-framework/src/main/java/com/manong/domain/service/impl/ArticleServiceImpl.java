package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Article;
import com.manong.domain.mapper.ArticleMapper;
import com.manong.domain.service.ArticleService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.vo.ArticleVo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        //得到查询到的数据集合
        List<Article> records = page(pageInfo, queryWrapper).getRecords();


        List<ArticleVo> articleVoList = BeanCopyUtil.copyBeanList(records, ArticleVo.class);
        //使用Stream流的方式将Article复制到ArticleVo中去
//        List<ArticleVo> articleVoList = records.stream().map((item) ->{
//            ArticleVo articleVo = new ArticleVo();
//            BeanUtils.copyProperties(item,articleVo);
//            return articleVo;
//        }).collect(Collectors.toList());


        return ResponseResult.okResult(articleVoList);
    }
}
