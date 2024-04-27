package com.samdb.bloggingapp.services.impl;

import com.samdb.bloggingapp.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadResources(String path, MultipartFile file) throws IOException {
        // Create resource folder if not exists
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        // Unique file name generator
        String randomId = UUID.randomUUID().toString();
        String originalFilename = file.getOriginalFilename();  // Filename
        String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

        // Path to folder and File copy
        String filePath = path + File.separator + fileName;
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream fetchResources(String path, String filename) throws FileNotFoundException {
        String fullPath = path + File.separator + filename;
        InputStream inputStream;
        inputStream = new FileInputStream(fullPath);
        return inputStream;
    }
}
