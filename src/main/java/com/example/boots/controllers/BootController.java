package com.example.boots.controllers;

import com.example.boots.entities.Boot;
import com.example.boots.enums.BootType;
import com.example.boots.exceptions.QueryNotSupportedException;
import com.example.boots.repositories.BootRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/boots")
public class BootController {
    private BootRepository bootRepository;

    public BootController(BootRepository bootRepository) {
        this.bootRepository = bootRepository;
    }

    @GetMapping("/")
    public Iterable<Boot> getAllBoots(){
        return bootRepository.findAll();
    }

    @PostMapping("/")
    public Boot addNewBoots(@RequestBody Boot boot){
        return bootRepository.save(boot);
    }

    @DeleteMapping("/{id}")
    public Boot deleteBoot(@PathVariable Integer id){
        Optional<Boot> optionalBoot = bootRepository.findById(id);

        if(!optionalBoot.isPresent()) return null;

        Boot bootToDelete = optionalBoot.get();
        bootRepository.delete(bootToDelete);

        return bootToDelete;
    }

    @PutMapping("/{id}/quantity/increment")
    public Boot increaseQuantity(@PathVariable Integer id){
        Optional<Boot> optionalBoot = bootRepository.findById(id);

        if(!optionalBoot.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Boot bootToIncrease = optionalBoot.get();
        bootToIncrease.setQuantity(bootToIncrease.getQuantity()+1);
        bootRepository.save(bootToIncrease);

        return bootToIncrease;
    }

    @PutMapping("/{id}/quantity/decrement")
    public Boot decreaseQuantity(@PathVariable Integer id){
        Optional<Boot> optionalBoot = bootRepository.findById(id);

        if(!optionalBoot.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Boot bootToDecrease = optionalBoot.get();
        bootToDecrease.setQuantity(bootToDecrease.getQuantity()-1);
        bootRepository.save(bootToDecrease);
        return bootToDecrease;
    }

    @GetMapping("/search")
    public List<Boot> searchBoots(@RequestParam(required = false) String material,
                                  @RequestParam(required = false) BootType type, @RequestParam(required = false) Float size,
                                  @RequestParam(required = false, name = "quantity") Integer minQuantity) throws QueryNotSupportedException {
        if (Objects.nonNull(material)) {
            if (Objects.nonNull(type) && Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a material, type, size, and minimum
                // quantity
                return bootRepository.findByMaterialAndTypeAndSizeAndQuantityGreaterThan(material,type,size,minQuantity);
            } else if (Objects.nonNull(type) && Objects.nonNull(size)) {
                // call the repository method that accepts a material, size, and type
                return bootRepository.findByMaterialAndSizeAndType(material,size,type);
            } else if (Objects.nonNull(type) && Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a material, a type, and a minimum
                // quantity
                return bootRepository.findByMaterialAndTypeAndQuantityGreaterThan(material,type,minQuantity);
            } else if (Objects.nonNull(type)) {
                // call the repository method that accepts a material and a type
                return bootRepository.findByMaterialAndType(material,type);
            } else {
                // call the repository method that accepts only a material
                return bootRepository.findBootByMaterial(material);
            }
        } else if (Objects.nonNull(type)) {
            if (Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a type, size, and minimum quantity
                return bootRepository.findByTypeAndSizeAndQuantityGreaterThan(type,size,minQuantity);
            } else if (Objects.nonNull(size)) {
                // call the repository method that accepts a type and a size
                return bootRepository.findByTypeAndSize(type,size);
            } else if (Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a type and a minimum quantity
                return bootRepository.findByTypeAndQuantityGreaterThan(type, minQuantity);
            } else {
                // call the repository method that accept only a type
                return bootRepository.findBootByType(type);
            }
        } else if (Objects.nonNull(size)) {
            if (Objects.nonNull(minQuantity)) {
                // call the repository method that accepts a size and a minimum quantity
                return bootRepository.findBySizeAndQuantityGreaterThan(size, minQuantity);
            } else {
                // call the repository method that accepts only a size
                return bootRepository.findBootBySize(size);
            }
        } else if (Objects.nonNull(minQuantity)) {
            // call the repository method that accepts only a minimum quantity
            return bootRepository.findByQuantityGreaterThan(minQuantity);
        } else {
            throw new QueryNotSupportedException("This query is not supported! Try a different combination of search parameters.");
        }
    }







}
