package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleGetAllResponseDTO {
    private List<Article> articles;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Article {
        private long id;
        private String name;
        private String category;
        private String text;
        private long firstItemId;
        private long secondItemId;
        private long thirdItemId;
        private String image;
    }
}

