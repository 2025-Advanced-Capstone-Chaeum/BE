package com.chaeum.api.global.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.chaeum.api.global.exception.ChaeumException;
import com.chaeum.api.global.exception.ErrorCode;
import com.chaeum.api.global.file.dto.FileUploadResult;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Primary
public class S3StorageService implements StorageService {

    private final AmazonS3Client amazonS3Client;
    private final String bucketName;

    private static final List<String> ALLOWED_EXTENSIONS =
        List.of(".jpg", ".jpeg", ".png", ".gif", ".pdf");

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    public S3StorageService(
        AmazonS3Client amazonS3Client,
        @Value("${cloud.aws.s3.bucket}") String bucketName
    ) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
    }

    @Override
    public FileUploadResult uploadFile(MultipartFile file, String folder) {
        validateFileSize(file);
        String originalFilename = file.getOriginalFilename();
        String fileExtension = extractFileExtension(originalFilename);
        validateExtension(fileExtension);
        String checkedFolder = checkFolder(folder);
        String storedFileName = checkedFolder + UUID.randomUUID() + fileExtension;
        return uploadS3(file, storedFileName);
    }

    private FileUploadResult uploadS3(MultipartFile file, String storedFileName) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            metadata.setContentDisposition("inline");
            amazonS3Client.putObject(new PutObjectRequest(bucketName, storedFileName, file.getInputStream(), metadata));
            String fileUrl = amazonS3Client.getUrl(bucketName, storedFileName).toString();
            return FileUploadResult.create(storedFileName, fileUrl);
        } catch (IOException e) {
            throw ChaeumException.from(ErrorCode.S3_UPLOAD_FAILURE);
        }
    }

    private String extractFileExtension(String originalFilename) {
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        return fileExtension;
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw ChaeumException.from(ErrorCode.FILE_SIZE_EXCEEDED);
        }
    }

    private void validateExtension(String extension) {
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw ChaeumException.from(ErrorCode.UNSUPPORTED_FILE_EXTENSION);
        }
    }

    private String checkFolder(String folder) {
        return folder.endsWith("/") ? folder : folder + "/";
    }
}
