package com.mycompany.ecommerce_microservices.productmanagementservice.repositories;

import com.mycompany.ecommerce_microservices.productmanagementservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategory(String category);
}
