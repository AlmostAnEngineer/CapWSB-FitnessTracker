package com.capgemini.wsb.fitnesstracker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LoggingAspect {
    @Around("within(com.capgemini.wsb.fitnesstracker.*.internal.*ServiceImpl+)\")")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Run method:: " + joinPoint.getSignature().getName());
        Object result = joinPoint.proceed();
        System.out.println("Method stopped: " + joinPoint.getSignature().getName());
        if (joinPoint.getArgs().length > 0 && result != null) {
            System.out.println("Result: " + result);
        }
        return result;
    }
}
