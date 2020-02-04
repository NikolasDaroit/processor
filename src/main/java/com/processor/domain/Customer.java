package com.processor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
public class Customer {
    
    private String CNPJ;
    private String name;
    private String businessArea;

}