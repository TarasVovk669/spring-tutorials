package com.demo.mvc.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class CloudLogAopConfiguration {

  @Before("com.demo.mvc.aop.SharedAopExpression.daoNoSetterAndGetter()") // exclude all getter and setter
  public void beforeComplexAdvice() {
    System.out.println("===> Cloud Log Aop Processing");
  }
}
