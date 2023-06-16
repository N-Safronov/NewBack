package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
}
