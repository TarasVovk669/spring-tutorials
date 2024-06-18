package com.reactive.product.productreactiveservice.controller;

import com.reactive.product.productreactiveservice.dto.ProductDto;
import com.reactive.product.productreactiveservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  private final Flux<ProductDto> flux;

  @GetMapping
  public Flux<ProductDto> getAll() {
    return productService.getAll();
  }

  @GetMapping("/price-range")
  public Flux<ProductDto> getAllByRange(
      @RequestParam("min") Integer min, @RequestParam("max") Integer max) {
    return productService.getAllByRange(min, max);
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<ProductDto>> getOne(@PathVariable("id") String id) {
    return productService
        .getOne(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  public Mono<ProductDto> save(@RequestBody Mono<ProductDto> productDtoMono) {
    return productService.save(productDtoMono);
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<ProductDto>> save(
      @PathVariable("id") String id, @RequestBody Mono<ProductDto> productDtoMono) {
    return productService
        .update(id, productDtoMono)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable("id") String id) {
    return productService.delete(id);
  }

  @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ProductDto> streamProducts(){
    return flux;
  }
}
