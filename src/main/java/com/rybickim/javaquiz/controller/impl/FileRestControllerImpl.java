package com.rybickim.javaquiz.controller.impl;


import com.rybickim.javaquiz.controller.FileRestController;
import com.rybickim.javaquiz.domain.DBFile;
import com.rybickim.javaquiz.payload.UploadFileResponse;
import com.rybickim.javaquiz.service.DBFileStorageService;
import com.rybickim.javaquiz.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileRestControllerImpl implements FileRestController {

    private static final Logger logger = LoggerFactory.getLogger(FileRestControllerImpl.class);

    private DBFileStorageService dbFileStorageService;

    @Autowired
    public FileRestControllerImpl(DBFileStorageService dbFileStorageService) {
        this.dbFileStorageService = dbFileStorageService;
    }

    @PostMapping("/uploadFile")
    @Override
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    @Override
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    @Override
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {

        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
}
