package com.practice.product.dto;

import com.practice.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {

  private Long id;

  private String name;

  private Long price;

  private Long quantity;

  public Product toEntity(){
    return Product.builder()
        .name(this.name)
        .price(this.price)
        .quantity(this.quantity)
        .build();
  }

}
