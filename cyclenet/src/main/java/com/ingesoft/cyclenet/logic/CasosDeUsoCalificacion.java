package com.ingesoft.cyclenet.logic;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.cyclenet.dataAccess.RepositorioCalificacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Calificacion;
import com.ingesoft.cyclenet.domain.Comentario;
import com.ingesoft.cyclenet.domain.Publicacion;
import com.ingesoft.cyclenet.domain.Usuario;

@Service
public class CasosDeUsoCalificacion {
    @Autowired
    protected RepositorioCalificacion repositorioCalificacion;
    @Autowired
    protected RepositorioUsuario repositorioUsuario;

    @Autowired
    protected RepositorioPublicacion repositorioPublicacion;

    public void realizarCalificacionPublicacion(Usuario usuario, int valorCalificado, Publicacion publicacion) throws Exception{
        if(valorCalificado < 1 || valorCalificado > 5){
            throw new Exception("La calificacion debe estar entre 1 y 5");
        }

        Calificacion calificacion = new Calificacion(valorCalificado, usuario ,publicacion);
        
        usuario.getCalificaciones().add(calificacion);
        publicacion.getCalificaciones().add(calificacion);

        repositorioCalificacion.save(calificacion);
        repositorioUsuario.save(usuario);
        repositorioPublicacion.save(publicacion);
        return;
    }

    @Transactional
    public void realizarCalificacionComentario(String nombreUsuario, int valorCalificado, Comentario comentario) throws Exception{
        if(valorCalificado < 1 || valorCalificado > 5){
            throw new ExcepcionCalificacion("La calificacion debe estar entre 1 y 5");
        }

        // Validar usuario
        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(nombreUsuario);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionUsuarios("Este usuario no existe");
        }

        Usuario usuario = optionalUsuario.get();

        System.out.println("LLEGA 0");
        //Crea la calificacion
        Calificacion calificacion = new Calificacion(valorCalificado, usuario ,comentario);
        System.out.println("LLEGA 1");

        try {
            usuario.getCalificaciones().add(calificacion);
        } catch (Exception e) {
            throw new ExcepcionUsuarios("No se pudo agregar la calificacion al usuario: ", e);
        }
        System.out.println("LLEGA 2");
        try {
            comentario.getCalificaciones().add(calificacion);    
        } catch (Exception e) {
            throw new ExcepcionComentario("No se pudo agregar la calificacion al comentario: ", e);
        }
        System.out.println("LLEGA 3");

        try {
            repositorioCalificacion.save(calificacion);
        } catch(Exception e) {
            throw new ExcepcionUsuarios("Error: No se pudo guardar la calificacion", e);
        }
        return;
    }
}
