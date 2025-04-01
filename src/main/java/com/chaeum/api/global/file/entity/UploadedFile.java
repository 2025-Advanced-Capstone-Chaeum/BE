package com.chaeum.api.global.file.entity;

import com.chaeum.api.global.entity.BaseEntity;
import com.chaeum.api.global.file.dto.FileUploadResult;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "uploaded_file")
public class UploadedFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uploaded_file_id")
    private Long id;

    @Column(name = "original_file_name", nullable = false)
    private String originalFileName;

    @Column(name = "stored_file_name", nullable = false, unique = true)
    private String storedFileName;

    @Column(name = "file_url", nullable = false)
    private String fileUrl;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "content_type")
    private String contentType;

    public static UploadedFile toEntity(FileUploadResult fileUploadResult, MultipartFile file) {
        return UploadedFile.builder()
            .originalFileName(file.getOriginalFilename())
            .storedFileName(fileUploadResult.getStoredFileName())
            .fileUrl(fileUploadResult.getFileUrl())
            .fileSize(file.getSize())
            .contentType(file.getContentType())
            .build();
    }
}
