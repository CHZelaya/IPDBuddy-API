package oosd.sait.ipdbuddyapi.controllers;

import oosd.sait.ipdbuddyapi.dto.ContractorDTO;
import oosd.sait.ipdbuddyapi.services.ContractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contractor")
public class ContractorController {

    @Autowired
    private ContractorService contractorService;

//    @GetMapping("/profile/{id}")
//    public ResponseEntity<?> getProfile(@PathVariable int id) {
//        try {
//            ContractorDTO contractorDTO = contractorService
//
//        } catch (RuntimeException e) {
//
//        }
//
//        return ResponseEntity.ok().build();
//    };








}//! Class
