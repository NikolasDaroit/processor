package com.processor.parser;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class Sale {
    
    private Integer id;
    private String items;
    private String salespersonName;
    private List<SaleItem> saleItems;
    
    public Float getTotalSale(){
        Float total = 0.0f;
        for (SaleItem saleItem : saleItems) {
            total = saleItem.getPrice() *saleItem.getQuantity();
        }
        return total;
    }

}