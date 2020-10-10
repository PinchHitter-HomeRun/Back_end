package com.toyproj.pinchhitterhomerun.aop;

import com.toyproj.pinchhitterhomerun.model.Logger;
import com.toyproj.pinchhitterhomerun.repository.LoggerRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class DBLoggerAop {

    LoggerRepository loggerRepository;

    public DBLoggerAop(LoggerRepository loggerRepository) {
        this.loggerRepository = loggerRepository;
    }

    @Around("execution(* com.toyproj.pinchhitterhomerun.controller..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();

        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        //System.out.println(request.getRequestURL());

        StringBuilder logString = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            logString.append(codeSignature.getParameterNames()[i]).append(" : ").append(args[i]).append("\n");
        }

        //System.out.println(logString.toString());

        Logger logger = new Logger(logString.toString(), request.getRequestURL().toString());
        loggerRepository.save(logger);

        return joinPoint.proceed();
    }
}
