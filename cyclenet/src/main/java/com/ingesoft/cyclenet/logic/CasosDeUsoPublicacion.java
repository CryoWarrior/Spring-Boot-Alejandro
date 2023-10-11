package com.ingesoft.cyclenet.logic;

import java.time.LocalDate;
import java.sql.Date;


import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
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

    public Long subirPublicacion(Usuario usuario, String mensaje, Boolean foto, Boolean lugar){

        //Guardar publicación
        Date fecha = Date.valueOf(LocalDate.now());

        Publicacion publicacion = new Publicacion(mensaje, foto, lugar, fecha, usuario);
        usuario.getPublicaciones().add(publicacion);

        publicacion = repositorioPublicacion.save(publicacion);
        usuario = repositorioUsuario.save(usuario);

        //Retornar datos a mostrar
         return publicacion.getId();
    }
/* 
    public void subirPublicacion(String nombreUsuario, String mensaje, Boolean foto, Boolean lugar) throws ExcepcionUsuarios {

        // Validar usuario
        Optional<Usuario> optionalUsuario = repositorioUsuario.findById(nombreUsuario);
        if(optionalUsuario.isEmpty()){
            throw new ExcepcionUsuarios("Este usuario no existe");
        }

        Usuario usuario = optionalUsuario.get();

        System.out.println("Usuario = " + usuario);
        System.out.println("Publicaciones = " + usuario.getPublicaciones());

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

        
        if(lugar == true){
            mostrarPublicacionLugar(publicacion);
            return;
        }
        

        //Retornar datos a mostrar
         return;
    }
    */
    public void mostrarPublicacionLugar(Publicacion publicacion){
        //Retornar datos a mostrar
        return;
    }
    
}
