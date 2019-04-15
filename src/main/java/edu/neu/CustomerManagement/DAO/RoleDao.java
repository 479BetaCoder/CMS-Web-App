package edu.neu.CustomerManagement.DAO;

import edu.neu.CustomerManagement.Entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}