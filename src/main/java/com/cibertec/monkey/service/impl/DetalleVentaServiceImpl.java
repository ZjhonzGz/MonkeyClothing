package com.cibertec.monkey.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cibertec.monkey.entity.DetalleVenta;
import com.cibertec.monkey.repository.DetalleVentaRepository;
import com.cibertec.monkey.service.DetalleVentaService;

@Service
public class DetalleVentaServiceImpl implements DetalleVentaService {

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Override
    public DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    @Override
    public List<DetalleVenta> listarTodosDetalleVenta() {
        return detalleVentaRepository.findAll();
    }

    @Override
    public List<DetalleVenta> buscarDetalleVentaByNroVenta(Long nroVenta) {
        return detalleVentaRepository.buscarByNroVenta(nroVenta);
    }
    
    @Override
    public void eliminarDetalleVenta(DetalleVenta d) {
        detalleVentaRepository.delete(d);
    }
}