package com.cibertec.monkey.service;

import java.util.List;
import com.cibertec.monkey.entity.Cliente;

public interface ClienteService {
    List<Cliente> listarTodosCliente();
    void guardarCliente(Cliente cliente);
    Cliente buscarById(Integer id);
    void eliminarCliente(Integer id);
}