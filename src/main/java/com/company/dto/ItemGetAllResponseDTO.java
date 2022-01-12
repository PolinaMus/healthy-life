package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemGetAllResponseDTO {
    private List<Item> items;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Item {
        private long id;
        private String name;
        private int price;
        private int qty;
        private String image;
    }
}

