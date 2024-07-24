package com.skywalker.product_command_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skywalker.product_command_service.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEvent {

    private String eventType;
    private Product product;

}
