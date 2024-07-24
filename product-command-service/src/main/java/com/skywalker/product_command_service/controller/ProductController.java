package com.skywalker.product_command_service.controller;

import com.skywalker.product_command_service.dto.ProductEvent;
import com.skywalker.product_command_service.entity.Product;
import com.skywalker.product_command_service.servive.ProductCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductCommandService productService;

    @PostMapping("/save")
    public ResponseEntity<Product> createproduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product){
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

}
