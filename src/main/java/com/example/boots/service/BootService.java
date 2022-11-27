package com.example.boots.service;

import com.example.boots.entities.Boot;
import com.example.boots.enums.BootType;
import com.example.boots.exceptions.QueryNotSupportedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public interface BootService {
    Iterable<Boot> getAllBoots();
    Boot addNewBoots(Boot boot);
    Boot deleteBoot(Integer id);
    Boot increaseQuantity(Integer id);
    Boot decreaseQuantity(Integer id);
    List<Boot> searchBoots(String material,BootType type,Float size, Integer minQuantity) throws QueryNotSupportedException;
}
