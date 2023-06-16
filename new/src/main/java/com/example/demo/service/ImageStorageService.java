package com.example.demo.service;

import com.example.demo.exaptions.ImageStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageStorageService {

    @Value("${upload.path}") // Путь к папке, где будут храниться загруженные картинки
    private String uploadPath;

    public String storeImage(MultipartFile file) {
        String fileName = generateFileName(file);
        try {
            Path filePath = Paths.get(uploadPath, fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return generateImageUrl(fileName);
        } catch (IOException e) {
            // Обработайте ошибку сохранения файла
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
        return "/images/" + fileName; // Формируйте URL в соответствии с вашей конфигурацией сервера
    }
}
