package com.manong.domain.DTO;

import com.manong.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto extends Article {

    private String CategoryName;
}
