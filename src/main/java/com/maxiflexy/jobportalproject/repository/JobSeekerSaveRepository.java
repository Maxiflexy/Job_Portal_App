package com.maxiflexy.jobportalproject.repository;

import com.maxiflexy.jobportalproject.entity.JobPostActivity;
import com.maxiflexy.jobportalproject.entity.JobSeekerProfile;
import com.maxiflexy.jobportalproject.entity.JobSeekerSave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobSeekerSaveRepository extends JpaRepository<JobSeekerSave, Integer> {

    List<JobSeekerSave> findByUserId(JobSeekerProfile userAccountId);

    List<JobSeekerSave> findByJob(JobPostActivity job);

}
