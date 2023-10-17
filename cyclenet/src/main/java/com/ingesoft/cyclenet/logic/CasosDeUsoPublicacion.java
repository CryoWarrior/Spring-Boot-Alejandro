package com.ingesoft.cyclenet.logic;

import java.util.List;
import java.util.Optional;
import java.sql.Timestamp;

import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Calificacion;
import com.ingesoft.cyclenet.domain.Comentario;
import com.ingesoft.cyclenet.domain.Publicacion;
import com.ingesoft.cyclenet.domain.Usuario;

import jakarta.transaction.Transactional;

//import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CasosDeUsoPublicacion {

    @Autowired
    protected RepositorioPublicacion repositorioPublicacion;

    @Autowired
    protected RepositorioUsuario repositorioUsuario;

    @Transactional
    public Long subirPublicacion(String nombreUsuario, String mensaje, Boolean foto, Boolean lugar) throws ExcepcionUsuarios{

        // Validar usuario
        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(nombreUsuario);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionUsuarios("Este usuario no existe");
        }

        Usuario usuario = optionalUsuario.get();

        //Guardar publicación
        Timestamp fecha = new Timestamp(System.currentTimeMillis());

        Publicacion publicacion = new Publicacion(mensaje, foto, lugar, fecha, usuario);

        try {
            publicacion = repositorioPublicacion.save(publicacion);
        } catch(Exception e) {
            throw new ExcepcionUsuarios("Error: No se pudo guardar la publicacion", e);
        }
        
        try {
            usuario.getPublicaciones().add(publicacion);        
        } catch (Exception e) {
            throw new ExcepcionUsuarios("Error: No se pudo guardar la publicacion en el usuario",e);
        }

        usuario = repositorioUsuario.save(usuario);

        //Retornar datos a mostrar
         return publicacion.getId();
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

    @Transactional
    public List<Publicacion> mostrarPublicaciones(String nombreUsuario)throws ExcepcionPublicacion, ExcepcionUsuarios{
        // Validar usuario
        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(nombreUsuario);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionUsuarios("Este usuario no existe");
        }

        Usuario usuario = optionalUsuario.get();

        //Valida que el usuario tenga publicaciones
        if(usuario.getPublicaciones().isEmpty()){
            throw new ExcepcionPublicacion("Este usuario no ha hecho ninguna publicacion.");
        }

        //Imprime informacion de las publicaciones
        System.out.println("Publicaciones de " + usuario.getNombre() + ":");
        
        for (Publicacion p : usuario.getPublicaciones()) {
            // Llama a la funcion de imprimir una publicacion de la clase casosdeusopublicacion
            mostrarPublicacion(p);
        }

        //Devuelve una lista con las publicaciones del usuario
        return usuario.getPublicaciones();
    }
}
