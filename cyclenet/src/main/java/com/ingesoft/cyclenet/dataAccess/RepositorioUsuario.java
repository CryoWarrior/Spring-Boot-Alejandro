package com.ingesoft.cyclenet.dataAccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ingesoft.cyclenet.domain.Usuario;
import java.util.List;


@Repository
public interface RepositorioUsuario 
extends JpaRepository<Usuario, String>
{
    //Creo que no sirve Usuario getUsuarioLogeado();
    
    List<Usuario> findByNombreUsuario(String nombreUsuario);
}
