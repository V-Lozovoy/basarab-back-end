package com.example.december.controller;

import com.example.december.service.DocumentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
public class DocumentController {
    private final DocumentService documentService;
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }


    @GetMapping(path = "/documents")
    @ResponseBody
    public String searchDocument(@RequestParam String code) {
        return documentService.getDocument(code);
    }

    @PostMapping(path = "/documents")
    public String addDocument(@RequestBody String document) {
        return "Document: " + "\"" + document + "\"" + " is added.";
    }

    @PutMapping(path = "/documents")
    public void updateDocument() {

    }

    @DeleteMapping(path = "/documents")
    public void deleteDocument() {

    }
}