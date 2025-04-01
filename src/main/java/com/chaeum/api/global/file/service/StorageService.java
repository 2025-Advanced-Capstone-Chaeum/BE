package com.chaeum.api.global.file.service;

import com.chaeum.api.global.file.dto.FileUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    FileUploadResult uploadFile(MultipartFile file, String folder);
}
