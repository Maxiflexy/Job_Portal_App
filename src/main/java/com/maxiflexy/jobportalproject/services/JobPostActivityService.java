package com.maxiflexy.jobportalproject.services;

import com.maxiflexy.jobportalproject.entity.JobPostActivity;
import com.maxiflexy.jobportalproject.entity.RecruiterJobsDto;

import java.time.LocalDate;
import java.util.List;

public interface JobPostActivityService {

    JobPostActivity addNew(JobPostActivity jobPostActivity);

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter);

    JobPostActivity getOne(int id);

    List<JobPostActivity> search(String job, String location, List<String> list, List<String> list1, LocalDate searchDate);

    List<JobPostActivity> getAll();
}
