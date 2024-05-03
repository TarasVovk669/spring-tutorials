package com.shop.manager.managerservice.controller;

import com.shop.manager.managerservice.domain.Product;
import com.shop.manager.managerservice.dto.ProductPayload;
import com.shop.manager.managerservice.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "catalogue/products")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping("/list")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "catalogue/products/list";
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "catalogue/products/view";
    }

    @GetMapping("/create")
    public String getProductNewPage() {
        return "catalogue/products/new_product";
    }

    @PostMapping("create")
    public String createProduct(@Valid ProductPayload payload,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList());
            return "catalogue/products/new_product";
        } else {
            Product product = this.productService.createProduct(payload.name(), payload.description(), payload.price());
            return "redirect:/catalogue/products/%d".formatted(product.getId());
        }
    }

    @GetMapping("/{id}/edit")
    public String getProductEditPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.getProduct(id));
        return "catalogue/products/edit";
    }

    @PostMapping("{id}/edit")
    public String updateProduct(@PathVariable("id") Long id,
                                @Valid ProductPayload payload,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payload", payload);
            model.addAttribute("errors", bindingResult.getAllErrors().stream()
                     .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList());
            return "catalogue/products/edit";
        } else {
            this.productService.updateProduct(id, payload.name(), payload.description(), payload.price());
            return "redirect:/catalogue/products/%d".formatted(id);
        }
    }

    @PostMapping("{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        this.productService.deleteProduct(id);
        return "redirect:/catalogue/products/list";
    }
}
