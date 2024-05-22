package com.example.demo.repository;

import com.example.demo.entity.Minio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MinioRepository extends CrudRepository<Minio, Long> {
    @Query(value = "SELECT nextval('minio_status_id_seq')", nativeQuery = true)
    Long getNextMinioStatusId();


}
