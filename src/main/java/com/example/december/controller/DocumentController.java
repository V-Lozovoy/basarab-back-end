package com.example.december.controller;

import com.example.december.dto.DocumentDto;
import com.example.december.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/documents")
@Tag(name = "Управління документами", description = "Застосунок (API) для управління документами")
public class DocumentController {
    private final DocumentService documentService;
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Operation(summary = "Отримати документ за кодом", description = "Повертається документ за відповідним унікальним кодом",
               responses = {@ApiResponse(responseCode = "200", description = "Документ знайдено",
               content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class))),
               @ApiResponse(responseCode = "404", description = "Документ не знайдено")
    })
    @GetMapping(path = "/{code}")
    public ResponseEntity<DocumentDto> getDocument(@PathVariable String code) {
        DocumentDto document = documentService.getDocument(code);
        return ResponseEntity.ok(document);
    }

    @Operation(summary = "Отримати усі документи", description = "Показуються усі документи, що є в нашій Базі даних",
            responses = {@ApiResponse(responseCode = "200", description = "Усі документи показані",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class))),
                    @ApiResponse(responseCode = "404", description = "Не всі документи показані")
            })
    @GetMapping(path = "/all")
    public List<DocumentDto> getAllDocuments() {
        return documentService.getDocuments();
    }

    @Operation(summary = "Додати новий документ", description = "Додає новий документ до нашої бази даних",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Дані нового документа", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class))),
            responses = {@ApiResponse(responseCode = "200", description = "Документ додано до Бази даних",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentDto.class)))})
    @PostMapping
    public ResponseEntity<DocumentDto> addDocument(@RequestBody DocumentDto documentDto) {
        DocumentDto savedDocument = documentService.addDocument(documentDto);
        return ResponseEntity.ok(savedDocument);
    }

    @Operation(summary = "Оновити документ", description = "Змінює дані вже існуючого документа за відповідним кодом",
               responses = {@ApiResponse(responseCode = "200", description = "Документ оновлено")})
    @PutMapping(path = "/{code}")
    public ResponseEntity<DocumentDto> updateDocument(@PathVariable String code, @RequestBody DocumentDto updatedDocumentDto) {
        DocumentDto updatedDocument = documentService.updateDocument(code, updatedDocumentDto);
        return ResponseEntity.ok(updatedDocument);
    }

    @Operation(summary = "Видалити документ", description = "Видаляє певний документ за відповідним кодом",
               responses = {@ApiResponse(responseCode = "204", description = "Документ видалено"),
                            @ApiResponse(responseCode = "404", description = "Документ не знайдено")})
    @DeleteMapping(path = "/{code}")
    public ResponseEntity<Void> deleteDocument(@PathVariable String code) {
        documentService.deleteDocument(code);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Отримати документ за логіном користувача", description = "Отримуємо дані про документ за логіном користувача, якому належить даний документ")
    @GetMapping(path = "/user/{userLogin}")
    public ResponseEntity<List<DocumentDto>> getDocumentsByUser(@PathVariable String userLogin) {
        List<DocumentDto> documents = documentService.getDocumentsByUser(userLogin);
        return ResponseEntity.ok(documents);
    }

    @Operation(summary = "Отримати підписані документи користувача", description = "Отримуємо дані про усі підписані документи за логіном користувача, якому належить даний документ")
    @GetMapping(path = "/user/{userLogin}/signed")
    public ResponseEntity<List<DocumentDto>> getSignedDocumentsByUser(@PathVariable String userLogin) {
        List<DocumentDto> documents = documentService.getSignedDocumentByUser(userLogin);
        return ResponseEntity.ok(documents);
    }

    @Operation(summary = "Отримати НЕ підписані документи користувача", description = "Отримуємо дані про усі НЕ підписані документи за логіном користувача, якому належить даний документ")
    @GetMapping(path = "/user/{userLogin}/unsigned")
    public ResponseEntity<List<DocumentDto>> getUnsignedDocumentsByUser(@PathVariable String userLogin) {
        List<DocumentDto> documents = documentService.getUnsignedDocumentByUser(userLogin);
        return ResponseEntity.ok(documents);
    }

    @Operation(summary = "Отримати усі документи за певний період", description = "Отримує список документів, які були створені у вказаний період",
            responses = {@ApiResponse(responseCode = "200", description = "Документи успішно отримано")})
    @GetMapping(path = "/dates")
    public ResponseEntity<List<DocumentDto>> getDocumentByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DocumentDto> documents = documentService.getDocumentByDateRange(startDate, endDate);
        return ResponseEntity.ok(documents);
    }
}