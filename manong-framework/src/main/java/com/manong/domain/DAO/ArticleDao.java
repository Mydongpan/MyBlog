package com.manong.domain.DAO;

import com.manong.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDao extends Article {

    private String CategoryName;
}
