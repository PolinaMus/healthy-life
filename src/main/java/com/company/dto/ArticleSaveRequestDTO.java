package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ArticleSaveRequestDTO {
    private long id;
    private String name;
    private String category;
    private String text;
    private long firstItemId;
    private long secondItemId;
    private long thirdItemId;
    private String image;
}

