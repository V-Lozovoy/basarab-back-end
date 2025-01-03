package com.example.december.entity;

import com.example.december.type.DocumentType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "documents")
@Data
public class Document {
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DocumentType type;

    @Column(name = "body")
    private String body;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "signed_date")
    private LocalDate signedDate;

    @Column(name = "user_login")
    private String userLogin;
}