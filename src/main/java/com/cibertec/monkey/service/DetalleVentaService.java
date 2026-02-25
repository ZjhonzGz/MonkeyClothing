package com.cibertec.monkey.service;

import java.util.List;
import com.cibertec.monkey.entity.DetalleVenta;

public interface DetalleVentaService {
    DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta);
    List<DetalleVenta> listarTodosDetalleVenta();
    List<DetalleVenta> buscarDetalleVentaByNroVenta(Long nroVenta);
    void eliminarDetalleVenta(DetalleVenta d);
}