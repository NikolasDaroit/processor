package com.processor.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Salesperson {
    
    private String CPF;
    private String name;
    private Float salary;

}