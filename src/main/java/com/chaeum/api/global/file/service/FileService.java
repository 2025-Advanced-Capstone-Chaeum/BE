package com.chaeum.api.global.file.service;

import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.file.dto.FileUploadResponse;
import com.chaeum.api.global.file.dto.FileUploadResult;
import com.chaeum.api.global.file.entity.UploadedFile;
import com.chaeum.api.global.file.repository.FileRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final StorageService storageService;
    private final FileRepository fileRepository;

    @Transactional
    public List<FileUploadResponse> uploadFiles(List<MultipartFile> files, String folder) {
        List<FileUploadResponse> fileUploadResponses = new ArrayList<>();
        for (MultipartFile file : files) {
            FileUploadResponse fileUploadResponse = uploadFile(file, folder);
            fileUploadResponses.add(fileUploadResponse);
        }
        return fileUploadResponses;
    }

    @Transactional(readOnly = true)
    public List<UploadedFile> getUploadedFilesByUrls(List<String> fileUrls) {
        if (fileUrls == null || fileUrls.isEmpty()) {
            return new ArrayList<>();
        }
        List<UploadedFile> uploadedFiles = fileRepository.findAllByFileUrlIn(fileUrls);
        if (uploadedFiles.size() != fileUrls.size()) {
            throw ChaeumException.from(ErrorCode.INCLUDE_NOT_UPLOADED_FILE);
        }
        return uploadedFiles;
    }

    public FileUploadResponse uploadFile(MultipartFile file, String folder) {
        FileUploadResult result = storageService.uploadFile(file, folder);
        UploadedFile uploadedFile = UploadedFile.toEntity(result, file);
        fileRepository.save(uploadedFile);
        return FileUploadResponse.toDto(uploadedFile);
    }
}
