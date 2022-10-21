package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.DTO.AddArticleVo;
import com.manong.domain.DTO.ArticleDto;
import com.manong.domain.ResponseResult;
import com.manong.domain.contants.SystemContants;
import com.manong.domain.entity.Article;
import com.manong.domain.entity.ArticleTag;
import com.manong.domain.entity.Category;
import com.manong.domain.mapper.ArticleMapper;
import com.manong.domain.service.ArticleService;
import com.manong.domain.service.ArticleTagService;
import com.manong.domain.service.CategoryService;
import com.manong.domain.utils.BeanCopyUtil;
import com.manong.domain.utils.RedisCache;
import com.manong.domain.vo.ArticleDetailVo;
import com.manong.domain.vo.ArticleListVo;
import com.manong.domain.vo.ArticleVo;

import com.manong.domain.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;

    /**
     * 获取文章主体
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {

        Article article = getById(id);
        ArticleDetailVo articleDetailVo = BeanCopyUtil.copyBean(article, ArticleDetailVo.class);

        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

    /**
     * 更新浏览数
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应的 id的浏览量
        redisCache.incrementCacheMapValue(SystemContants.VIEW_COUNT,id.toString(),1);
        return ResponseResult.okResult();

    }

    /**
     * 新增博文
     * @param addarticle
     * @return
     */
    @Override
    public ResponseResult addArticle(AddArticleVo addarticle) {
        Article article = BeanCopyUtil.copyBean(addarticle, Article.class);
        save(article);

        //将文章和标签的关系保存
        List<ArticleTag> articleTags = addarticle.getTags().stream().map((item) -> {
            ArticleTag articleTag = new ArticleTag(article.getId(), item);
            return articleTag;
        }).collect(Collectors.toList());

        articleTagService.saveBatch(articleTags);

        return ResponseResult.okResult();
    }

    /**
     * 获取后端文章列表
     * @param article
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public ResponseResult selectArticleList(Article article,Integer pageNum, Integer pageSize) {

        Page<Article> pageInfo = new Page<>(pageNum,pageSize);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(article.getTitle()),Article::getTitle,article.getTitle());
        queryWrapper.like(StringUtils.hasText(article.getSummary()),Article::getSummary,article.getSummary());
        Page<Article> articlePage = page(pageInfo, queryWrapper);

        PageVo pageVo = new PageVo(pageInfo.getRecords(), pageInfo.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 后端回显并且修改文章
     * @param id
     * @return
     */
    @Override
    public ResponseResult getInfo(Long id) {

        Article article = getById(id);

        //查询与该文章相关的标签
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> articleTagList = articleTagService.list(queryWrapper);

        List<Long> tagList = articleTagList.stream().map(articleTag -> articleTag.getTagId())
                .collect(Collectors.toList());

        ArticleVo articleVo = BeanCopyUtil.copyBean(article, ArticleVo.class);
        articleVo.setTags(tagList);

        return ResponseResult.okResult(articleVo);
    }

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

    /**
     * 首页和分类页面查询文章列表
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    public ResponseResult getArticleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //创建分页查询构造器
        Page<Article> pageInfo = new Page(pageNum,pageSize);

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId就作为添加到查询条件
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,Article::getCategoryId,categoryId);
        queryWrapper.eq(Article::getStatus, SystemContants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        page(pageInfo,queryWrapper);

        List<Article> articleList = pageInfo.getRecords();
        List<ArticleDto> articleDtoList = BeanCopyUtil.copyBeanList(articleList, ArticleDto.class);

        articleDtoList = articleDtoList.stream().map((item) ->{
            //设置分类id
            item.setCategoryName(categoryService.getById(item.getCategoryId()).getName());
            return item;
        }).collect(Collectors.toList());

        List<ArticleListVo> articleListVos = BeanCopyUtil.copyBeanList(articleDtoList, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVos, pageInfo.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 修改文章
     * @param articleVo
     * @return
     */
    @Override
    public ResponseResult update(ArticleVo articleVo) {

        Article article = BeanCopyUtil.copyBean(articleVo, Article.class);
        updateById(article);

        //删除原来文章与标签的对应关系
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(queryWrapper);
        //添加新的博客和标签的关联信息
        List<Long> tags = articleVo.getTags();
        List<ArticleTag> articleTags = tags.stream().map((item) -> {
            ArticleTag articleTag = new ArticleTag(articleVo.getId(), item);
            return articleTag;
        }).collect(Collectors.toList());

        articleTagService.saveBatch(articleTags);


        return ResponseResult.okResult();
    }
}
