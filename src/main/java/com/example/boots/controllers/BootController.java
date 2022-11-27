package com.example.boots.controllers;

import com.example.boots.entities.Boot;
import com.example.boots.enums.BootType;
import com.example.boots.exceptions.QueryNotSupportedException;
import com.example.boots.repositories.BootRepository;
import com.example.boots.service.BootService;
import com.example.boots.service.BootServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/boots")
@RequiredArgsConstructor
public class BootController {
    private BootServiceImpl bootService;


    @GetMapping("/")
    public Iterable<Boot> getAllBoots(){
        return bootService.getAllBoots();
    }

    @PostMapping("/")
    public Boot addNewBoots(@RequestBody Boot boot){
        return bootService.addNewBoots(boot);
    }

    @DeleteMapping("/{id}")
    public Boot deleteBoot(@PathVariable Integer id){
        return bootService.deleteBoot(id);
    }

    @PutMapping("/{id}/quantity/increment")
    public Boot increaseQuantity(@PathVariable Integer id){
        return bootService.increaseQuantity(id);
    }

    @PutMapping("/{id}/quantity/decrement")
    public Boot decreaseQuantity(@PathVariable Integer id){
        return bootService.decreaseQuantity(id);
    }

    @GetMapping("/search")
    public List<Boot> searchBoots(@RequestParam(required = false) String material,
                                  @RequestParam(required = false) BootType type, @RequestParam(required = false) Float size,
                                  @RequestParam(required = false, name = "quantity") Integer minQuantity) throws QueryNotSupportedException {
        return bootService.searchBoots(material,type,size,minQuantity);
    }







}
