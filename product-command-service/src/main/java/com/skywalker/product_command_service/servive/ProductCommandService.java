package com.skywalker.product_command_service.servive;

import com.skywalker.product_command_service.dto.ProductEvent;
import com.skywalker.product_command_service.entity.Product;
import com.skywalker.product_command_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, ProductEvent> kafkaTemplate;

    public Product createProduct(Product product){
        Product savedProduct = productRepository.save(product);
        ProductEvent productEvent = new ProductEvent("CreateProduct", savedProduct);
        kafkaTemplate.send("product-event-topic", productEvent);
        return savedProduct;
    }

    public Product updateProduct(long id, Product product){
        Product existingProduct = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getName());
        existingProduct.setPrice(product.getPrice());
        Product updatedProduct =  productRepository.save(existingProduct);
        ProductEvent productEvent = new ProductEvent("UpdateProduct", updatedProduct);
        kafkaTemplate.send("product-event-topic", productEvent);
        return updatedProduct;
    }
}
