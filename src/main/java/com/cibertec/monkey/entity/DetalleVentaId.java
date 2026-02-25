package com.cibertec.monkey.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DetalleVentaId implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "nroventa")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "idproducto")
    private Producto producto;

    public DetalleVentaId() {
    }

    public DetalleVentaId(Venta venta, Producto producto) {
        this.venta = venta;
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleVentaId that)) return false;
        return Objects.equals(venta, that.venta) &&
               Objects.equals(producto, that.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                venta != null ? venta.getId() : null,
                producto != null ? producto.getIdproducto() : null
        );
    }
}