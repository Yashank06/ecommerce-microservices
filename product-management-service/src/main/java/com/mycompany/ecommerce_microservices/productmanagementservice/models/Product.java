package com.mycompany.ecommerce_microservices.productmanagementservice.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Products")
@EntityListeners(AuditingEntityListener.class)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private Double price;

    @CreatedBy
    @Column(nullable = false)
    private String addby;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime addedtime;

    @LastModifiedBy
    @Column(nullable = false)
    private String modifyby;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifytime;
}
