package com.example.december.controller;

import com.example.december.dto.DocumentDto;
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
    public ResponseEntity<DocumentDto> getDocument(@PathVariable String code) {
        DocumentDto document = documentService.getDocument(code);
        return ResponseEntity.ok(document);
    }

//    @GetMapping(path = "/{code}")
//    @ResponseBody
//    public Document searchDocument(@RequestParam String code) {
//        return documentService.getDocument(code);
//    }

    @PostMapping
    public ResponseEntity<DocumentDto> addDocument(@RequestBody DocumentDto documentDto) {
        DocumentDto savedDocument = documentService.addDocument(documentDto);
        return ResponseEntity.ok(savedDocument);
    }

    @PutMapping(path = "/{code}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable String code, @RequestBody DocumentDto updatedDocumentDto) {
        DocumentDto updatedDocument = documentService.updateDocument(code, updatedDocumentDto);
        return ResponseEntity.ok(updatedDocument);
    }

    @DeleteMapping(path = "/{code}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String code) {
        documentService.deleteDocument(code);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/user/{userLogin}")
    public ResponseEntity<List<DocumentDto>> getDocumentsByUser(@PathVariable String userLogin) {
        List<DocumentDto> documents = documentService.getDocumentsByUser(userLogin);
        return ResponseEntity.ok(documents);
    }

    @GetMapping(path = "/user/{userLogin}/signed")
    public ResponseEntity<List<DocumentDto>> getSignedDocumentsByUser(@PathVariable String userLogin) {
        List<DocumentDto> documents = documentService.getSignedDocumentByUser(userLogin);
        return ResponseEntity.ok(documents);
    }

    @GetMapping(path = "/user/{userLogin}/unsigned")
    public ResponseEntity<List<DocumentDto>> getUnsignedDocumentsByUser(@PathVariable String userLogin) {
        List<DocumentDto> documents = documentService.getUnsignedDocumentByUser(userLogin);
        return ResponseEntity.ok(documents);
    }

    @GetMapping(path = "/dates")
    public ResponseEntity<List<DocumentDto>> getDocumentByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DocumentDto> documents = documentService.getDocumentByDateRange(startDate, endDate);
        return ResponseEntity.ok(documents);
    }
}