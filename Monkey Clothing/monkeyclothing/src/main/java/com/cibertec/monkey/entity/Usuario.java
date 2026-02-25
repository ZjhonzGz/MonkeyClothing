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
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 50)
    private String usuario;

    @Column(nullable = false, length = 100)
    private String clave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idrol", nullable = false)
    private Rol rol;

    @Column(columnDefinition = "TINYINT")
    private Integer estado = 1;
}