package com.example.december.service;

import com.example.december.entity.Document;
import com.example.december.repository.DocumentRepository;
import com.example.december.type.DocumentType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public String getDocument(String code) {
        Optional<Document> documentOptional = documentRepository.findById(code);
        return documentOptional.map(Document::getBody).orElse("Document is not found");
    }

    public String write(String name) {
        return "Document " + name + " is writing";
    }
}
