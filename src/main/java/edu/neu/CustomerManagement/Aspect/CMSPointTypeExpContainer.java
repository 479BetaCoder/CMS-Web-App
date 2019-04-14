package edu.neu.CustomerManagement.Aspect;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class CMSPointTypeExpContainer {
	
	@Pointcut("execution(* edu.neu.CustomerManagement.Controller.*.*(..))")
	public void controllerLogger() {
		
	}
	
	@Pointcut("execution(* edu.neu.CustomerManagement.Service.*.*(..))")
	public void serviceLogger() {
		
	}
	
	@Pointcut("execution(* edu.neu.CustomerManagement.DAO.*.*(..))")
	public void DAOLogger() {
		
	}
	
	@Pointcut("controllerLogger() || serviceLogger() || DAOLogger()")
	public void CMSFlow() {
		
	}
	

}
