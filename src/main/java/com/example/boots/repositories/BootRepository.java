package com.example.boots.repositories;

import com.example.boots.entities.Boot;
import com.example.boots.enums.BootType;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BootRepository extends CrudRepository<Boot, Integer> {

    List<Boot> findBootBySize(Float size);
    List<Boot> findBootByMaterial(String material);
    List<Boot> findBootByType(BootType type);
    List<Boot> findByQuantityGreaterThan(Integer minQuantity);
    List<Boot> findByMaterialAndTypeAndSizeAndQuantityGreaterThan(String material, BootType type, Float size, Integer minQuantity);
    List<Boot> findByMaterialAndSizeAndType(String material, Float size, BootType type);
    List<Boot> findByMaterialAndTypeAndQuantityGreaterThan(String material, BootType type, Integer minQuantity);
    List<Boot> findByMaterialAndType(String material, BootType type);
    List<Boot> findByTypeAndSizeAndQuantityGreaterThan(BootType type, Float size, Integer minQuantity);
    List<Boot> findByTypeAndSize(BootType type, Float size);
    List<Boot> findByTypeAndQuantityGreaterThan(BootType type, Integer minQuantity);
    List<Boot> findBySizeAndQuantityGreaterThan(Float size, Integer minQuantity);


}
