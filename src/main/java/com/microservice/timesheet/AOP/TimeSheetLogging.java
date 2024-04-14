package com.microservice.timesheet.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Date;

@Aspect
@Component
public class TimeSheetLogging {
	
	
	Logger ioLogger = LoggerFactory.getLogger(getClass());
	
	
	@Before(value = "execution(* com.microservice.TimeSheetService..*(..))")
	public void beforeAdvice(JoinPoint joinPoint) {
		
		ioLogger.debug("Request to"+joinPoint.getSignature()+" with Parameters "+joinPoint.getArgs().toString()+"started at "+ new Date());
		
	}
	
	
	@After(value = "execution(* com.microservice.TimeSheetService..*(..))")
	//Will get executed always even during exception
	public void afterAdvice(JoinPoint joinPoint) {
		
		ioLogger.debug("Request to"+joinPoint.getSignature()+" ended at "+ new Date());
		
	}


	@AfterReturning(value = "execution(* com.microservice.TimeSheetService..*(..))", returning = "result")
	//Will get executed only if there is no exception
	public void afterAdviceReturn(JoinPoint joinPoint,Object result) {
		
		ioLogger.debug("Request to"+joinPoint.getSignature()+" with return value "+ result);
		
	}
	
	
	@AfterThrowing(value = "execution(* com.microservice.TimeSheetService..*(..))", throwing = "exception")
	//Will get executed if there is an exception
	public void afterAdviceReturnException(JoinPoint joinPoint,Exception exception) {
		
		ioLogger.error("Request to"+joinPoint.getSignature()+" was having exception "+ exception.getMessage());
		
	}
	
	
	
	  @Around(value = "execution(* com.microservice.TimeSheetService..*(..))")
	  public Object logMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
	        long startTime = System.currentTimeMillis();

	        // Proceed with the method execution
	        Object result = joinPoint.proceed();

	        long executionTime = System.currentTimeMillis() - startTime;

	        // Log the method execution time
	        ioLogger.info("Method {} took {} ms to execute", joinPoint.getSignature().toShortString(), executionTime);

	        return result;
	    }
	
	
	

}
