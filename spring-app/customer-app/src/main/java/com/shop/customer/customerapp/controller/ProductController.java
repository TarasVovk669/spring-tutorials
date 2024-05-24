package com.shop.customer.customerapp.controller;

import com.shop.customer.customerapp.client.ProductClient;
import com.shop.customer.customerapp.dao.ReviewPayload;
import com.shop.customer.customerapp.entity.ReviewProduct;
import com.shop.customer.customerapp.service.FavoriteService;
import com.shop.customer.customerapp.service.ProductReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer/products")
public class ProductController {

    private final ProductClient productClient;
    private final FavoriteService favoriteService;
    private final ProductReviewService productReviewService;

    @GetMapping("/list")
    public Mono<String> getProductListPage(Model model) {
        return productClient.findAllProducts()
                .collectList() //aggregate flux into mono-list
                .doOnNext(productsList -> model.addAttribute("products", productsList))
                .thenReturn("customer/products/list");
    }

    @GetMapping("/{id}")
    public Mono<String> getProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("isFav", false);
        return productClient.getProduct(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .doOnNext(p -> model.addAttribute("product", p))
                .flatMap(p -> favoriteService.findById(p.id())
                        .doOnNext(f -> model.addAttribute("isFav", true))
                        .then(Mono.just(p)))
                .then(productReviewService.findAllForProduct(id).collectList()
                        .doOnNext(r -> model.addAttribute("reviews", r)))
                .thenReturn("customer/products/view");
    }

    @GetMapping("/create")
    public String getProductNewPage() {
        return "customer/products/new_product";
    }

    @PostMapping("/{id}/add-to-fav")
    public Mono<String> addProdToFav(@PathVariable Long id) {
        return Mono.just(id)
                .flatMap(this.favoriteService::addProductToFavorite)
                .thenReturn("redirect:/customer/products/" + id);
    }

    @PostMapping("/{id}/remove-from-fav")
    public Mono<String> removeProdToFav(@PathVariable Long id) {
        return Mono.just(id)
                .flatMap(this.favoriteService::removeProductFromFavorite)
                .thenReturn("redirect:/customer/products/" + id);
    }

    @PostMapping("/{id}/create-review")
    public Mono<String> createReview(@PathVariable Long id,
                                     @Valid ReviewPayload payload,
                                     BindingResult bindingResult,
                                     Model model) {

        if (bindingResult.hasErrors()) {
            return productClient.getProduct(id)
                    .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                    .doOnNext(p -> {
                        model.addAttribute("product", p);
                        model.addAttribute("isFav", false);
                        model.addAttribute("payload", payload);
                        model.addAttribute("errors", bindingResult.getAllErrors().stream()
                                .map(ObjectError::getDefaultMessage)
                                .collect(Collectors.toList()));
                    })
                    .thenReturn("customer/products/view");
        } else {
            return productReviewService.createReview(id, payload.rating(), payload.value())
                    .thenReturn("redirect:/customer/products/" + id);
        }
    }

    @ExceptionHandler(Exception.class)
    public String handle(Exception e, Model model) {
        model.addAttribute("error", e.getMessage());

        return "customer/products/error/404";
    }

}
