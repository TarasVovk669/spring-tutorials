package com.reactive.product.productreactiveservice.service.impl;

import com.reactive.product.productreactiveservice.dto.ProductDto;
import com.reactive.product.productreactiveservice.repository.ProductRepository;
import com.reactive.product.productreactiveservice.service.ProductService;
import com.reactive.product.productreactiveservice.util.DtoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  private final Sinks.Many<ProductDto> sink;

  @Override
  public Flux<ProductDto> getAll() {
    return productRepository.findAll().map(DtoUtil::toDto);
  }

  @Override
  public Flux<ProductDto> getAllByRange(Integer min, Integer max) {
    return productRepository.getAllByPriceBetween(Range.closed(min, max)).map(DtoUtil::toDto);
  }

  @Override
  public Mono<ProductDto> getOne(String id) {
    return productRepository.findById(id).map(DtoUtil::toDto);
  }

  @Override
  public Mono<ProductDto> save(Mono<ProductDto> dto) {
    return dto.map(DtoUtil::toEntity)
        .flatMap(productRepository::insert)
        .map(DtoUtil::toDto)
        .doOnNext(sink::tryEmitNext);
  }

  @Override
  public Mono<ProductDto> update(String id, Mono<ProductDto> dto) {
    return productRepository
        .findById(id)
        .flatMap(p -> dto.map(DtoUtil::toEntity).doOnNext(e -> e.setId(id)))
        .flatMap(productRepository::save)
        .map(DtoUtil::toDto);
  }

  @Override
  public Mono<Void> delete(String id) {
    return productRepository.deleteById(id);
  }
}
