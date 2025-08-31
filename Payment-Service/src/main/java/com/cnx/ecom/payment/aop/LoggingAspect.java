package com.cnx.ecom.payment.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerMethods() {}

    @Around("restControllerMethods()")
    public Object logRequestResponse(ProceedingJoinPoint joinPoint) throws Throwable{
        HttpServletRequest request = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletAttrs) {
            request = servletAttrs.getRequest();
        }

        String uri = request != null ? request.getRequestURI() : "N/A";
        String httpMethod = request != null ? request.getMethod() : "N/A";
        String requestBody = "N/A";

        try {
            requestBody = objectMapper.writeValueAsString(joinPoint.getArgs());
        } catch (Exception e) {
            requestBody = "Unable to serialize request";
        }

        long startTime = System.currentTimeMillis();
        Object response;
        try {
            response = joinPoint.proceed();
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - startTime;
            log.error("[{} {}] {} | Request: {} | Exception: {} | Duration: {} ms",
                    httpMethod, uri, joinPoint.getSignature().toShortString(),
                    requestBody, ex.getMessage(), duration);
            throw ex;
        }
        long duration = System.currentTimeMillis() - startTime;

        String responseBody;
        try {
            responseBody = objectMapper.writeValueAsString(response);
        } catch (Exception e) {
            responseBody = "Unable to serialize response";
        }

        log.info("[{} {}] {} | Request: {} | Response: {} | Duration: {} ms",
                httpMethod, uri, joinPoint.getSignature().toShortString(),
                requestBody, responseBody, duration);

        return response;
    }
}
