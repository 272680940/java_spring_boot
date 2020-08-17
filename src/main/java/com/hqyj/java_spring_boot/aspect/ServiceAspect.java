package com.hqyj.java_spring_boot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class ServiceAspect {
    private  final  static Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);
    /**
     * Order 代表优先级，数字越小优先级越高
     */
    @Pointcut("@annotation(com.hqyj.java_spring_boot.aspect.ServiceAnnotation)")
    @Order(1)
    public void servicePointCut() {}

    @Before(value = "com.hqyj.java_spring_boot.aspect.ServiceAspect.servicePointCut()")
    public void beforeService(JoinPoint joinpoint) {
        LOGGER.debug("==== This is before Service ====");
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

    }

    @Around(value = "com.hqyj.java_spring_boot.aspect.ServiceAspect.servicePointCut()")
    public Object aroundService(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {
        LOGGER.debug("==== This is around Service ==== ");
        return proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
    }

    @After(value = "com.hqyj.java_spring_boot.aspect.ServiceAspect.servicePointCut()")
    public void afterService() {
        LOGGER.debug("==== This is after Service ====");
    }
}
