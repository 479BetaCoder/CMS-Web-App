package edu.neu.CustomerManagement.DAO;

import java.util.List;

import edu.neu.CustomerManagement.Entity.Customer;

public interface CustomerDAO {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);

	public void removeCustomer(int theId);
	
	public List<Customer> searchCustomers(String theSearchName);
	
}
