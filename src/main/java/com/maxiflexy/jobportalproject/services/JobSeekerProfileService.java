package com.maxiflexy.jobportalproject.services;

import com.maxiflexy.jobportalproject.entity.JobSeekerProfile;

import java.util.Optional;

public interface JobSeekerProfileService {

    Optional<JobSeekerProfile> getOne(Integer id);

    JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile);

    JobSeekerProfile getCurrentSeekerProfile();
}
