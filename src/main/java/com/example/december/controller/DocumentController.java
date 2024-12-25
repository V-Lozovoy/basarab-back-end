package com.example.december.controller;

import com.example.december.entity.Document;
import com.example.december.service.DocumentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<Document> getDocument(@PathVariable String code) {
        Document document;
        document = documentService.getDocument(code);
        return ResponseEntity.ok(document);
    }

//    @GetMapping(path = "/{code}")
//    @ResponseBody
//    public Document searchDocument(@RequestParam String code) {
//        return documentService.getDocument(code);
//    }

    @PostMapping
    public ResponseEntity<Document> addDocument(@RequestBody Document document) {
        Document savedDocument = documentService.addDocument(document);
        return ResponseEntity.ok(savedDocument);
    }

    @PutMapping(path = "/{code}")
    public ResponseEntity<Document> updateDocument(@PathVariable String code, @RequestBody Document updatedDocument) {
        Document document = documentService.updateDocument(code, updatedDocument);
        return ResponseEntity.ok(document);
    }

    @DeleteMapping(path = "/{code}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String code) {
        documentService.deleteDocument(code);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/user/{userLogin}")
    public ResponseEntity<List<Document>> getDocumentsByUser(@PathVariable String userLogin) {
        List<Document> documents = documentService.getDocumentsByUser(userLogin);
        return ResponseEntity.ok(documents);
    }

    @GetMapping(path = "/user/{userLogin}/signed")
    public ResponseEntity<List<Document>> getSignedDocumentsByUser(@PathVariable String userLogin) {
        List<Document> documents = documentService.getSignedDocumentByUser(userLogin);
        return ResponseEntity.ok(documents);
    }

    @GetMapping(path = "/user/{userLogin}/unsigned")
    public ResponseEntity<List<Document>> getUnsignedDocumentsByUser(@PathVariable String userLogin) {
        List<Document> documents = documentService.getUnsignedDocumentByUser(userLogin);
        return ResponseEntity.ok(documents);
    }

    @GetMapping(path = "/dates")
    public ResponseEntity<List<Document>> getDocumentByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Document> documents = documentService.getDocumentByDateRange(startDate, endDate);
        return ResponseEntity.ok(documents);
    }
}