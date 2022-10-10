package com.manong.dpmain;

import com.manong.dpmain.entity.Article;
import com.manong.dpmain.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    
    @GetMapping("/list")
    public List<Article> list(){

        List<Article> articleList = articleService.list();

        return articleList;
    }
    
}
