package com.cibertec.monkey.service;

import java.util.List;
import com.cibertec.monkey.entity.AsesorVenta;

public interface AsesorVentaService {
    List<AsesorVenta> listarTodos();
    AsesorVenta guardar(AsesorVenta asesor);
    AsesorVenta buscarById(Integer id);
    void eliminar(Integer id);
}