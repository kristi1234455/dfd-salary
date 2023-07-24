package com.dfd.aspect;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info("请求地址:{}", Optional.ofNullable(request.getRequestURI().toString()).orElse(null));
        log.info("请求方式:{}",request.getMethod());
        log.info("请求方IP地址:{}",request.getRemoteAddr());
        log.info("请求类方法:{}",joinPoint.getSignature());
        log.info("请求类方法参数:{}", JSONObject.toJSONString(filterArgs(joinPoint.getArgs())));
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

    private List<Object> filterArgs(Object[] objects) {
        return Arrays.stream(objects).filter(obj -> !(obj instanceof MultipartFile)
                && !(obj instanceof HttpServletResponse)
                && !(obj instanceof HttpServletRequest)).collect(Collectors.toList());
    }
}
