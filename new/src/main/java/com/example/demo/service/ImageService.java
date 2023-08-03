package com.example.demo.service;

import com.example.demo.exaptions.ImageStorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {

    private final ImageStorageService imageStorageService;

    public ImageService(ImageStorageService imageStorageService) {
        this.imageStorageService = imageStorageService;
    }

    public String uploadImage(MultipartFile file) {
        String imageUrl = imageStorageService.storeImage(file);
        // Сохраните imageUrl в базу данных для соответствующей сущности или таблицы
        // ...
        return imageUrl;
    }

    public byte[] getImageContent(String filename) {
        String filePath = imageStorageService.getFilePath(filename);

        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            throw new ImageStorageException("Image not found: " + filename, e);
        }
    }
}
