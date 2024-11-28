package com.practice.product.controller;

import com.practice.product.dto.ProductDto;
import com.practice.product.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping(path = "/api/v1/product")
  public List<ProductDto> getProductInfo(Pageable pageable){
    return productService.getProductInfo(pageable);
  }

  @PostMapping(path = "/api/v1/product")
  public void save(@RequestBody List<ProductDto> productDtos){
    productService.save(productDtos);
  }


}
