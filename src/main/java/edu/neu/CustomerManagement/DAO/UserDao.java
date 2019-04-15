package edu.neu.CustomerManagement.DAO;

import edu.neu.CustomerManagement.Entity.User;

public interface UserDao {

    User findByUserName(String userName);
    
    void save(User user);
    
}