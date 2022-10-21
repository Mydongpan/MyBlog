package com.manong.controller;

import com.manong.domain.DTO.AddArticleVo;
import com.manong.domain.ResponseResult;

import com.manong.domain.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("content/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleVo article){
        return articleService.addArticle(article);
    }
}
