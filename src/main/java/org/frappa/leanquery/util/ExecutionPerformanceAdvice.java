package org.frappa.leanquery.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.frappa.leanquery.config.StatisticsListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@ConditionalOnExpression("${aspect.enabled:true}")
public class ExecutionPerformanceAdvice {

    @Around("@annotation(LogExecutionPerformance)")
    public Object executionTime(ProceedingJoinPoint point) throws Throwable {
        long startTime = System.currentTimeMillis();
        StatisticsListener.restart();
        Object object = point.proceed();
        long endtime = System.currentTimeMillis();
        log.debug("[" + point.getSignature().getDeclaringType().getName() +"."+ point.getSignature().getName()+"] " +
                StatisticsListener.getQueries()+" queries in " + (endtime-startTime) +"ms");
        return object;
    }
}