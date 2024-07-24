package com.skywalker.product_command_service.repository;


import com.skywalker.product_command_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
