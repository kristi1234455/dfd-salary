package com.dfd.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLogAspect {

    //AOP通知
    //execution(<修饰符模式>?<返回类型模式><方法名模式>(<参数模式>)<异常模式>?)
    //第一个*表示任意返回类型，第二个..*表示该包下或任何子包下的任何方法，第三个(..)表示该包下的任意方法的任意入参
    @Around("execution(* com.dfd.controller..*.*(..))")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("==============================开始执行:{}.{}==============================",joinPoint.getTarget().getClass().getName(),joinPoint.getSignature().getName());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long takeTime = endTime - startTime;
        if (takeTime > 3000) {
            log.error("==============================执行结束，耗时：{}毫秒==============================",takeTime);
        }else if (takeTime > 2000) {
            log.warn("==============================执行结束，耗时：{}毫秒==============================",takeTime);
        }else{
            log.info("==============================执行结束，耗时：{}毫秒==============================", takeTime);
        }
        return result;
    }
}
