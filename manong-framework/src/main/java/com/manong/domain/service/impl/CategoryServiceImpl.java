package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.ResponseResult;
import com.manong.domain.contants.SystemContants;
import com.manong.domain.entity.Article;
import com.manong.domain.entity.Category;
import com.manong.domain.mapper.CategoryMapper;
import com.manong.domain.service.ArticleService;
import com.manong.domain.service.CategoryService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.vo.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    /**
     * 查询分类列表，要求有正式文章发布的
     * @return
     */
    @Override
    public ResponseResult getCategoryList() {
        //查询文章对应的分类id
        LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
        //select category_id from article where status = 0
        articleQueryWrapper.eq(Article::getStatus, SystemContants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleQueryWrapper);
        //查询分类id,并且去除重复分类id
        Set<Long> categoryIds = articleList.stream().map((item) ->{
           Long categoryId = item.getCategoryId();
           return categoryId;
        }).collect(Collectors.toSet());

        //通过分类id，得到分类名称
        List<Category> categoryList = listByIds(categoryIds);
        categoryList = categoryList.stream().filter((item) ->{
            boolean equals = SystemContants.STATUS_NORMAL.equals(item.getStatus());
            return equals;
        }).collect(Collectors.toList());

        List<CategoryVo> categoryVoList = BeanCopyUtil.copyBeanList(categoryList, CategoryVo.class);


        return ResponseResult.okResult(categoryVoList);
    }

    /**
     * 获取所有的标签
     * @return
     */
    @Override
    public ResponseResult<CategoryVo> getAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Category::getStatus,SystemContants.STATUS_NORMAL);
        List<Category> categoryList = list(queryWrapper);
        List<CategoryVo> categoryVoList = BeanCopyUtil.copyBeanList(categoryList, CategoryVo.class);

        return ResponseResult.okResult(categoryVoList);
    }


}
