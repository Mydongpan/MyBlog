package com.manong.controller;

import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Article;
import com.manong.domain.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    
//    @GetMapping("/list")
//    public List<Article> list(){
//
//        List<Article> articleList = articleService.list();
//
//        return articleList;
//    }

    /**
     * 获取热门文章列表
     * @return
     */
    @GetMapping("/hotArticleList")
    public ResponseResult<Article> list(){

      return  articleService.getHotArticleList();

    }


    @GetMapping("/articleList")
    public ResponseResult articleList(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Long categoryId){
        ResponseResult articleList = articleService.getArticleList(pageNum, pageSize, categoryId);
        return articleList;
    }


    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable Long id){

        return articleService.getArticleDetail(id);
    }
}
