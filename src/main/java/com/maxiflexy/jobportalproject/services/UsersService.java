package com.maxiflexy.jobportalproject.services;

import com.maxiflexy.jobportalproject.entity.Users;

public interface UsersService {

    Users addNewUser(Users users);

    Object getCurrentUserProfile();

    Users getCurrentUser();

    Users findByEmail(String currentUsername);
}
