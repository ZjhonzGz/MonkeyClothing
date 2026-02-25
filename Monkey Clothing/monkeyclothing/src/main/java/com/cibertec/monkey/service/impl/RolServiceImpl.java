package com.cibertec.monkey.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cibertec.monkey.entity.Rol;
import com.cibertec.monkey.repository.RolRepository;
import com.cibertec.monkey.service.RolService;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository repo;

    @Override
    public List<Rol> listarTodosRol() {
        return repo.findAll();
    }

    @Override
    public Rol buscarById(Integer id) {
        return repo.findById(id).orElse(null);
    }
}