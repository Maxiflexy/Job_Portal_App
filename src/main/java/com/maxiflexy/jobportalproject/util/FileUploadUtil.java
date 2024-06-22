package com.maxiflexy.jobportalproject.util;

import com.maxiflexy.jobportalproject.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadUtil {

    private final String uploadDir;
    private final String candidateUploadDir;

    @Autowired
    public FileUploadUtil(FileStorageProperties fileStorageProperties) {
        this.uploadDir = fileStorageProperties.getUploadDir();
        this.candidateUploadDir = fileStorageProperties.getCandidateUploadDir();
    }

    public void saveFile(String subDir, String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(uploadDir, subDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = multipartFile.getInputStream()){

            Path path = uploadPath.resolve(fileName);
            System.out.println("FilePath " + path);
            System.out.println("FileName " + fileName);
            System.out.println("FilePath: " + path.toAbsolutePath());
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

        }catch (IOException exception){
            throw new RuntimeException("Could not save image file: " + fileName, exception);
        }
    }

    public void saveFileSeeker(String subDir, String fileName, MultipartFile multipartFile) throws IOException {

        Path uploadPath = Paths.get(candidateUploadDir, subDir);

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        try(InputStream inputStream = multipartFile.getInputStream()){

            Path path = uploadPath.resolve(fileName);
            System.out.println("FilePath " + path);
            System.out.println("FileName " + fileName);
            System.out.println("FilePath: " + path.toAbsolutePath());
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

        }catch (IOException exception){
            throw new RuntimeException("Could not save image file: " + fileName, exception);
        }
    }
}
