package com.example.miniproject.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class BookServiceAspect {

    @Pointcut("execution(* com.example.miniproject.service.impl.*.*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void beforeMethod(JoinPoint joinPoint) {

        log.info("Before method: {}", joinPoint.getSignature().getName());

        log.info("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {

        log.info("Method success: {}", joinPoint.getSignature().getName());

        log.info("Return: {}", result);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {

        log.error("Exception in method: {}", joinPoint.getSignature().getName());

        log.error("Message: {}", ex.getMessage());
    }
}