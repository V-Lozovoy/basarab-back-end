package com.example.december.service;

import com.example.december.entity.Document;
import com.example.december.repository.DocumentRepository;
import com.example.december.type.DocumentType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    // Знайти документ за кодом
    public Document getDocument(String code) {
        return documentRepository.findById(code).orElseThrow(() -> new IllegalArgumentException("Документ з кодом: " + code + " не знайдено"));
    }

    // Додати новий документ
    public Document addDocument(Document document) {
        return documentRepository.save(document);
    }

    // Оновити документ
    public Document updateDocument(String code, Document updatedDocument) {
        return documentRepository.findById(code).map(existingDocument -> {
            existingDocument.setName(updatedDocument.getName());
            existingDocument.setType(updatedDocument.getType());
            existingDocument.setBody(updatedDocument.getBody());
            existingDocument.setCreatedDate(updatedDocument.getCreatedDate());
            existingDocument.setSignedDate(updatedDocument.getSignedDate());
            existingDocument.setUserLogin(updatedDocument.getUserLogin());
            return documentRepository.save(existingDocument);
        }).orElseThrow(() -> new IllegalArgumentException("Документ з кодом: " + code + " не знайдено"));
    }

    // Видалити документ
    public void deleteDocument(String code) {
        documentRepository.deleteById(code);
    }

    // Знайти документ за логіном користувача
    public List<Document> getDocumentsByUser(String userLogin) {
        return documentRepository.findByUserLogin(userLogin);
    }

    // Знайти підписаний документ за логіном користувача
    public List<Document> getSignedDocumentByUser(String userLogin) {
        return documentRepository.findByUserLoginAndSignedDateIsNotNull(userLogin);
    }


    // Знайти не підписаний документ за логіном користувача
    public List<Document> getUnsignedDocumentByUser(String userLogin) {
        return documentRepository.findByUserLoginAndSignedDateIsNull(userLogin);
    }

    // Знайти документ за певний період
    public List<Document> getDocumentByDateRange(LocalDate startDate, LocalDate endDate) {
        return documentRepository.findByCreatedDateBetween(startDate, endDate);
    }

//    public String write(String name) {
//        return "Document " + name + " is writing";
//    }
}
