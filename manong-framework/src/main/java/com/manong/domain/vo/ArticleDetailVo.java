package com.manong.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {

    private Long id;
    //标题
    private String title;

    private String categoryName;

    //所属分类id
    private Long categoryId;

    //访问量
    private Long viewCount;

    private Date createTime;

    //文章内容
    private String content;
    //是否允许评论 1是，0否
    private String isComment;


}
