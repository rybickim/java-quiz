package com.rybickim.javaquiz.service;

import com.rybickim.javaquiz.domain.DBFile;
import org.springframework.web.multipart.MultipartFile;

public interface DBFileStorageService {

    DBFile storeFile(MultipartFile file);
    DBFile getFile(String fileId);

    DBFile getFileByFileName(String fileName);
}
