package com.manong.job;

import com.manong.domain.contants.SystemContants;
import com.manong.domain.entity.Article;
import com.manong.domain.service.ArticleService;
import com.manong.domain.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 从redis中获取数据更新到数据库中
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount(){

        //获取redis中的浏览数

       Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemContants.VIEW_COUNT);

        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        //更新到数据库中
        articleService.updateBatchById(articles);
    }
}
