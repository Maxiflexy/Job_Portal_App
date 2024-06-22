package com.maxiflexy.jobportalproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final FileStorageProperties fileStorageProperties;

    @Autowired
    public WebConfig(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadDir = fileStorageProperties.getUploadDir();

        String candidateUploadDir = fileStorageProperties.getCandidateUploadDir();

        String fullUploadDir = System.getProperty("user.dir") + "/" + uploadDir;

        String fullUploadDir2 = System.getProperty("user.dir") + "/" + candidateUploadDir;

        registry.addResourceHandler("/" + uploadDir + "/**")
                .addResourceLocations("file:" + fullUploadDir + "/");

        registry.addResourceHandler("/" + candidateUploadDir + "/**")
                .addResourceLocations("file:" + fullUploadDir2 + "/");
    }

}
