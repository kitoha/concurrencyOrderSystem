package com.practice.product.service;

import com.practice.product.dto.OrderDto;
import com.practice.product.entity.Order;
import com.practice.product.entity.Product;
import com.practice.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

  private final DistributedLock distributedLock;
  private final ProductRepository productRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public void order(List<OrderDto> orderDtos){
    orderDtos.forEach(this::processOrder);
  }

  private void processOrder(OrderDto orderDto){
    String productKey = Long.toString(orderDto.getProductId());
    RLock lock = distributedLock.acquireLock(productKey);
    try {
      Product product = productRepository.findById(orderDto.getProductId())
          .orElseThrow(() -> new IllegalArgumentException("product not found"));
      product.decrease(orderDto.getQuantity());
      productRepository.save(product);
      eventPublisher.publishEvent(lock);
    } catch (Exception e){
      log.error("order failed");
    }
  }

  @Transactional
  public void orderWithLock(OrderDto orderDto){
    try {
      Product product = productRepository.findByIdWithLock(orderDto.getProductId())
          .orElseThrow(() -> new IllegalArgumentException("Product not found"));
      product.decrease(orderDto.getQuantity());
      productRepository.save(product);
    }catch (Exception e) {
      log.error("Order failed for product {}: {}", orderDto.getProductId(), e.getMessage());
    }
  }

}
