package com.chaeum.api.global.file.controller;

import com.chaeum.api.global.file.dto.FileUploadResponse;
import com.chaeum.api.global.file.service.FileService;
import com.chaeum.api.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
@Tag(name = "File", description = "파일 관리")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "아이템 사진 업로드", description = "[ADMIN 이상 가능]")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(
        value = "/item",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<FileUploadResponse> uploadItemImage(
        @RequestParam(name = "multipartFile") MultipartFile multipartFile
    ) {
        FileUploadResponse fileUploadResponse = fileService.uploadFile(multipartFile, "item/");
        return ApiResponse.success(fileUploadResponse);
    }

    @Operation(summary = "펀딩 사진 업로드", description = "[DONOR 이상 가능]")
    @PreAuthorize("hasRole('DONOR')")
    @PostMapping(
        value = "/funding",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ApiResponse<List<FileUploadResponse>> uploadFundingImage(
        @RequestParam(name = "multipartFile") List<MultipartFile> multipartFiles
    ) {
        List<FileUploadResponse> fileUploadResponses = fileService.uploadFiles(multipartFiles, "funding/");
        return ApiResponse.success(fileUploadResponses);
    }
}
