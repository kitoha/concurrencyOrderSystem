package com.practice.product.service;

import com.practice.product.dto.OrderDto;
import com.practice.product.entity.Product;
import com.practice.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final DistributedLock distributedLock;
  private final ProductRepository productRepository;

  @Transactional
  public void order(List<OrderDto> orderDtos){
    orderDtos.forEach(this::processOrder);
  }

  private void processOrder(OrderDto orderDto){
    String productKey = Long.toString(orderDto.getProductId());
    if(distributedLock.getLock(productKey)) {
      Product product = productRepository.findById(orderDto.getProductId())
          .orElseThrow(() -> new IllegalArgumentException("product not found"));
      product.decrease(orderDto.getQuantity());
    }
  }

}
