package com.rybickim.javaquiz.config;

import com.rybickim.javaquiz.property.FileStorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FileStorageProperties.class)
public class FileStorageConfig {
}
