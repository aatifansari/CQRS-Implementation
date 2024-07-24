package com.skywalker.product_query_service.service;

import com.skywalker.product_query_service.dto.ProductEvent;
import com.skywalker.product_query_service.entity.Product;
import com.skywalker.product_query_service.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryService {

    @Autowired
    private ProductRepository productRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductQueryService.class);

    public List<Product> getProducts(){
        return productRepository.findAll();
    }

    @KafkaListener(topics = "product-event-topic", groupId = "product-event-group",
            containerFactory = "productKafkaListenerContainerFactory")
    public void processProductEvents(ProductEvent productEvent){
        LOGGER.info("Product Event {}", productEvent);
        Product product = productEvent.getProduct();
        if(productEvent.getEventType().equalsIgnoreCase("CreateProduct")){
            productRepository.save(product);
        } else if (productEvent.getEventType().equalsIgnoreCase("UpdateProduct")) {
            Product existingProduct = productRepository.findById(productEvent.getProduct().getId()).orElseThrow(EntityNotFoundException::new);
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getName());
            existingProduct.setPrice(product.getPrice());
            productRepository.save(existingProduct);
        }
    }

}
