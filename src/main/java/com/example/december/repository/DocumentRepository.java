package com.example.december.repository;

import com.example.december.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {
    // Знайти документ за логіном користувача, user_login написал через прочерк, а не CamelCase
    List<Document> findByUserLogin(String user_login);

    // Знайти підписані документи за логіном користувача, user_login написал через прочерк, а не CamelCase
    List<Document> findByUserLoginAndSignedDate(String user_login);

    // Знайти не підписані документи за логіном користувача, user_login написал через прочерк, а не CamelCase
    List<Document> findByUserLoginAndUnsignedDate(String user_login);

    // Знайти документ за певний період
    List<Document> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}
