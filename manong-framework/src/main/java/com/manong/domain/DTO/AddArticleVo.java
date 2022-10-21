package com.manong.domain.DTO;

import com.manong.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddArticleVo extends Article {

    private List<Long> tags;

}
