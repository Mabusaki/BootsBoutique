package com.example.boots.service;

import com.example.boots.entities.Boot;
import com.example.boots.enums.BootType;
import com.example.boots.exceptions.QueryNotSupportedException;
import com.example.boots.repositories.BootRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BootServiceImpl implements  BootService{
    private final BootRepository bootRepository;

    @Override
    public Iterable<Boot> getAllBoots() {
        return bootRepository.findAll();
    }

    @Override
    public Boot addNewBoots(Boot boot) {
        return bootRepository.save(boot);
    }

    @Override
    public Boot deleteBoot(Integer id) {
        Optional<Boot> optionalBoot = bootRepository.findById(id);
        if(!optionalBoot.isPresent()) return null;

        Boot bootToDelete = optionalBoot.get();
        bootRepository.delete(bootToDelete);
        return bootToDelete;
    }

    @Override
    public Boot increaseQuantity(Integer id) {
        Optional<Boot> optionalBoot = bootRepository.findById(id);

        if(!optionalBoot.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Boot bootToIncrease = optionalBoot.get();
        bootToIncrease.setQuantity(bootToIncrease.getQuantity()+1);
        bootRepository.save(bootToIncrease);

        return bootToIncrease;
    }

    @Override
    public Boot decreaseQuantity(Integer id) {
        Optional<Boot> optionalBoot = bootRepository.findById(id);

        if(!optionalBoot.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Boot bootToDecrease = optionalBoot.get();
        bootToDecrease.setQuantity(bootToDecrease.getQuantity()-1);
        bootRepository.save(bootToDecrease);

        return bootToDecrease;
    }

    @Override
    public List<Boot> searchBoots(String material, BootType type, Float size, Integer minQuantity) throws QueryNotSupportedException {
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
