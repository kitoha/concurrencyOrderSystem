package com.practice.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Order {

  @Id
  @GeneratedValue
  private Long id;

  private String productName;

  private Long quantity;

}
