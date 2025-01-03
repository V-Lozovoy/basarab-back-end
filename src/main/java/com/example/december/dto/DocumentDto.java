package com.example.december.dto;
import com.example.december.type.DocumentType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DocumentDto {
    @Schema(name = "code", title = "Унікальний код документа")
    private String code;

    @Schema(name = "name", title = "Назва документа")
    private String name;

    @Schema(name = "type", title = "Тип документа")
    private DocumentType type;

    @Schema(name = "body", title = "Опис документа")
    private String body;

    @Schema(name = "createdDate", title = "Дата створення документу")
    private LocalDate createdDate;

    @Schema(name = "signedDate", title = "Дата підпису документу")
    private LocalDate signedDate;

    @Schema(name = "code", title = "Логін користувача")
    private String userLogin;
}