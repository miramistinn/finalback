package com.example.finall.controllers;

import com.example.finall.dto.MainInformationDTO;
import com.example.finall.dto.RejectedDTO;
import com.example.finall.entity.MainInformation;
import com.example.finall.services.MainInformationService;
import com.example.finall.services.RejectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@AllArgsConstructor
public class AplicationController {
    private  final MainInformationService mainInformationService;
    private final RejectService rejectService;
    @PostMapping("create/application")
    public ResponseEntity<MainInformation> create(@RequestBody MainInformationDTO dto){
        System.out.println("пришло на сервер");
        return new ResponseEntity<>(mainInformationService.createApl(dto), HttpStatus.OK);
    }

    @GetMapping("/application/readAll")
    public ResponseEntity<List<MainInformation>> readAll(){
        return new ResponseEntity<>(mainInformationService.readAllnew(), HttpStatus.OK);
    }

    @PostMapping("application/readByID/{id}")
    public ResponseEntity<MainInformation> readById(@PathVariable Long id){
        System.out.println(id);
        return new ResponseEntity<>(mainInformationService.readById(id), HttpStatus.OK);
    }

    @PostMapping("application/reject")
    public HttpStatus rejectId(@RequestBody RejectedDTO dto){
        rejectService.reject(dto);
        System.out.println("Был отменен");
        return HttpStatus.OK;
    }
}
