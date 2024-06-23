package com.maxiflexy.jobportalproject.controller;

import com.maxiflexy.jobportalproject.entity.JobSeekerProfile;
import com.maxiflexy.jobportalproject.entity.Skills;
import com.maxiflexy.jobportalproject.entity.Users;
import com.maxiflexy.jobportalproject.repository.UsersRepository;
import com.maxiflexy.jobportalproject.services.JobSeekerProfileService;
import com.maxiflexy.jobportalproject.util.FileDownLoadUtil;
import com.maxiflexy.jobportalproject.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController {

    private final JobSeekerProfileService jobSeekerProfileService;
    private final UsersRepository usersRepository;
    private final FileUploadUtil fileUploadUtil;

    @Autowired
    public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService,
                                      UsersRepository usersRepository, FileUploadUtil fileUploadUtil) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.usersRepository = usersRepository;
        this.fileUploadUtil = fileUploadUtil;
    }


    @GetMapping("/")
    public String jobSeekerProfile(Model model){

        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<Skills> skills = new ArrayList<>();

        if(!(authentication instanceof AnonymousAuthenticationToken)){
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

            Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(user.getUserId());
            if(seekerProfile.isPresent()){
                jobSeekerProfile = seekerProfile.get();
                if(jobSeekerProfile.getSkills().isEmpty()){
                    skills.add(new Skills());
                    jobSeekerProfile.setSkills(skills);
                }
            }

            model.addAttribute("skills", skills);
            model.addAttribute("profile", jobSeekerProfile);
        }

        return "job-seeker-profile";
    }


    @PostMapping("/addNew")
    public String addNew(JobSeekerProfile jobSeekerProfile, @RequestParam("image")MultipartFile multipartFileImage,
                         @RequestParam("pdf") MultipartFile multipartFilePdf, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(!(authentication instanceof  AnonymousAuthenticationToken)){
            Users user = usersRepository.findByEmail(authentication.getName()).orElseThrow(() ->
                    new UsernameNotFoundException("User not found"));

            jobSeekerProfile.setUserId(user);
            jobSeekerProfile.setUserAccountId(user.getUserId());
        }

        List<Skills> skillsList = new ArrayList<>();

        model.addAttribute("profile", jobSeekerProfile);
        model.addAttribute("skills", skillsList);

        for(Skills skill : jobSeekerProfile.getSkills()){
            skill.setJobSeekerProfile(jobSeekerProfile);
        }

        String imageName = "";
        String resumeName = "";

        if(!multipartFileImage.getOriginalFilename().equals("")){
            imageName = StringUtils.cleanPath(Objects.requireNonNull(multipartFileImage.getOriginalFilename()));
            jobSeekerProfile.setProfilePhoto(imageName);
        }

        if(!multipartFilePdf.getOriginalFilename().equals("")){
            resumeName = StringUtils.cleanPath(Objects.requireNonNull(multipartFilePdf.getOriginalFilename()));
            jobSeekerProfile.setResume(resumeName);
        }

        JobSeekerProfile seekerProfile = jobSeekerProfileService.addNew(jobSeekerProfile);

        String subDir = String.valueOf(seekerProfile.getUserAccountId());

        try{

            if(!Objects.equals(multipartFileImage.getOriginalFilename(), "")){
                fileUploadUtil.saveFileSeeker(subDir, imageName, multipartFileImage);
            }
            if(!Objects.equals(multipartFilePdf.getOriginalFilename(), "")){
                fileUploadUtil.saveFileSeeker(subDir, resumeName, multipartFilePdf);
            }

        }catch (IOException exception){
            throw new RuntimeException(exception);
        }
        return "redirect:/dashboard/";
    }


    @GetMapping("/{id}")
    public String candidateProfile(@PathVariable("id") int id, Model model){

        Optional<JobSeekerProfile> seekerProfile = jobSeekerProfileService.getOne(id);
        if (seekerProfile.isPresent()) {
            model.addAttribute("profile", seekerProfile.get());
        }else {
            return "redirect:/dashboard/";
        }
        return "job-seeker-profile";
    }


    @GetMapping("/downloadResume")
    public ResponseEntity<?> downloadResume(@RequestParam(value = "fileName") String fileName,
                                            @RequestParam(value = "userID") String userId){

        FileDownLoadUtil fileDownLoadUtil = new FileDownLoadUtil();
        Resource resource = null;

        try{
            resource = fileDownLoadUtil.getFileAsResource( "photos/candidate/" + userId, fileName);

        } catch (IOException exception){
            return ResponseEntity.badRequest().build();
        }

        if(resource == null){
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment: filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
