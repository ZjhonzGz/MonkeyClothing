package com.cibertec.monkey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cibertec.monkey.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}