package com.cibertec.monkey.service;

import java.util.List;
import com.cibertec.monkey.entity.Usuario;

public interface UsuarioService {
    Usuario guardarUsuario(Usuario usuario);
    List<Usuario> listarTodosUsuario();
    boolean login(Usuario usuario);
    Usuario buscarByUsuario(String username);
    Usuario buscarById(Long id);
    void eliminarUsuario(Long id);
}