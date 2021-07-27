package com.currency.converter.controllers;

import com.currency.converter.dto.TransactionRequestDTO;
import com.currency.converter.dto.TransactionResponseDTO;
import com.currency.converter.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @RequestBody @Valid TransactionRequestDTO requestDTO){
        var response = service.createTransaction(requestDTO);
        URI uri = fromController(getClass()).path("/{id}")
                .buildAndExpand(response.getIdTransaction())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> find(
            @RequestParam(required = false) Long idUser){
        List<TransactionResponseDTO> response;
        if(idUser != null){
            response = service.findByIdUser(idUser);
        } else {
            response = service.findAll();
        }
        return  ResponseEntity.ok(response);
    }

}
