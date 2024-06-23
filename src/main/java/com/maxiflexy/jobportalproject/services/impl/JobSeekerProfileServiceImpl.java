package com.maxiflexy.jobportalproject.services.impl;

import com.maxiflexy.jobportalproject.entity.JobSeekerProfile;
import com.maxiflexy.jobportalproject.entity.Users;
import com.maxiflexy.jobportalproject.repository.JobPostActivityRepository;
import com.maxiflexy.jobportalproject.repository.JobSeekerProfileRepository;
import com.maxiflexy.jobportalproject.repository.UsersRepository;
import com.maxiflexy.jobportalproject.services.JobSeekerProfileService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobSeekerProfileServiceImpl implements JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UsersRepository usersRepository;

    public JobSeekerProfileServiceImpl(JobSeekerProfileRepository jobSeekerProfileRepository, UsersRepository usersRepository) {
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<JobSeekerProfile> getOne(Integer id){
        return jobSeekerProfileRepository.findById(id);
    }

    @Override
    public JobSeekerProfile addNew(JobSeekerProfile jobSeekerProfile) {
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }

    @Override
    public JobSeekerProfile getCurrentSeekerProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow(() ->
                    new UsernameNotFoundException("User not " + "found"));

            Optional<JobSeekerProfile> seekerProfile = getOne(users.getUserId());
            return seekerProfile.orElse(null);
        }else
            return null;
    }
}
