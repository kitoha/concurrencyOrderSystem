package com.practice.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "orders")
public class Order {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private Long productId;

  @Column
  private Long quantity;

}
