package com.maxiflexy.jobportalproject.services.impl;

import com.maxiflexy.jobportalproject.entity.JobPostActivity;
import com.maxiflexy.jobportalproject.entity.JobSeekerApply;
import com.maxiflexy.jobportalproject.entity.JobSeekerProfile;
import com.maxiflexy.jobportalproject.repository.JobSeekerApplyRepository;
import com.maxiflexy.jobportalproject.services.JobSeekerApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerApplyServiceImpl implements JobSeekerApplyService {

    private final JobSeekerApplyRepository jobSeekerApplyRepository;

    @Autowired
    public JobSeekerApplyServiceImpl(JobSeekerApplyRepository jobSeekerApplyRepository) {
        this.jobSeekerApplyRepository = jobSeekerApplyRepository;
    }

    public List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId){
        return jobSeekerApplyRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerApply> getJobCandidates(JobPostActivity job){
        return jobSeekerApplyRepository.findByJob(job);
    }

    @Override
    public void addNew(JobSeekerApply jobSeekerApply) {
        jobSeekerApplyRepository.save(jobSeekerApply);
    }


}
