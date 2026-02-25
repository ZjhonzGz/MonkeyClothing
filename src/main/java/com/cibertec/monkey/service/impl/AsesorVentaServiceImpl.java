package com.cibertec.monkey.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cibertec.monkey.entity.AsesorVenta;
import com.cibertec.monkey.repository.AsesorVentaRepository;
import com.cibertec.monkey.service.AsesorVentaService;

@Service
public class AsesorVentaServiceImpl implements AsesorVentaService {

    @Autowired
    private AsesorVentaRepository repo;

    @Override
    public List<AsesorVenta> listarTodos() {
        return repo.findAll();
    }

    @Override
    public AsesorVenta guardar(AsesorVenta asesor) {
        return repo.save(asesor);
    }

    @Override
    public AsesorVenta buscarById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}