package com.example.december.repository;

import com.example.december.entity.Document;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {
    // Знайти документ за логіном користувача
    List<Document> findByUserLogin(String userLogin);

    // Знайти підписані документи за логіном користувача
    List<Document> findByUserLoginAndSignedDateIsNotNull(String userLogin);

    // Знайти не підписані документи за логіном користувача
    List<Document> findByUserLoginAndSignedDateIsNull(String userLogin);

    // Знайти документ за певний період
    List<Document> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}
