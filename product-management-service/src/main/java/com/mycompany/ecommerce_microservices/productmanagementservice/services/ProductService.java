package com.mycompany.ecommerce_microservices.productmanagementservice.services;

import com.mycompany.ecommerce_microservices.productmanagementservice.models.Product;
import com.mycompany.ecommerce_microservices.productmanagementservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService{

    @Autowired
    private ProductRepository productRepository;

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product){
        Product product1 = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product1.setName(product.getName());
        product1.setDescription(product.getDescription());
        product1.setPrice(product.getPrice());
        product1.setCategory(product.getCategory());
        return productRepository.save(product1);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

}
