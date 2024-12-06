package com.example.finall.controllers;

import com.example.finall.dto.AddtiotionalDTO;
import com.example.finall.entity.Additional;
import com.example.finall.entity.AdditionalServiceEntity;
import com.example.finall.entity.BMenu;
import com.example.finall.entity.Dish;
import com.example.finall.services.AdditionalService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@AllArgsConstructor
public class DopController {
    private final AdditionalService additionalService;
    @GetMapping("dop/read")
    public ResponseEntity<List<AdditionalServiceEntity>> readAllDopes() {
        System.out.println("запрос пришел на червер readAllDopes");
        return new ResponseEntity<>(additionalService.readAll(), HttpStatus.OK);
    }
    @PostMapping("dop/add")
    public HttpStatus addDop(@RequestBody AddtiotionalDTO dto) {
        System.out.println("запрос пришел на червер addDop");
        System.out.println(dto.getAppId());
        additionalService.add(dto);
        return  HttpStatus.OK;
    }
    @DeleteMapping("dop/delete")
    public HttpStatus addDelete(@RequestBody AddtiotionalDTO dto) {
        System.out.println("запрос пришел на червер addDelete");
        additionalService.delete(dto);
        return  HttpStatus.OK;
    }
    @DeleteMapping("dop/delete/{addid}")
    public HttpStatus addDelet(@PathVariable Long addid) {
        System.out.println("запрос пришел на червер addDelete");
        additionalService.delete(addid);
        return  HttpStatus.OK;
    }
    @GetMapping("dop/read/{mainId}")
    public ResponseEntity<List<Additional>> readAppDopes(@PathVariable Long mainId) {
        System.out.println("пришло на сервер readAppDopes");
        return new ResponseEntity<>(additionalService.findByMenuId(mainId), HttpStatus.OK);
    }
}
