package com.maxiflexy.jobportalproject.services;

import com.maxiflexy.jobportalproject.entity.JobPostActivity;
import com.maxiflexy.jobportalproject.entity.JobSeekerProfile;
import com.maxiflexy.jobportalproject.entity.JobSeekerSave;

import java.util.List;

public interface JobSeekerSaveService {

    List<JobSeekerSave> getCandidatesJob(JobSeekerProfile userAccountId);

    List<JobSeekerSave> getJobCandidates(JobPostActivity job);

    void addNew(JobSeekerSave jobSeekerSave);
}
