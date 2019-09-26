package com.rybickim.javaquiz.controller;

import com.rybickim.javaquiz.payload.UploadFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FileRestController {

    UploadFileResponse uploadFile(MultipartFile file);
    List<UploadFileResponse> uploadMultipleFiles(MultipartFile[] files);
    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);

}
