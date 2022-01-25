package com.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleGetAllResponseDTO {
    private List<Sale> sales;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Sale{
        private long id;
        private String name;
        private int price;
        private int qty;
    }
}
