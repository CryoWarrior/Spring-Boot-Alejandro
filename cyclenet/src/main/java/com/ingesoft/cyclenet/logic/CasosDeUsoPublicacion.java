package com.ingesoft.cyclenet.logic;

import java.time.LocalDate;
import java.util.Optional;
import java.sql.Date;


import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Calificacion;
import com.ingesoft.cyclenet.domain.Comentario;
import com.ingesoft.cyclenet.domain.Publicacion;
import com.ingesoft.cyclenet.domain.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CasosDeUsoPublicacion {

    @Autowired
    protected RepositorioPublicacion repositorioPublicacion;

    @Autowired
    protected RepositorioUsuario repositorioUsuario;

    public Long subirPublicacion(String nombreUsuario, String mensaje, Boolean foto, Boolean lugar) throws ExcepcionUsuarios{

        // Validar usuario
        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(nombreUsuario);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionUsuarios("Este usuario no existe");
        }

        Usuario usuario = optionalUsuario.get();

        //Guardar publicación
        Date fecha = Date.valueOf(LocalDate.now());

        Publicacion publicacion = new Publicacion(mensaje, foto, lugar, fecha, usuario);

        try {
            publicacion = repositorioPublicacion.save(publicacion);
        } catch(Exception e) {
            throw new ExcepcionUsuarios("Error: No se pudo guardar la publicacion", e);
        }
        
        try {
            usuario.getPublicaciones().add(publicacion);        
        } catch (Exception e) {
            throw new ExcepcionUsuarios("Error: No se pudo guardar el usuario",e);
        }

        publicacion = repositorioPublicacion.save(publicacion);
        usuario = repositorioUsuario.save(usuario);

        //Retornar datos a mostrar
         return publicacion.getId();
    }

    public void mostrarPublicacionLugar(Publicacion publicacion){
        //Retornar datos a mostrar
        return;
    }
    
    public void mostrarPublicacion(Publicacion p) {
        System.out.println("Mensaje: " + p.getMensaje());
        System.out.println(" Foto: " + p.getFoto());
        System.out.println(" Lugar: " + p.getLugar());
        System.out.println(" Fecha: " + p.getFecha());
        System.out.println(" Usuario: " + p.getUsuario().getNombre());
        System.out.println(" Comentarios: ");
        for (Comentario c : p.getComentarios()) {
            System.out.println("  Mensaje: " + c.getMensaje());
            System.out.println("  Fecha: " + c.getFecha());
        }
        System.out.println(" Calificaciones: ");
        for (Calificacion c : p.getCalificaciones()) {
            System.out.println("  Numero: " + c.getNumCalificacion());
        }
    }

    public void mostrarPublicaciones(String nombreUsuario)throws ExcepcionPublicacion, ExcepcionUsuarios{
        // Validar usuario
        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(nombreUsuario);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionUsuarios("Este usuario no existe");
        }

        Usuario usuario = optionalUsuario.get();

        if(usuario.getPublicaciones().isEmpty()){
            throw new ExcepcionPublicacion("Este usuario no ha hecho ninguna publicacion.");
        }

        System.out.println("Publicaiones de " + usuario.getNombre() + ":");
        
        for (Publicacion p : usuario.getPublicaciones()) {
            // Llama a la funcion de imprimir una publicacion de la clase casosdeusopublicacion
            mostrarPublicacion(p);
        }
    }
}
