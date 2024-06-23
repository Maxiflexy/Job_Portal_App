package com.maxiflexy.jobportalproject.services.impl;

import com.maxiflexy.jobportalproject.entity.JobPostActivity;
import com.maxiflexy.jobportalproject.entity.JobSeekerProfile;
import com.maxiflexy.jobportalproject.entity.JobSeekerSave;
import com.maxiflexy.jobportalproject.repository.JobSeekerSaveRepository;
import com.maxiflexy.jobportalproject.services.JobSeekerSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerSaveServiceImpl implements JobSeekerSaveService {

    private final JobSeekerSaveRepository jobSeekerSaveRepository;

    @Autowired
    public JobSeekerSaveServiceImpl(JobSeekerSaveRepository jobSeekerSaveRepository) {
        this.jobSeekerSaveRepository = jobSeekerSaveRepository;
    }

    public List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId){
        return jobSeekerSaveRepository.findByUserId(userAccountId);
    }

    public List<JobSeekerSave> getJobCandidates(JobPostActivity job){
        return jobSeekerSaveRepository.findByJob(job);
    }

    @Override
    public void addNew(JobSeekerSave jobSeekerSave) {
        jobSeekerSaveRepository.save(jobSeekerSave);
    }
}
