package com.graphql.tutorials.productservice.controller;

import com.graphql.tutorials.productservice.generated.types.ModelSimple;
import com.graphql.tutorials.productservice.service.ModelQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/models")
public class ModelController {

    private final ModelQueryService modelQueryService;

    public ModelController(ModelQueryService modelQueryService) {
        this.modelQueryService = modelQueryService;
    }

    @GetMapping(value = "/simple")
    public List<ModelSimple> modelSimples(
            @RequestParam(name = "modelIds") List<Long> ids
    ) {
        return modelQueryService.getSimpleModels(ids);
    }
}
