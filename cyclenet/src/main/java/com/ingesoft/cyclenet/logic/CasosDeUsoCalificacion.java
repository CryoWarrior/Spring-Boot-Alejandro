package com.ingesoft.cyclenet.logic;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.cyclenet.dataAccess.RepositorioCalificacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioComentario;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Calificacion;
import com.ingesoft.cyclenet.domain.Comentario;
import com.ingesoft.cyclenet.domain.Publicacion;
import com.ingesoft.cyclenet.domain.Usuario;

import lombok.Synchronized;

@Service
public class CasosDeUsoCalificacion {
    @Autowired
    protected RepositorioCalificacion repositorioCalificacion;
    @Autowired
    protected RepositorioUsuario repositorioUsuario;
    @Autowired
    protected RepositorioComentario repositorioComentario;
    @Autowired
    protected RepositorioPublicacion repositorioPublicacion;

    @Transactional
    public Long realizarCalificacionPublicacion(String nombreUsuario, int valorCalificado, Long idPublicacion) throws Exception{
        if(valorCalificado < 1 || valorCalificado > 5){
            throw new Exception("La calificacion debe estar entre 1 y 5");
        }

        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(nombreUsuario);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionUsuarios("Este usuario no existe");
        }
        Usuario usuario = optionalUsuario.get();

        Optional<Publicacion> optionalPublicacion = repositorioPublicacion.findById(idPublicacion);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionPublicacion("Esta publicacion no existe");
        }
        Publicacion publicacion = optionalPublicacion.get();

        Calificacion calificacion = new Calificacion(valorCalificado, usuario ,publicacion);
        
        usuario.getCalificaciones().add(calificacion);
        publicacion.getCalificaciones().add(calificacion);

        calificacion = repositorioCalificacion.save(calificacion);
        usuario = repositorioUsuario.save(usuario);
        publicacion = repositorioPublicacion.save(publicacion);

        return calificacion.getId();
    }

    @Transactional
    public Long realizarCalificacionComentario(String nombreUsuario, int valorCalificado, Long idComentario) throws Exception{
        if(valorCalificado < 1 || valorCalificado > 5){
            throw new ExcepcionCalificacion("La calificacion debe estar entre 1 y 5");
        }

        // Validar usuario
        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(nombreUsuario);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionUsuarios("Este usuario no existe");
        }

        Usuario usuario = optionalUsuario.get();


        Optional<Comentario> optionalComentario = repositorioComentario.findById(idComentario);
        if(optionalComentario.isEmpty()){
            throw new ExcepcionComentario("Este Comentario no existe");
        }
        Comentario comentario = optionalComentario.get();

        //Crea la calificacion
        Calificacion calificacion = new Calificacion(valorCalificado, usuario ,comentario);

        try {
            usuario.getCalificaciones().add(calificacion);
        } catch (Exception e) {
            throw new ExcepcionUsuarios("No se pudo agregar la calificacion al usuario: ", e);
        }

        try {
            comentario.getCalificaciones().add(calificacion);    
        } catch (Exception e) {
            throw new ExcepcionComentario("No se pudo agregar la calificacion al comentario: ", e);
        }

        try {
            calificacion = repositorioCalificacion.save(calificacion);
        } catch(Exception e) {
            throw new ExcepcionUsuarios("Error: No se pudo guardar la calificacion", e);
        }

        usuario = repositorioUsuario.save(usuario);
        comentario = repositorioComentario.save(comentario);

        //Retorna el Id de la calificacion
        return calificacion.getId();
    }
}
