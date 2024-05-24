package com.shop.catalogue.catalogueservice.controller;

import com.shop.catalogue.catalogueservice.domain.Product;
import com.shop.catalogue.catalogueservice.dto.ProductPayload;
import com.shop.catalogue.catalogueservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("catalogue-api/products")
public class ProductController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @GetMapping
    public List<Product> getAllProducts(Principal principal) {
        System.out.println(principal);
        return this.productService.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(this.productService.getProduct(id));
    }

    @GetMapping("/{id}/custom")
    public ResponseEntity<?> getProductCustom(@PathVariable Long id) {
        return ResponseEntity.ok(this.productService.getProductCustomQ(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        this.productService.deleteProduct(id);

    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductPayload payload,
                                    BindingResult bindingResult,
                                    UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException e) {
                throw e;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Product product = this.productService.createProduct(payload.name(), payload.description(), payload.price());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("catalogue-api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);

        }
    }

    @PatchMapping
    public ResponseEntity<?> update(@Valid @RequestBody ProductPayload payload,
                                    BindingResult bindingResult) throws BindException {

        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException e) {
                throw e;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.productService.updateProduct(payload.id(), payload.name(), payload.description(), payload.price());
            return ResponseEntity
                    .noContent().build();


        }
    }

}
