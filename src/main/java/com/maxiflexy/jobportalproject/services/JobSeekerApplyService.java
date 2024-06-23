package com.maxiflexy.jobportalproject.services;

import com.maxiflexy.jobportalproject.entity.JobPostActivity;
import com.maxiflexy.jobportalproject.entity.JobSeekerApply;
import com.maxiflexy.jobportalproject.entity.JobSeekerProfile;

import java.util.List;

public interface JobSeekerApplyService {

    List<JobSeekerApply> getCandidatesJobs(JobSeekerProfile userAccountId);

    List<JobSeekerApply> getJobCandidates(JobPostActivity job);

    void addNew(JobSeekerApply jobSeekerApply);
}
