package com.chaeum.api.global.file.dto;

import com.chaeum.api.global.file.entity.UploadedFile;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileUploadResponse {

    private Long id;

    private String originalFileName;

    private String storedFileName;

    private String fileUrl;

    private Long fileSize;

    private String contentType;

    public static FileUploadResponse toDto(UploadedFile file) {
        return FileUploadResponse.builder()
            .id(file.getId())
            .originalFileName(file.getOriginalFileName())
            .storedFileName(file.getStoredFileName())
            .fileUrl(file.getFileUrl())
            .fileSize(file.getFileSize())
            .contentType(file.getContentType())
            .build();
    }
}
