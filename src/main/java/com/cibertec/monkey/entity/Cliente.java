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
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idclie")
    private Integer id;

    @Column(name = "razon_soc", nullable = false, length = 100)
    private String razonSocial;         

    @Column(name = "nombre_ciudad", length = 100)
    private String ciudad;

    @Column(name = "direccion_clie", length = 150)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(length = 150)
    private String email;           

    @Column(length = 20)
    private String origen;             
}