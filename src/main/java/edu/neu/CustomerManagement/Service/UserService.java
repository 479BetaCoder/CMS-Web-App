package edu.neu.CustomerManagement.Service;

import org.springframework.security.core.userdetails.UserDetailsService;

import edu.neu.CustomerManagement.CrmUser;
import edu.neu.CustomerManagement.Entity.User;

public interface UserService extends UserDetailsService {

    User findByUserName(String userName);

    void save(CrmUser crmUser);
}