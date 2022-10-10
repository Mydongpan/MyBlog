package com.manong.dpmain.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.dpmain.entity.Article;
import com.manong.dpmain.mapper.ArticleMapper;
import com.manong.dpmain.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {



}
