package com.demo.mvc.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopConfiguration {

    //this is where we add all of our related advices for logging

    @Before("execution(public void addAccount())") //pointcut expression
    public void beforeAdvice(){

    System.out.println("===> Before call method");
    }
}
