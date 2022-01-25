package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleGetByIdResponseDTO {
    private Sale sale;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Sale {
        private long id;
        private String name;
        private int price;
        private int qty;
    }
}
