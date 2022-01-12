package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSaveResponseDTO {
    private Item item;

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
