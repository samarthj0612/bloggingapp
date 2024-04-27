package com.samdb.bloggingapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    String uploadResources(String path, MultipartFile file) throws IOException;

    InputStream fetchResources(String path, String filename) throws FileNotFoundException;

}
