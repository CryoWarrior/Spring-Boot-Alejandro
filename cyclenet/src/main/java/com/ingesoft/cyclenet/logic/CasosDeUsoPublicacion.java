package com.ingesoft.cyclenet.logic;

import java.time.LocalDate;
import java.sql.Date;

import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.domain.Publicacion;
import com.ingesoft.cyclenet.domain.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CasosDeUsoPublicacion {

    @Autowired
    protected RepositorioPublicacion repositorioPublicacion;

    public void subirPublicacion(Usuario usuario, String mensaje, Boolean foto, Boolean lugar){
        //Guardar publicación
        Date fecha = Date.valueOf(LocalDate.now());
        Publicacion publicacion = new Publicacion(mensaje, foto, lugar, fecha, usuario);
        usuario.getPublicaciones().add(publicacion);
        repositorioPublicacion.save(publicacion);

        if(lugar == true){
            mostrarPublicacionLugar(publicacion);
            return;
        }

        //Mostrar publicacion
         //Mostrar texto, e imagen
         return;
    }

    public void mostrarPublicacionLugar(Publicacion publicacion){
        //Procedimiento para obtener y mostrar el lugar
        return;
    }
    
}
