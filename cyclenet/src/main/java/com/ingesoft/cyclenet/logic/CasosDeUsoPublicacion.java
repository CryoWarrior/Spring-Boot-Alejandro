package com.ingesoft.cyclenet.logic;

import java.time.LocalDate;
import java.util.List;
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

    public void subirPublicacion(Usuario usuario, String mensaje, Boolean foto, Boolean lugar) throws ExcepcionUsuarios {
        //Guardar publicación
        Date fecha = Date.valueOf(LocalDate.now());
        Publicacion publicacion = new Publicacion(mensaje, foto, lugar, fecha, usuario);
        
        //Validar usuario
        List<Usuario> listasUsuarios = repositorioUsuario.findByNombreUsuario(usuario.getNombreUsuario());
        if(listasUsuarios.size() == 0){
            throw new ExcepcionUsuarios("Este usuario no existe");
        }
        usuario.getPublicaciones().add(publicacion);
        repositorioPublicacion.save(publicacion);
        
        if(lugar == true){
            mostrarPublicacionLugar(publicacion);
            return;
        }

        //Retornar datos a mostrar
         return;
    }

    public void mostrarPublicacionLugar(Publicacion publicacion){
        //Retornar datos a mostrar
        return;
    }
    
}
