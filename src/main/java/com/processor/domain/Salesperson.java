package com.processor.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class Salesperson {
    
    private String CPF;
    private String name;
    private Float salary;

}