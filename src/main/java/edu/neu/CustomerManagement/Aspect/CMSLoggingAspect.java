package edu.neu.CustomerManagement.Aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CMSLoggingAspect {

	private Logger myLogger = Logger.getLogger(getClass().getName());

	@Before("edu.neu.CustomerManagement.Aspect.CMSPointTypeExpContainer.CMSFlow()")
	public void getBeforeLogger(JoinPoint joinpoint) {
		String theMethod = joinpoint.getSignature().toShortString();
		myLogger.info("===> @Before calling the method :: " + theMethod);

		Object[] argsPassed = joinpoint.getArgs();

		for (Object o : argsPassed) {
			myLogger.info("===> Argument Passed ::: " + o);
		}

	}

	@AfterReturning(pointcut = "edu.neu.CustomerManagement.Aspect.CMSPointTypeExpContainer.CMSFlow()", returning = "result")
	public void getAfterLogger(JoinPoint joinpoint, Object result) {
		String theMethod = joinpoint.getSignature().toShortString();
		myLogger.info("===> @After calling the method :: " + theMethod);
		myLogger.info("===> Result returned ::: " + result);

	}

}
