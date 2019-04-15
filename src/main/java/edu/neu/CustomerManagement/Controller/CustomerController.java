package edu.neu.CustomerManagement.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.neu.CustomerManagement.Entity.Customer;
import edu.neu.CustomerManagement.Service.CustomerService;

@Controller
@RequestMapping("/Customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")
	public String listCustomers(Model theModel) {

		List<Customer> thecustomers = customerService.getCustomers();
		theModel.addAttribute("customers", thecustomers);

		return "list-Customers";
	}

	@GetMapping("/showFormForAdd")
	public String showCustomerForm(Model theModel) {

		Customer theCustomer = new Customer();
		theModel.addAttribute("customer", theCustomer);
		return "CustomerForm";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute Customer customer) {

		customerService.saveCustomer(customer);

		return "redirect:/Customer/list";
	}

	@GetMapping("/showFormForUpdate")
	public String updateCustomer(@RequestParam("customerId") int theId, Model theModel) {

		Customer thecustomer = customerService.getCustomer(theId);
		theModel.addAttribute("customer", thecustomer);

		return "CustomerForm";
	}

	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		customerService.removeCustomer(theId);
		return "redirect:/Customer/list";

	}

	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") String theSearchName, Model theModel) {

		// search customers from the service
		List<Customer> theCustomers = customerService.searchCustomers(theSearchName);

		// add the customers to the model
		theModel.addAttribute("customers", theCustomers);

		return "list-Customers";
	}

}
