package com.practice.product.entity;

import com.practice.product.dto.ProductDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String name;

  @Column
  private Long price;

  @Column
  private Long quantity;

  public ProductDto toDto(){
    return ProductDto.builder()
        .id(this.id)
        .name(this.name)
        .price(this.price)
        .quantity(this.quantity)
        .build();
  }

  public void decrease(Long orderQuantity){
    if(quantity - orderQuantity<0){
      throw new IllegalArgumentException();
    }
    this.quantity -= orderQuantity;
  }
}
