package com.demo.mvc.controller;

import com.demo.mvc.controller.customvalidation.IndexPeople;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class Student {

    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    @NotNull
    @Min(value = 18)
    @Max(value = 60)
    private Integer age;
    private String country;
    @IndexPeople(value = "QWE", message = "must start with QWE")
    private String index;

}
