package com.manong.runner;

import com.manong.domain.contants.SystemContants;
import com.manong.domain.entity.Article;
import com.manong.domain.mapper.ArticleMapper;
import com.manong.domain.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id viewCount
        List<Article> articleList = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articleList.stream().
                collect(Collectors.toMap(article -> article.getId().toString(), article -> {
                    return article.getViewCount().intValue();
                }));
//        Map<String, Integer> viewCountMap = articleList.stream()
//                .collect(Collectors.toMap(article -> article.getId().toString(), article -> {
//                    return article.getViewCount().intValue();
//                }));
        //将数据储存到redis中
        redisCache.setCacheMap(SystemContants.VIEW_COUNT,viewCountMap);

        System.out.println("成功加入redis");
    }
}
