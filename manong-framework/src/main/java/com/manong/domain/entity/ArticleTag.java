package com.manong.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文章标签关联表(ArticleTag)表实体类
 *
 * @author makejava
 * @since 2022-10-20 15:55:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("/sg_article_tag")
public class ArticleTag {
    //文章id
    private Long articleId;
    //标签id
    private Long tagId;



}

