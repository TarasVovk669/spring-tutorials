package com.demo.mvc.aop;

import com.demo.mvc.domain.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class AopConfiguration {

  @Pointcut("execution(public void addAccount(..))")
  public void declareLoggingPointcut() {}

  // this is where we add all of our related advices for logging

  // @Before("execution(public void addAccount())") //pointcut expression
  // @Before("execution(public void com.demo.mvc.dao.AccountDao.addAccount())")
  // @Before("execution(public void add*())")
  // @Before("execution(public void addAccount(com.demo.mvc.domain.Account))")
  // @Before("execution(public void addAccount(..))") //accept any params from 0 to many
  @Before("declareLoggingPointcut()") // reuse pointcut
  public void beforeAdvice() {
    System.out.println("===> Before call method");
  }

  @Before("com.demo.mvc.aop.SharedAopExpression.daoNoSetterAndGetter()") // exclude all getter and
  // setter
  public void beforeComplexAdvice() {
    System.out.println("===> Before call method");
  }

  @Before("execution(public void addAccount(..))") // pointcut expression
  public void beforeAddAccount(JoinPoint jp) {
    System.out.println("===> Before addAccount method");

    MethodSignature methodSignature = (MethodSignature) jp.getSignature();
    System.out.println("Signature: " + methodSignature);

    Object[] args = jp.getArgs();

    for (Object arg : args) {
      System.out.println(arg);

      if (arg instanceof Account c) {
        System.out.println("Account: " + c);
      }
    }
  }
}
