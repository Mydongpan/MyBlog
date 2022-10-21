package com.manong.controller;

import com.manong.domain.DTO.AddArticleVo;
import com.manong.domain.ResponseResult;

import com.manong.domain.entity.Article;
import com.manong.domain.service.ArticleService;
import com.manong.domain.vo.ArticleVo;
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

    @GetMapping("/list")
    public ResponseResult list(Article article, Integer pageNum, Integer pageSize){
        return articleService.selectArticleList(article,pageNum,pageSize);
    }

    @GetMapping("/{id}")
    public ResponseResult getInfo(@PathVariable Long id){

        return articleService.getInfo(id);

    }

    /**
     * 将修改打的数据更新到数据库中
     * @param articleVo
     * @return
     */
    @PutMapping
    public ResponseResult edit(@RequestBody ArticleVo articleVo){

        return articleService.update(articleVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){

        articleService.removeById(id);

        return ResponseResult.okResult();
    }
}
