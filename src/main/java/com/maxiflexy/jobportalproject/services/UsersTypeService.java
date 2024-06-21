package com.maxiflexy.jobportalproject.services;

import com.maxiflexy.jobportalproject.entity.UsersType;
import org.hibernate.usertype.UserType;

import java.util.List;

public interface UsersTypeService {

    List<UsersType> getAll();
}
