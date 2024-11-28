package com.practice.product.controller;

import com.practice.product.dto.OrderDto;
import com.practice.product.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping(path = "/api/v1/order")
  public void order(List<OrderDto> orderDtos){
    orderService.order(orderDtos);
  }

}
