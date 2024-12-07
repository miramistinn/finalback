package com.example.finall.controllers;

import com.example.finall.entity.Additional;
import com.example.finall.services.CalculationService;
import com.example.finall.services.DogovorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@AllArgsConstructor
public class DogovorController {
    private final DogovorService dogovor;
    private final CalculationService calculationService;
    @PostMapping("dogovor/downlend/{Id}")
    public HttpStatus downlend(@PathVariable Long Id) {
        System.out.println(Id);
        dogovor.create(Id);
        return  HttpStatus.OK;
    }
    @GetMapping("dop/cost/{id}")
    public ResponseEntity<List<Double>> readCost(@PathVariable Long id) {
        System.out.println("пришло на сервер readCost");
        return new ResponseEntity<>(calculationService.takeList(id), HttpStatus.OK);
    }
}
