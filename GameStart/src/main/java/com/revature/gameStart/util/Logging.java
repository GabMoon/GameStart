package com.revature.gameStart.util;

import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * class for logging INFO
 */
@Aspect
@Component
public class Logging {

    private final Logger logger = LogManager.getLogger(Logging.class);

    /**
     * logs all the point cuts
     */
    @Pointcut("within(com.revature.gameStart..*)")
    public void logAllPointcut(){};

    /**
     * starts the logging
     * @param joinPoint join point
     */
    @Before("logAllPointcut()")
    public void logStart(JoinPoint joinPoint){
        String methodSignature = getMethodSignature(joinPoint);
        String argument = Arrays.toString(joinPoint.getArgs());
        logger.info("Invoked--->Time: {} Method: {} Input Arguments: {}", LocalDateTime.now(),methodSignature,argument); }

    /**
     * logs after
     * @param joinPoint join point
     * @param returned object returned
     */
    @AfterReturning(pointcut = "logAllPointcut()",returning = "returned")
    public void logReturn(JoinPoint joinPoint, Object returned){
        String methodSignature = getMethodSignature(joinPoint);
        logger.info("Successful--->Time: {} Returned Method: {} Value: {}", LocalDateTime.now(),methodSignature,returned);
    }

    /**
     * logs after throwing an error
     * @param joinPoint join point
     * @param e error
     */
    @AfterThrowing(pointcut = "logAllPointcut()", throwing ="e")
    public void logError(JoinPoint joinPoint,Exception e){
        String methodSignature = getMethodSignature(joinPoint);
        logger.error("Error--->Time: {} Method: {} Message: {}", LocalDateTime.now(),e.getClass().getSimpleName(),e.getMessage());
    }

    /**
     * returns a method signature
     * @param joinPoint join point
     * @return a method signature
     */
    private String getMethodSignature(JoinPoint joinPoint){
        return joinPoint.getTarget().getClass().toString()+ "." + joinPoint.getSignature().getName();
    }


    //create a buffered reader that connects to the console, we use it so we can read lines
//    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));


//    private PrintWriter printWriter(){
//        //create an print writer for writing to a file
//
//
//        try {
//            PrintWriter out = new PrintWriter(new FileWriter("src/main/resources/Log.txt"));
//            return out;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
////        //close the file (VERY IMPORTANT!)
////        out.close();
//        return null;
//    }

}
