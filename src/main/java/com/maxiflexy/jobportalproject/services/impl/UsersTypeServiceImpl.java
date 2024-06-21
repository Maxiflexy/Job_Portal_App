package com.maxiflexy.jobportalproject.services.impl;

import com.maxiflexy.jobportalproject.entity.UsersType;
import com.maxiflexy.jobportalproject.repository.UsersTypeRepository;
import com.maxiflexy.jobportalproject.services.UsersTypeService;
import org.hibernate.usertype.UserType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeServiceImpl implements UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    public UsersTypeServiceImpl(UsersTypeRepository usersTypeRepository){
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> getAll(){
        return usersTypeRepository.findAll();
    }

}
