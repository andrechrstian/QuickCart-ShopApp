package org.example.tokonyadia.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    public String storeFile(MultipartFile file, String id);
    Resource loadFileAsResource(String fileName);
}
