package com.cibertec.monkey.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.cibertec.monkey.entity.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("SELECT c.email FROM Cliente c WHERE c.origen = :origen AND c.email IS NOT NULL")
    List<String> findEmailsByOrigen(@Param("origen") String origen);
}