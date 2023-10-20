package com.ingesoft.cyclenet.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingesoft.cyclenet.domain.Comentario;

@Repository
public interface RepositorioComentario 
extends JpaRepository<Comentario, Long>
{
    
}
