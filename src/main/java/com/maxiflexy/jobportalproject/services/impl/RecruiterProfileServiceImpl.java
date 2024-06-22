package com.maxiflexy.jobportalproject.services.impl;

import com.maxiflexy.jobportalproject.entity.RecruiterProfile;
import com.maxiflexy.jobportalproject.repository.RecruiterProfileRepository;
import com.maxiflexy.jobportalproject.services.RecruiterProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileServiceImpl implements RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    public RecruiterProfileServiceImpl(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Optional<RecruiterProfile> getOne(Integer id){
        return recruiterProfileRepository.findById(id);
    }
}
