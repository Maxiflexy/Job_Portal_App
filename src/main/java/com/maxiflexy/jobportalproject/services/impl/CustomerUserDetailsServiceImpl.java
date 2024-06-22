package com.maxiflexy.jobportalproject.services.impl;

import com.maxiflexy.jobportalproject.entity.Users;
import com.maxiflexy.jobportalproject.repository.UsersRepository;
import com.maxiflexy.jobportalproject.services.CustomerUserDetailsService;
import com.maxiflexy.jobportalproject.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsServiceImpl implements CustomerUserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public CustomerUserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users user = usersRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Could not found user"));
       return new CustomUserDetails(user);
    }



}
