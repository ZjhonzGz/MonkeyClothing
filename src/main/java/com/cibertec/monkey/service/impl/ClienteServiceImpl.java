package com.cibertec.monkey.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cibertec.monkey.entity.Cliente;
import com.cibertec.monkey.repository.ClienteRepository;
import com.cibertec.monkey.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Override
    public List<Cliente> listarTodosCliente() {
        return repo.findAll();
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        repo.save(cliente);
    }

    @Override
    public Cliente buscarById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void eliminarCliente(Integer id) {
        repo.deleteById(id);
    }
}