package com.example.december.service;
import com.example.december.dto.DocumentDto;
import com.example.december.entity.Document;
import com.example.december.repository.DocumentRepository;
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

    // Знайти документ за кодом
    public DocumentDto getDocument(String code) {
        Document document = documentRepository.findById(code).orElseThrow(() -> new IllegalArgumentException("Документ з кодом: " + code + " не знайдено"));
        return convertToDto(document);
    }

    // Додати новий документ
    public DocumentDto addDocument(DocumentDto documentDto) {
        Document document = convertToEntity(documentDto);
        Document savedDocument = documentRepository.save(document);
        return convertToDto(savedDocument);
    }

    // Оновити документ
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

    // Видалити документ
    public void deleteDocument(String code) {
        documentRepository.deleteById(code);
    }

    // Знайти документ за логіном користувача
    public List<DocumentDto> getDocumentsByUser(String userLogin) {
        return documentRepository.findByUserLogin(userLogin).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Знайти підписаний документ за логіном користувача
    public List<DocumentDto> getSignedDocumentByUser(String userLogin) {
        return documentRepository.findByUserLoginAndSignedDateIsNotNull(userLogin).stream().map(this::convertToDto).collect(Collectors.toList());
    }


    // Знайти не підписаний документ за логіном користувача
    public List<DocumentDto> getUnsignedDocumentByUser(String userLogin) {
        return documentRepository.findByUserLoginAndSignedDateIsNull(userLogin).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Знайти документ за певний період
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

//    public String write(String name) {
//        return "Document " + name + " is writing";
//    }

}