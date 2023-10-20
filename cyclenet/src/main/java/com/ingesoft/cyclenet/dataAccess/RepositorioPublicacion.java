package com.ingesoft.cyclenet.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingesoft.cyclenet.domain.Publicacion;

@Repository
public interface RepositorioPublicacion 
extends JpaRepository<Publicacion, Long>
{
    
}
