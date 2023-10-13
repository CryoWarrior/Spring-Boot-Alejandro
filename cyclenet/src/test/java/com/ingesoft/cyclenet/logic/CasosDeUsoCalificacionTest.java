package com.ingesoft.cyclenet.logic;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ingesoft.cyclenet.dataAccess.RepositorioCalificacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioComentario;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Comentario;
import com.ingesoft.cyclenet.domain.Publicacion;
import com.ingesoft.cyclenet.domain.Usuario;


@SpringBootTest
public class CasosDeUsoCalificacionTest {
    
    @Autowired
    protected CasosDeUsoCalificacion casosDeUsoCalificacion;

    @Autowired
    protected RepositorioCalificacion repositorioCalificacion;
    @Autowired
    protected RepositorioPublicacion repositorioPublicacion;
    @Autowired
    protected RepositorioUsuario repositorioUsuario;
    @Autowired
    protected RepositorioComentario repositorioComentario;

    @BeforeEach
    public void prepararAmbienteDePruebas(){
        repositorioCalificacion.deleteAll();
        repositorioPublicacion.deleteAll();
        repositorioComentario.deleteAll();
        repositorioUsuario.deleteAll();
    }

    @Test
    public void pruebaSubirCalificacionPublicacionExitosamente(){
        try {

            //Arrange 
            Usuario usuario = new Usuario("camilo","juan","lina123","NOO","si");
            usuario = repositorioUsuario.save(usuario);

            Publicacion publicacion = new Publicacion("Asi es",false,false,Date.valueOf(LocalDate.now()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);
            
            //Act
            casosDeUsoCalificacion.realizarCalificacionPublicacion(usuario, 3, publicacion);

            //Assert
            //fail("OK: Se logro calificar la publicacion correctamente");
        } catch (Exception e) {
            fail("No se califico la publicacion exitosamente");
        }
    }

    @Test
    public void pruebaSubirCalificacionComentarioExitosamente(){
        try {
            //Arrange 
            Usuario usuario = new Usuario("camilo","juan","lina123","NOO","si");
            usuario = repositorioUsuario.save(usuario);
            Publicacion publicacion = new Publicacion("Asi es",false,false,Date.valueOf(LocalDate.now()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);
            Comentario comentario = new Comentario(
                "No, asi no es",
                Date.valueOf(LocalDate.now()),
                usuario,
                publicacion);
            comentario = repositorioComentario.save(comentario);

            //Act
            casosDeUsoCalificacion.realizarCalificacionComentario("camilo", 2, comentario);

            //Assert
            //fail("OK: Se logro calificar el comentario correctamente");
        } catch (Exception e) {
            fail("No se califico el comentario exitosamente: ", e);
        }
    }

    @Test
    public void pruebaSubirCalifacionFueraDeRango(){
        try {
            System.out.println("SIGUIENTE");
            //Arrange 
            Usuario usuario = new Usuario("camilo","juan","lina123","NOO","si");
            usuario = repositorioUsuario.save(usuario);
            Publicacion publicacion = new Publicacion("Asi es",false,false,Date.valueOf(LocalDate.now()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);

            //Act
            casosDeUsoCalificacion.realizarCalificacionPublicacion(usuario, 9, publicacion);

            //Assert
            fail("Se califico la publicacion con un valor erroneo");
        } catch (Exception e) {
            //fail("OK: No se califico la publicacion con valor erroneo");
        }
        
    }
}
