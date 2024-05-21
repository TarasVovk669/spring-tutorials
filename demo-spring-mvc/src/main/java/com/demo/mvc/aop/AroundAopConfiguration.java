package com.demo.mvc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AroundAopConfiguration {

  @Around("execution(* com.demo.mvc.dao.*.getProcessDataAccounts(..))")
  public Object logAfterReturningAccounts(ProceedingJoinPoint proceedingJoinPoint)
      throws Throwable {

    long start = System.currentTimeMillis();
    var obj = proceedingJoinPoint.proceed();
    long end = System.currentTimeMillis();

    System.out.println("Delta time: " + (end - start));
    return obj;
  }
}
