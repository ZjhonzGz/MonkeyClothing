package com.cibertec.monkey.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rol")
@Getter
@Setter
@NoArgsConstructor
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idrol;

    @Column(nullable = false, unique = true, length = 100)
    private String descripcion;
}