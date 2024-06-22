package com.maxiflexy.jobportalproject.services.impl;

import com.maxiflexy.jobportalproject.entity.*;
import com.maxiflexy.jobportalproject.repository.JobPostActivityRepository;
import com.maxiflexy.jobportalproject.services.JobPostActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostActivityServiceImpl implements JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    @Autowired
    public JobPostActivityServiceImpl(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity){

        return jobPostActivityRepository.save(jobPostActivity);

    }

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter){

        List<IRecruiterJobs> recruiterJobsDtos = jobPostActivityRepository.getRecruiterJobs(recruiter);

        List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();

        for(IRecruiterJobs iRecruiterJobs : recruiterJobsDtos){
            JobLocation location = new JobLocation(iRecruiterJobs.getLocationId(), iRecruiterJobs.getCity(),
                    iRecruiterJobs.getState(), iRecruiterJobs.getCountry());

            JobCompany jobCompany = new JobCompany(iRecruiterJobs.getLocationId(), iRecruiterJobs.getName(), "");

            recruiterJobsDtoList.add(new RecruiterJobsDto(iRecruiterJobs.getTotalCandidates(),
                    iRecruiterJobs.getJob_post_id(), iRecruiterJobs.getJob_title(), location, jobCompany));
        }
        return recruiterJobsDtoList;
    }

    @Override
    public JobPostActivity getOne(int id) {
        return jobPostActivityRepository.findById(id).orElseThrow( () ->
                new RuntimeException("Job not found"));
    }
}
