package com.maxiflexy.jobportalproject.services;

import com.maxiflexy.jobportalproject.entity.RecruiterProfile;

import java.util.Optional;

public interface RecruiterProfileService {

    Optional<RecruiterProfile> getOne(Integer id);

    RecruiterProfile addNew(RecruiterProfile recruiterProfile);

    RecruiterProfile getCurrentRecruiterProfile();
}
