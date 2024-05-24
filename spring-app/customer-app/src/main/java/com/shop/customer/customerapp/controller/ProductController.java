package com.shop.customer.customerapp.controller;

import com.shop.customer.customerapp.client.ProductClient;
import com.shop.customer.customerapp.entity.Product;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer/products")
public class ProductController {

    private final ProductClient productClient;

    @GetMapping("/list")
    public Mono<String> getProductListPage(Model model) {

        return productClient.findAllProducts()
                .collectList() //aggregate flux into mono-list
                .doOnNext(productsList -> model.addAttribute("products", productsList))
                .thenReturn("customer/products/list");
    }

}
