package com.processor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class SaleItem {
    
    private Integer id;
    private Integer quantity;
    private Float price;

}