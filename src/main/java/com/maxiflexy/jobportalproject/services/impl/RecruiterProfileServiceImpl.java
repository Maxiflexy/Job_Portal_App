package com.maxiflexy.jobportalproject.services.impl;

import com.maxiflexy.jobportalproject.entity.RecruiterProfile;
import com.maxiflexy.jobportalproject.entity.Users;
import com.maxiflexy.jobportalproject.repository.RecruiterProfileRepository;
import com.maxiflexy.jobportalproject.repository.UsersRepository;
import com.maxiflexy.jobportalproject.services.RecruiterProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecruiterProfileServiceImpl implements RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public RecruiterProfileServiceImpl(RecruiterProfileRepository recruiterProfileRepository, UsersRepository usersRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.usersRepository = usersRepository;
    }

    public Optional<RecruiterProfile> getOne(Integer id){
        return recruiterProfileRepository.findById(id);
    }

    @Override
    public RecruiterProfile addNew(RecruiterProfile recruiterProfile) {
        return recruiterProfileRepository.save(recruiterProfile);
    }

    @Override
    public RecruiterProfile getCurrentRecruiterProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            String currentUsername = authentication.getName();
            Users users = usersRepository.findByEmail(currentUsername).orElseThrow( () ->
                    new UsernameNotFoundException("User not " + "found"));

            Optional<RecruiterProfile> recruiterProfile = getOne(users.getUserId());

            return recruiterProfile.orElse(null);
        }else
            return null;
    }
}
