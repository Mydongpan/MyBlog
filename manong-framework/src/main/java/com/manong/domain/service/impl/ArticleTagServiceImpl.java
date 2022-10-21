package com.manong.domain.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.manong.domain.entity.ArticleTag;
import com.manong.domain.mapper.ArticleTagMapper;
import com.manong.domain.service.ArticleTagService;
import org.springframework.stereotype.Service;

@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
