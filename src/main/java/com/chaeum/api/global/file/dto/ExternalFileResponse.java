package com.chaeum.api.global.file.dto;

import com.chaeum.api.global.file.entity.UploadedFile;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExternalFileResponse {

    private String fileUrl;

    private Long fileSize;

    private String contentType;

    public static ExternalFileResponse toDto(UploadedFile uploadedFile) {
        return ExternalFileResponse.builder()
            .fileUrl(uploadedFile.getFileUrl())
            .fileSize(uploadedFile.getFileSize())
            .contentType(uploadedFile.getContentType())
            .build();
    }

    public static List<ExternalFileResponse> toListDto(List<UploadedFile> uploadedFiles) {
        return uploadedFiles.stream()
            .map(ExternalFileResponse::toDto)
            .toList();
    }
}
