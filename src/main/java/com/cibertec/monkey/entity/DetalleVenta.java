package com.cibertec.monkey.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detalle_ventas")
public class DetalleVenta implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DetalleVentaId id;

    @Column(name = "cantidad_vta", nullable = false)
    private int cantidadVta;

    @Column(name = "precio_vta", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioVta = BigDecimal.ZERO;

    @Column(name = "importe_vta", nullable = false, precision = 10, scale = 2)
    private BigDecimal importeVta = BigDecimal.ZERO;

    @Column(name = "precio_compra", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioCompra = BigDecimal.ZERO;
}