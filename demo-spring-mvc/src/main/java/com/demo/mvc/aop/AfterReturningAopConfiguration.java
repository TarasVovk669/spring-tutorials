package com.demo.mvc.aop;

import com.demo.mvc.domain.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class AfterReturningAopConfiguration {

  @AfterReturning(
      pointcut = "execution(* com.demo.mvc.dao.*.getAccounts(..))",
      returning = "result")
  public void logAfterReturningAccounts(JoinPoint joinPoint, List<Account> result) {
    System.out.println("Result: " + result);
  }

  @AfterReturning(
      pointcut = "execution(* com.demo.mvc.dao.*.getAccounts(..))",
      returning = "result")
  public void logAfterReturningAccountsPostProcessData(JoinPoint joinPoint, List<Account> result) {


   if (!result.isEmpty()){
     for(Account a: result){
       a.setName(a.getName().concat("_changed"));
     }

     System.out.println("Result_postProcessor: " + result);
    }

  }
}
