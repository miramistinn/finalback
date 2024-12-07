package com.example.finall.controllers;

import com.example.finall.dto.MainInformationDTO;
import com.example.finall.dto.RejectedDTO;
import com.example.finall.entity.MainInformation;
import com.example.finall.entity.Rejected;
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
    @GetMapping("/application/proccesing")
    public ResponseEntity<List<MainInformation>> readProccesing(){
        return new ResponseEntity<>(mainInformationService.readAllProc(), HttpStatus.OK);
    }
    @GetMapping("/application/closed")
    public ResponseEntity<List<MainInformation>> readClose(){
        return new ResponseEntity<>(mainInformationService.readAllClosed(), HttpStatus.OK);
    }

    @GetMapping("/application/rejected")
    public ResponseEntity<List<Rejected>> readRej(){
        return new ResponseEntity<>(rejectService.readAllRejected(), HttpStatus.OK);
    }
    @PostMapping("application/readByID/{id}")
    public ResponseEntity<MainInformation> readById(@PathVariable Long id){
        System.out.println(id);
        return new ResponseEntity<>(mainInformationService.readById(id), HttpStatus.OK);
    }
    @PostMapping("application/close/{id}")
    public HttpStatus close(@PathVariable Long id){
        mainInformationService.close(id);
        System.out.println("close the project");
        return HttpStatus.OK;
    }

    @PostMapping("application/reject")
    public HttpStatus rejectId(@RequestBody RejectedDTO dto){
        rejectService.reject(dto);
        System.out.println("Был отменен");
        return HttpStatus.OK;
    }
    @PostMapping("application/startProc/{id}")
    public HttpStatus startProc(@PathVariable Long id){
        mainInformationService.startProc(id);
        System.out.println("startProc");
        return HttpStatus.OK;
    }
    @PostMapping("application/update/{id}")
    public HttpStatus update(@PathVariable Long id, @RequestBody MainInformationDTO dto){
        System.out.println(id);
        mainInformationService.update(id, dto);
        return HttpStatus.OK;
    }
    @PostMapping("application/create")
    public HttpStatus update(@RequestBody MainInformationDTO dto){
        mainInformationService.createApl( dto);
        return HttpStatus.OK;
    }
}
