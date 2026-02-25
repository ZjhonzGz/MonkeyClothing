package com.cibertec.monkey.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cibertec.monkey.entity.DetalleVenta;
import com.cibertec.monkey.entity.DetalleVentaId;

@Repository
public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, DetalleVentaId> {

    @Query(value = "SELECT * FROM detalle_ventas WHERE nroventa = :nroVenta", nativeQuery = true)
    List<DetalleVenta> buscarByNroVenta(@Param("nroVenta") Long nroVenta);
}