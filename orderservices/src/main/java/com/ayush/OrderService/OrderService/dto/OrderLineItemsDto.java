package com.ayush.OrderService.OrderService.dto;

import java.math.BigDecimal;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
    
    private String name ;
    private String barcode ;
    private BigDecimal price ;
    private Integer quantity ;
}
