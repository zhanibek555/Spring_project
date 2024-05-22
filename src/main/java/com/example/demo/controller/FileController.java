package com.example.demo.controller;

import com.example.demo.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
//            minioService.uploadFile("my-bucket", file.getOriginalFilename(), file.getInputStream(), file.getContentType());
            minioService.uploadFile("my-bucket", file.getOriginalFilename(), bytes, file.getContentType());
            return new ResponseEntity<>("Файл успешно загружен", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Ошибка загрузки файла: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
