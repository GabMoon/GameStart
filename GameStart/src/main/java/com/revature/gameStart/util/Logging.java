package com.revature.gameStart.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class Logging {

    private final Logger logger = LogManager.getLogger(Logging.class);

    @Pointcut("within(com.revature.gameStart..*)")
    public void logAllPointcut(){};

    @Before("logAllPointcut()")
    public void logStart(JoinPoint joinPoint){
        String methodSignature = getMethodSignature(joinPoint);
        String argument = Arrays.toString(joinPoint.getArgs());
        logger.info("Invoked--->Time: {} Method: {} Input Arguments: {}", LocalDateTime.now(),methodSignature,argument);
        System.out.println("logStart");
    }

    @AfterReturning(pointcut = "logAllPointcut()",returning = "returned")
    public void logReturn(JoinPoint joinPoint, Object returned){
        String methodSignature = getMethodSignature(joinPoint);
        logger.info("Successful--->Time: {} Returned Method: {} Value: {}", LocalDateTime.now(),methodSignature,returned);
        System.out.println("logReturn");
    }

    @AfterThrowing(pointcut = "logAllPointcut()", throwing ="e")
    public void logError(JoinPoint joinPoint,Exception e){
        String methodSignature = getMethodSignature(joinPoint);
        logger.error("Error--->Time: {} Method: {} Message: {}", LocalDateTime.now(),e.getClass().getSimpleName(),e.getMessage());
        System.out.println("logError");
    }


    private String getMethodSignature(JoinPoint joinPoint){
        return joinPoint.getTarget().getClass().toString()+ "." + joinPoint.getSignature().getName();
    }


//    /** Handle to the log file */
//    private final Log log = LogFactory.getLog(getClass());
//
//    public Logging () {}
//
//    @AfterReturning("execution(* com.revature..*.*(..))")
//    public void logMethodAccessAfter(JoinPoint joinPoint) {
//        log.info("***** Completed: " + joinPoint.getSignature().getName() + " *****");
//        System.out.println("***** Completed: " + joinPoint.getSignature().getName() + " *****");
//    }
//
//    @Before("execution(* com.revature..*.*(..))")
//    public void logMethodAccessBefore(JoinPoint joinPoint) {
//        log.info("***** Starting: " + joinPoint.getSignature().getName() + " *****");
//        System.out.println("***** Starting: " + joinPoint.getSignature().getName() + " *****");
//    }
}
