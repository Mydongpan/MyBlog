package com.manong.domain.controller;

import com.manong.domain.ResponseResult;
import com.manong.domain.entity.Article;
import com.manong.domain.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/hotArticleList")
    public ResponseResult<Article> list(){

      return  articleService.getHotArticleList();

    }

}
