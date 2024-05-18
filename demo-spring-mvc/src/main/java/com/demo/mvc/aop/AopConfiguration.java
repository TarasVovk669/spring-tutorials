package com.demo.mvc.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopConfiguration {

    @Pointcut("execution(public void addAccount(..))")
    public void declareLoggingPointcut(){}

    //this is where we add all of our related advices for logging

    //@Before("execution(public void addAccount())") //pointcut expression
    //@Before("execution(public void com.demo.mvc.dao.AccountDao.addAccount())")
    //@Before("execution(public void add*())")
    //@Before("execution(public void addAccount(com.demo.mvc.domain.Account))")
    //@Before("execution(public void addAccount(..))") //accept any params from 0 to many
    @Before("declareLoggingPointcut()") //reuse pointcut
    public void beforeAdvice(){
    System.out.println("===> Before call method");
    }
}
