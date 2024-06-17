package com.reactive.order.service;

import com.reactive.order.client.ProductClient;
import com.reactive.order.client.UserClient;
import com.reactive.order.domain.PurchaseOrder;
import com.reactive.order.dto.OrderStatus;
import com.reactive.order.dto.PurchaseRequestDto;
import com.reactive.order.dto.PurchaseResponseDto;
import com.reactive.order.dto.RequestContext;
import com.reactive.order.dto.user.TransactionRequestDto;
import com.reactive.order.dto.user.TransactionStatus;
import com.reactive.order.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class OrderFulfillmentServiceImpl implements OrderFulfillmentService {

  private final ProductClient productClient;

  private final UserClient userClient;

  private final PurchaseOrderRepository purchaseOrderRepository;

  @Override
  public Mono<PurchaseResponseDto> processDto(Mono<PurchaseRequestDto> dtoMono) {
    return dtoMono
        .map(dto -> RequestContext.builder().purchaseRequestDto(dto).build())
        .flatMap(
            requestContext ->
                productClient
                    .getProductById(requestContext.getPurchaseRequestDto().getProductId())
                    .doOnNext(requestContext::setProductDto)
                    .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                    .thenReturn(requestContext))
        .doOnNext(
            requestContext ->
                requestContext.setTransactionRequestDto(
                    TransactionRequestDto.builder()
                        .userId(requestContext.getPurchaseRequestDto().getUserId())
                        .amount(requestContext.getProductDto().getPrice())
                        .build()))
        .flatMap(
            requestContext ->
                userClient
                    .authorizeTrx(requestContext.getTransactionRequestDto())
                    .doOnNext(requestContext::setTransactionResponseDto)
                    .thenReturn(requestContext))
        .map(
            requestContext ->
                PurchaseOrder.builder()
                    .userId(requestContext.getPurchaseRequestDto().getUserId())
                    .productId(requestContext.getPurchaseRequestDto().getProductId())
                    .amount(requestContext.getProductDto().getPrice().longValue())
                    .orderStatus(
                        requestContext
                                .getTransactionResponseDto()
                                .getStatus()
                                .equals(TransactionStatus.APPROVED)
                            ? OrderStatus.COMPLETED
                            : OrderStatus.FAILED)
                    .build())
        .map(purchaseOrderRepository::save) // blocking
        .map(
            purchaseOrder ->
                PurchaseResponseDto.builder()
                    .userId(purchaseOrder.getUserId())
                    .productId(purchaseOrder.getProductId())
                    .orderId(purchaseOrder.getId())
                    .amount(purchaseOrder.getAmount())
                    .orderStatus(purchaseOrder.getOrderStatus())
                    .build())
        .subscribeOn(Schedulers.boundedElastic());
  }

  @Override
  public Flux<PurchaseResponseDto> getPurchaseOrders(Long userId) {
    return Flux.fromStream(purchaseOrderRepository.getAllByUserId(userId).stream())
        .map(
            purchaseOrder ->
                PurchaseResponseDto.builder()
                    .userId(purchaseOrder.getUserId())
                    .productId(purchaseOrder.getProductId())
                    .orderId(purchaseOrder.getId())
                    .amount(purchaseOrder.getAmount())
                    .orderStatus(purchaseOrder.getOrderStatus())
                    .build())
        .subscribeOn(Schedulers.boundedElastic());
  }
}
