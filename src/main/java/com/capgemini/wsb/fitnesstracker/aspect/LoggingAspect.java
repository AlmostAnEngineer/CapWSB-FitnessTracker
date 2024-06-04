package com.capgemini.wsb.fitnesstracker.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.rmi.server.UID;
import java.time.Duration;
import java.time.LocalDateTime;

@Aspect
@Service
public class LoggingAspect {
    @Around("within(com.capgemini.wsb.fitnesstracker.*.internal.*)\")")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();

        Method method = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getMethod();

        Class<?> returnType = method.getReturnType();
        String returnTypeMessage = returnType.getSimpleName().equals("void") ? "void" : returnType.getSimpleName();

        LocalDateTime beforeExecution = LocalDateTime.now();
        StringBuilder logMessage = new StringBuilder();
        logMessage
                .append(beforeExecution)
                .append(" EXECUTING ")
                .append(returnTypeMessage)
                .append(" ")
                .append(className)
                .append(".")
                .append(methodName)
                .append("(");

        if (args != null) {
            try {
                for (Object arg : args) {
                    if(arg != null) {
                        logMessage.append(arg);
                    }
                }
            } catch (StackOverflowError e) {
            }
        }

        logMessage.append(")");
        UID uid = new UID();
        logMessage.append(" ID: ").append(uid);
        System.out.println(logMessage);
        Object result = joinPoint.proceed(args);

        LocalDateTime afterExecution = LocalDateTime.now();
        StringBuilder logMessageAfterExecution = new StringBuilder();
        logMessageAfterExecution
                .append(afterExecution)
                .append(" FINISHED ")
                .append(returnTypeMessage)
                .append(" ")
                .append(className)
                .append(".")
                .append(methodName)
                .append("(");

        if (args != null) {
            try {
                for (Object arg : args) {
                    if(arg != null) {
                        logMessageAfterExecution.append(arg);
                    }
                }
            } catch (StackOverflowError e) {
            }
        }

        logMessageAfterExecution.append(")");
        logMessageAfterExecution.append(" completed in: ");
        Duration executionTime = Duration.between(beforeExecution, afterExecution);
        logMessageAfterExecution.append(executionTime);
        logMessageAfterExecution.append(" ID: ").append(uid);
        System.out.println(logMessageAfterExecution);

        return result;
    }
}
