package com.demo.mvc.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SharedAopExpression {

    @Pointcut("execution(* com.demo.mvc.dao.*.*(..))")
    public void dao() {}

    @Pointcut("execution(* com.demo.mvc.dao.*.get*(..))")
    public void getter() {}

    @Pointcut("execution(* com.demo.mvc.dao.*.set*(..))")
    public void setter() {}

    @Pointcut("dao() && !(getter() || setter())")
    public void daoNoSetterAndGetter() {}
}
