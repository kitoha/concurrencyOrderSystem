package com.practice.product.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDto {

  private Long productId;

  private Long quantity;

}
