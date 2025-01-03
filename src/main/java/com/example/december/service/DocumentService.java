package com.example.december.service;

import com.example.december.dto.DocumentDto;
import com.example.december.entity.Document;
import com.example.december.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public DocumentDto getDocument(String code) {
        Document document = documentRepository.findById(code).orElseThrow(() -> new IllegalArgumentException("Документ з кодом: " + code + " не знайдено"));
        return convertToDto(document);
    }

    public List<DocumentDto> getDocuments() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public DocumentDto addDocument(DocumentDto documentDto) {
        Document document = convertToEntity(documentDto);
        Document savedDocument = documentRepository.save(document);
        return convertToDto(savedDocument);
    }

    @Transactional
    public DocumentDto updateDocument(String code, DocumentDto updatedDocumentDto) {
        return documentRepository.findById(code).map(existingDocument -> {
            existingDocument.setName(updatedDocumentDto.getName());
            existingDocument.setType(updatedDocumentDto.getType());
            existingDocument.setBody(updatedDocumentDto.getBody());
            existingDocument.setCreatedDate(updatedDocumentDto.getCreatedDate());
            existingDocument.setSignedDate(updatedDocumentDto.getSignedDate());
            existingDocument.setUserLogin(updatedDocumentDto.getUserLogin());

            Document updatedDocument = documentRepository.save(existingDocument);
            return convertToDto(updatedDocument);
        }).orElseThrow(() -> new IllegalArgumentException("Документ з кодом: " + code + " не знайдено"));
    }

    public void deleteDocument(String code) {
        documentRepository.deleteById(code);
    }

    public List<DocumentDto> getDocumentsByUser(String userLogin) {
        return documentRepository.findByUserLogin(userLogin).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<DocumentDto> getSignedDocumentByUser(String userLogin) {
        return documentRepository.findByUserLoginAndSignedDateIsNotNull(userLogin).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<DocumentDto> getUnsignedDocumentByUser(String userLogin) {
        return documentRepository.findByUserLoginAndSignedDateIsNull(userLogin).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<DocumentDto> getDocumentByDateRange(LocalDate startDate, LocalDate endDate) {
        return documentRepository.findByCreatedDateBetween(startDate, endDate).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private DocumentDto convertToDto(Document document) {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setCode(document.getCode());
        documentDto.setName(document.getName());
        documentDto.setType(document.getType());
        documentDto.setBody(document.getBody());
        documentDto.setCreatedDate(document.getCreatedDate());
        documentDto.setSignedDate(document.getSignedDate());
        documentDto.setUserLogin(document.getUserLogin());
        return documentDto;
    }

    private Document convertToEntity(DocumentDto documentDto) {
        Document document = new Document();
        document.setCode(documentDto.getCode());
        document.setName(documentDto.getName());
        document.setType(documentDto.getType());
        document.setBody(documentDto.getBody());
        document.setCreatedDate(documentDto.getCreatedDate());
        document.setSignedDate(documentDto.getSignedDate());
        document.setUserLogin(documentDto.getUserLogin());
        return document;
    }
}