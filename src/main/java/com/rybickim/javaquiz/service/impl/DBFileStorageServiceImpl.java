package com.rybickim.javaquiz.service.impl;

import com.rybickim.javaquiz.data.DBFileRepository;
import com.rybickim.javaquiz.domain.DBFile;
import com.rybickim.javaquiz.exception.FileStorageException;
import com.rybickim.javaquiz.exception.MyFileNotFoundException;
import com.rybickim.javaquiz.service.DBFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DBFileStorageServiceImpl implements DBFileStorageService {

    private DBFileRepository dbFileRepository;

    @Autowired
    public DBFileStorageServiceImpl(DBFileRepository dbFileRepository) {
        this.dbFileRepository = dbFileRepository;
    }

    @Override
    public DBFile storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return dbFileRepository.save(dbFile);
        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    @Override
    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    @Override
    public DBFile getFileByFileName(String fileName) {
        return dbFileRepository.findFirstByFileName(fileName);
    }
}
