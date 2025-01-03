package com.example.december.repository;

import com.example.december.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {
    List<Document> findByUserLogin(String userLogin);
    List<Document> findByUserLoginAndSignedDateIsNotNull(String userLogin);
    List<Document> findByUserLoginAndSignedDateIsNull(String userLogin);
    List<Document> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}
