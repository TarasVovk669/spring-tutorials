package com.demo.mvc.controller.customvalidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IndexPeopleValidator implements ConstraintValidator<IndexPeople, String> {

  private String indexPrefix;

  @Override
  public void initialize(IndexPeople constraintAnnotation) {
    this.indexPrefix = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
    boolean result;

    if (s != null) {
      result = s.startsWith(indexPrefix);
    } else {
      result = true;
    }

    return result;
  }
}
