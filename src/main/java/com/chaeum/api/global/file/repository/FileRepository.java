package com.chaeum.api.global.file.repository;

import com.chaeum.api.global.file.entity.UploadedFile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<UploadedFile, Long> {

    Optional<UploadedFile> findByFileUrl(String fileUrl);

    List<UploadedFile> findAllByFileUrlIn(List<String> fileUrls);
}
