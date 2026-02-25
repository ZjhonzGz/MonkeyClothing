package com.cibertec.monkey.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @Column(name = "idcate")
    private String idcate;

    @Column(name = "descripcion")
    private String descripcion;
}