package com.capgemini.wsb.fitnesstracker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Service
public class LoggingAspect {
    @Around("within(com.capgemini.wsb.fitnesstracker.*.internal.*ServiceImpl+)\")")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();

        LocalDateTime beforeExecution = LocalDateTime.now();
        StringBuilder logMessage = new StringBuilder();
        logMessage
                .append(beforeExecution)
                .append(" EXECUTING ")
                .append(className)
                .append(".")
                .append(methodName)
                .append("(");

        for (Object arg : args) {
            logMessage.append(arg);
        }
        logMessage.append(")");
        System.out.println(logMessage);
        Object result = joinPoint.proceed(args);

        LocalDateTime afterExecution = LocalDateTime.now();
        StringBuilder logMessageAfterExecution = new StringBuilder();
        logMessageAfterExecution
                .append(afterExecution)
                .append(" FINISHED ")
                .append(className)
                .append(".")
                .append(methodName)
                .append("(");

        for (Object arg : args) {
            logMessageAfterExecution.append(arg);
        }

        logMessageAfterExecution.append(")");
        logMessageAfterExecution.append(" completed in: ");
        Duration executionTime = Duration.between(beforeExecution, afterExecution);
        logMessageAfterExecution.append(executionTime);
        System.out.println(logMessageAfterExecution);

        return result;
    }
}
