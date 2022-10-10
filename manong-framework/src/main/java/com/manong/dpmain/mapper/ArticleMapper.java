package com.manong.dpmain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.manong.dpmain.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
