package com.chaeum.api.global.file.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileUploadResult {

    private String storedFileName;

    private String fileUrl;

    public static FileUploadResult create(String storedFileName, String fileUrl) {
        return FileUploadResult.builder()
            .storedFileName(storedFileName)
            .fileUrl(fileUrl)
            .build();
    }
}
