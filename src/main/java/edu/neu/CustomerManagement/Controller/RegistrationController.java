package edu.neu.CustomerManagement.Controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.neu.CustomerManagement.CrmUser;
import edu.neu.CustomerManagement.Entity.User;
import edu.neu.CustomerManagement.Service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	
    @Autowired
    private UserService userService;
    
    private Map<String, String> roles;
    
    @PostConstruct
    protected void loadRoles() {
    // using hashmap, could also read this info from a database
    roles = new LinkedHashMap<String, String>();
    // key=the role, value=display to user
    roles.put("ROLE_EMPLOYEE", "Employee");
    roles.put("ROLE_MANAGER", "Manager");
    roles.put("ROLE_ADMIN", "Admin");
    }
	
    private Logger logger = Logger.getLogger(getClass().getName());
    
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("crmUser", new CrmUser());
		// add roles to the model for form display
		theModel.addAttribute("roles", roles);
		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
				@Valid @ModelAttribute("crmUser") CrmUser theCrmUser, 
				BindingResult theBindingResult, 
				Model theModel) {
		
		
		String userName = theCrmUser.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "registration-form";
	        }

		// check the database if user already exists
        User existing = userService.findByUserName(userName);
        if (existing != null){
        	theModel.addAttribute("crmUser", new CrmUser());
        	// add roles to the model for form display
        	theModel.addAttribute("roles", roles);
			theModel.addAttribute("registrationError", "User name already exists.");
			logger.warning("User name already exists.");
        	return "registration-form";
        }
        
        
        // create user account        						
        userService.save(theCrmUser);
        logger.info("Successfully created user: " + userName);
        return "registration-confirmation";		
	}
}
