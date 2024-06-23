package com.maxiflexy.jobportalproject.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownLoadUtil {

    private Path foundFile;

    public Resource getFileAsResource(String downloadDir, String fileName) throws IOException {

        Path path = Paths.get(downloadDir);
        Files.list(path).forEach(file->{
            if(file.getFileName().toString().startsWith(fileName)){
                foundFile = file;
            }
        });

        if(foundFile != null){
            return new UrlResource(foundFile.toUri());
        }
        return null;
    }
}
