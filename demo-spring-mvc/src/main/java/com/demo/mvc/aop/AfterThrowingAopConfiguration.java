package com.demo.mvc.aop;

import com.demo.mvc.domain.Account;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AfterThrowingAopConfiguration {

  @AfterThrowing(
      pointcut = "execution(* com.demo.mvc.dao.*.getExceptionAccount(..))",
          throwing = "exp")
  public void logAfterReturningAccounts(JoinPoint joinPoint, Throwable exp) {

    System.out.println("Exception: " + exp);
  }


}
