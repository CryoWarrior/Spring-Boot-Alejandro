package com.ingesoft.cyclenet.logic;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingesoft.cyclenet.dataAccess.RepositorioComentario;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Comentario;
import com.ingesoft.cyclenet.domain.Publicacion;
import com.ingesoft.cyclenet.domain.Usuario;

import jakarta.transaction.Transactional;

@Service
public class CasosDeUsoComentario {
    
    @Autowired
    RepositorioComentario repositorioComentario;
    @Autowired
    protected RepositorioPublicacion repositorioPublicacion;
    @Autowired
    protected RepositorioUsuario repositorioUsuario;

    @Transactional
    public Long subirComentario(String nombreUsuario, Long idPublicacion, String mensaje) throws ExcepcionUsuarios, ExcepcionPublicacion{

        // Validar usuario
        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(nombreUsuario);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionUsuarios("Este usuario no existe");
        }

        Usuario usuario = optionalUsuario.get();

        //Validar publicacion
        Optional<Publicacion> optionalPublicacion = repositorioPublicacion.findById(idPublicacion);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionPublicacion("Esta publicacion no existe");
        }

        Publicacion publicacion = optionalPublicacion.get();


        //Crear comentario
        
        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        Comentario comentario = new Comentario(mensaje, fecha, usuario, publicacion);

        try {
            repositorioComentario.save(comentario);
        } catch(Exception e) {
            throw new ExcepcionUsuarios("Error: No se pudo guardar el comentario", e);
        }

        try {
            publicacion.getComentarios().add(comentario);
        } catch(Exception e) {
            throw new ExcepcionUsuarios("Error: No se pudo guardar el comentario en la publicacion", e);
        }
        
        try {
            usuario.getComentarios().add(comentario);        
        } catch (Exception e) {
            throw new ExcepcionUsuarios("Error: No se pudo guardar el comentario en el usuario",e);
        }

        publicacion = repositorioPublicacion.save(publicacion);
        usuario = repositorioUsuario.save(usuario);

        //Retornar datos a mostrar
        return comentario.getId();
    }

}
