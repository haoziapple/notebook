package com.bkrwin.elevator.datarealm;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author wanghao
 * @Description 数据权限切面
 * @date 2019-04-23 17:25
 */
@Aspect
@Component
public class DataAuthAspect {

    @Around("@annotation(enableDataAuth)")
    public Object around(ProceedingJoinPoint joinPoint, EnableDataAuth enableDataAuth) throws Throwable {
        UserContextHolder.setDataField(enableDataAuth.dataField());
        Object result = joinPoint.proceed();
        UserContextHolder.setDataField(null);
        return result;
    }
}
