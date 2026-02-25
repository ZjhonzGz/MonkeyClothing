package com.cibertec.monkey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.monkey.entity.Producto;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Optional<Producto> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
}