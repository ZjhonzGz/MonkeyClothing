package com.cibertec.monkey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.monkey.entity.AsesorVenta;

@Repository
public interface AsesorVentaRepository extends JpaRepository<AsesorVenta, Integer> {
}