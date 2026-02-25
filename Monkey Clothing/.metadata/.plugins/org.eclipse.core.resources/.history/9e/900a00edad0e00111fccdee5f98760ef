package com.cibertec.monkey.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asesor_vta")
public class AsesorVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codasesor")
    private Integer id;

    @Column(nullable = false, length = 80)
    private String nombres;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String telefono;
}