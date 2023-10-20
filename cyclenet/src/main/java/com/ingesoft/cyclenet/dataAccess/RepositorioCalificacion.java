package com.ingesoft.cyclenet.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingesoft.cyclenet.domain.Calificacion;

@Repository
public interface RepositorioCalificacion 
extends JpaRepository<Calificacion, Long>
{
    
}
