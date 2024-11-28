package com.practice.product.service;

import com.practice.product.dto.ProductDto;
import com.practice.product.entity.Product;
import com.practice.product.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public void save(List<ProductDto> productDto) {
    List<Product> products = productDto.stream().map(ProductDto::toEntity).toList();
    productRepository.saveAll(products);
  }

  public List<ProductDto> getProductInfo(Pageable pageable){
    List<Product> products = Optional.ofNullable(productRepository.findAll(pageable))
        .map(Page::getContent).orElseThrow(()->new IllegalArgumentException("not found"));

    return products.stream().map(Product::toDto).toList();
  }
}
