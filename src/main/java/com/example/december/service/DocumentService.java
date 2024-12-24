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
    public String getDocument(String code) {
        Optional<Document> documentOptional = documentRepository.findById(code);
        return documentOptional.map(Document::getBody).orElse("Document is not found");
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
            existingDocument.setCreated_date(updatedDocument.getCreated_date());
            existingDocument.setSigned_date(updatedDocument.getSigned_date());
            existingDocument.setUser_login(updatedDocument.getUser_login());
            return documentRepository.save(existingDocument);
        }).orElseThrow(() -> new IllegalArgumentException("Документ з кодом: " + code + " не знайдено"));
    }

    // Видалити документ
    public void deleteDocument(String code) {
        documentRepository.deleteById(code);
    }

    // Знайти документ за логіном користувача
    public List<Document> getDocumentsByUser(String user_login) {
        return documentRepository.findByUserLogin(user_login);
    }

    // Знайти підписаний документ за логіном користувача
    public List<Document> getSignedDocumentByUser(String user_login) {
        return documentRepository.findByUserLoginAndSignedDate(user_login);
    }


    // Знайти не підписаний документ за логіном користувача
    public List<Document> getUnsignedDocumentByUser(String user_login) {
        return documentRepository.findByUserLoginAndUnsignedDate(user_login);
    }

    // Знайти документ за певний період
    public List<Document> getDocumentByDateRange(LocalDate startDate, LocalDate endDate) {
        return documentRepository.findByCreatedDateBetween(startDate, endDate);
    }

//    public String write(String name) {
//        return "Document " + name + " is writing";
//    }
}
