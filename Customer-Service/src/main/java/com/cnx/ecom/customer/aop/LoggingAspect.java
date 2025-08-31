package com.cnx.ecom.customer.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.cnx.ecom.customer.controller..*(..)) || execution(* com.cnx.ecom.customer.service..*(..))")
    public Object logRequestResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("Entering method: {} with args: {}", methodName, args);

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            log.error("Exception in method: {} with message: {}", methodName, e.getMessage());
            throw e;
        }

        log.info("Exiting method: {} with response: {}", methodName, result);
        return result;
    }
}
