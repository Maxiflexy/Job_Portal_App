package com.maxiflexy.jobportalproject.services.impl;

import com.maxiflexy.jobportalproject.entity.JobSeekerProfile;
import com.maxiflexy.jobportalproject.entity.RecruiterProfile;
import com.maxiflexy.jobportalproject.entity.Users;
import com.maxiflexy.jobportalproject.repository.JobSeekerProfileRepository;
import com.maxiflexy.jobportalproject.repository.RecruiterProfileRepository;
import com.maxiflexy.jobportalproject.repository.UsersRepository;
import com.maxiflexy.jobportalproject.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository,
                            JobSeekerProfileRepository jobSeekerProfileRepository,
                            RecruiterProfileRepository  recruiterProfileRepository,
                            PasswordEncoder passwordEncoder){

        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public Users addNewUser(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword())); // during registration, encrypt user password
        Users savedUser = usersRepository.save(users);

        int userTypeId = users.getUserTypeId().getUserTypeId();

        if(userTypeId == 1){
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }
        return savedUser;
    }

    @Override
    public Object getCurrentUserProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));

            int userId = users.getUserId();
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {

                RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
                return recruiterProfile;

            } else {

                JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
                return jobSeekerProfile;
            }
        }
        return null;
    }

    @Override
    public Users getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
           String username = authentication.getName();

            Users user = usersRepository.findByEmail(username).orElseThrow(() ->
                    new UsernameNotFoundException("Could not found " + "user"));

            return user;
        }
        return null;
    }

    @Override
    public Users findByEmail(String currentUsername) {
        return usersRepository.findByEmail(currentUsername).orElseThrow( () ->
                new UsernameNotFoundException("User not " + "found"));
    }
}
