package com.example.demo.service;

import com.example.demo.dto.ReportData;
import com.example.demo.entity.Employee;
import com.example.demo.entity.Minio;
import com.example.demo.entity.User;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.repository.MinioRepository;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private MinioService minioService;

    @Autowired
    private MinioRepository minioRepository;

    public byte[] generateReport(){
        // id придёт c фронта
        Long id = minioRepository.getNextMinioStatusId();

        //Создание записи в таблице со статусами Minio
        Minio minio = new Minio();
        minio.setId(id);
        minio.setStatus("Pending");
        minio.setBucketName("my-bucket");
        minio.setFileName("Отправка Чси");
        minio.setDateEnd(Instant.now());
        minioRepository.save(minio);

        try {
            List<Employee> employees = repository.findAll();

            //Загрузка файла и данных для него
            File file = ResourceUtils.getFile("classpath:employees.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Java Techie");

            // Формирование файла, экспортирование в байты
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

            // Загрузка в Minio файла
            minioService.uploadFile("my-bucket", "employee_report.pdf", pdfBytes, "application/pdf");

            // Update данных Minio
            Minio minio2 = minioRepository.findById(id).orElseThrow(() -> new RuntimeException("Minio not found"));
            minio2.setDateEnd(Instant.now());
            minio2.setStatus("Success");

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            Minio minio2 = minioRepository.findById(id).orElseThrow(() -> new RuntimeException("Minio not found"));
            minio2.setDateEnd(Instant.now());
            minio2.setStatus("Error");
            minioRepository.save(minio2);
            throw new RuntimeException("Error generating report", e);
        }

//        response.setContentType("application/pdf");
//        response.addHeader("Content-Disposition", "inline; filename=report.pdf");
    }
}

