package com.example.boots.entities;


import com.example.boots.enums.BootType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "BOOTS")
@Data
public class Boot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private BootType type;
    @Column(name = "SIZE")
    private Float size;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "MATERIAL")
    private String material;
}
