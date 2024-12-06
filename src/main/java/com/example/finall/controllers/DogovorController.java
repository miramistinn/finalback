package com.example.finall.controllers;

import com.example.finall.services.DogovorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@AllArgsConstructor
public class DogovorController {
    private final DogovorService dogovor;
    @PostMapping("dogovor/downlend/{Id}")
    public HttpStatus downlend(@PathVariable Long Id) {
        System.out.println(Id);
        dogovor.create(Id);
        return  HttpStatus.OK;

    }
}
