package com.example.demo.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Service
public class MinioService {

    private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    @Autowired
    private MinioClient minioClient;

    public void createBucketIfNotExists(String bucketName) throws Exception {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                logger.info("Бакет {} успешно создан.", bucketName);
            } else {
                logger.info("Бакет {} уже существует.", bucketName);
            }
        } catch (MinioException e) {
            logger.error("Ошибка при создании бакета в MinIO", e);
            throw new Exception("Ошибка при создании бакета в MinIO", e);
        }
    }

//    public void uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType) throws Exception {
    public void uploadFile(String bucketName, String objectName, byte[] bytes, String contentType) throws Exception {
        try {
            // Создать бакет, если он не существует
            createBucketIfNotExists(bucketName);

            logger.info("Начало загрузки файла: {}", objectName);
            logger.info("Имя бакета: {}", bucketName);
            logger.info("Тип контента: {}", contentType);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
//                            .stream(inputStream, inputStream.available(), -1)
                            .stream(new ByteArrayInputStream(bytes),bytes.length, -1)
                            .contentType(contentType)
                            .build());
            logger.info("Файл успешно загружен: {}", objectName);
        } catch (MinioException e) {
            logger.error("Ошибка при загрузке файла в MinIO", e);
            throw new Exception("Ошибка при загрузке файла в MinIO", e);
        }
    }
}

