package com.example.demo.service;

import com.example.demo.exaptions.ImageStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.util.UUID;

@Service
public class ImageStorageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String storeImage(MultipartFile file) {
        String fileName = generateFileName(file);
        try {
            Path filePath = Paths.get(uploadPath, fileName);
            Files.write(filePath, file.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            return generateImageUrl(fileName);
        } catch (IOException e) {
            throw new ImageStorageException("Failed to store image: " + fileName, e);
        }
    }

    private String generateFileName(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(originalFileName);
        String uniqueFileName = UUID.randomUUID().toString();
        return uniqueFileName + "." + extension;
    }

    private String generateImageUrl(String fileName) {
        return "http://localhost:8080/v1/file/" + fileName; // Формируйте URL в соответствии с вашей конфигурацией сервера
    }


    public String getFilePath(String filename) {
        return Paths.get(uploadPath, filename).toString();
    }
}
