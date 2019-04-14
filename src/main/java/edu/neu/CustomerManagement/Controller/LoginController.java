package edu.neu.CustomerManagement.Controller;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Login")
public class LoginController {

	private Logger myLog = Logger.getLogger(getClass().getName());
	
	
	
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		myLog.info("Hey!!!!");
		// return "plain-login";
		System.out.println("You entered here...");
		return "fancy-login";
		
	}
	
	// add request mapping for /access-denied
	
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		myLog.info("Hey!!!!....Denied");
		return "access-denied";
		
	}
}