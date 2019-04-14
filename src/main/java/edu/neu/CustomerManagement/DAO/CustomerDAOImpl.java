package edu.neu.CustomerManagement.DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import edu.neu.CustomerManagement.Entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers() {
		List<Customer> customerData;
		Session session = sessionFactory.getCurrentSession();
		try {
			Query<Customer> query = session.createQuery("from Customer order by lastName", Customer.class);
			customerData = query.getResultList();
		} catch (Exception e) {
			System.out.println("Some exception occured : " + e);
			return null;
		}
		return customerData;
	}

	@Override
	public void saveCustomer(Customer customer) {

		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(customer);

	}

	@Override
	public Customer getCustomer(int theId) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Customer.class, theId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeCustomer(int theId) {
		Session session = sessionFactory.getCurrentSession();
		Query<Customer> theQuery = session.createQuery("delete from Customer c where c.id =:customerId");
		theQuery.setParameter("customerId", theId);
		theQuery.executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {

		Session currentSession = sessionFactory.getCurrentSession();
		Query<Customer> theQuery = null;
		if (theSearchName != null && theSearchName.trim().length() > 0) {
			// search for firstName or lastName ... case insensitive
			theQuery = currentSession.createQuery(
					"from Customer where lower(firstName) like :theName or lower(lastName) like :theName",
					Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		} else {
			// theSearchName is empty ... so just get all customers
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}

		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();

		// return the results
		return customers;

	}
}
