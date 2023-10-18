package com.ingesoft.cyclenet.logic;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ingesoft.cyclenet.dataAccess.RepositorioCalificacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioComentario;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Calificacion;
import com.ingesoft.cyclenet.domain.Comentario;
import com.ingesoft.cyclenet.domain.Publicacion;
import com.ingesoft.cyclenet.domain.Usuario;

import jakarta.transaction.Transactional;


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
        repositorioComentario.deleteAll();
        repositorioPublicacion.deleteAll();
        repositorioUsuario.deleteAll();
    }

    @Transactional
    @Test
    public void pruebaSubirCalificacionPublicacionExitosamente(){
        try {

            //Arrange 
            Usuario usuario = new Usuario("camilo","juan","lina123","NOO","si");
            usuario = repositorioUsuario.save(usuario);

            Publicacion publicacion = new Publicacion("Asi es",false,false,new Timestamp(System.currentTimeMillis()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);
            
            //Act
            Long idCalificacionPublicacion = casosDeUsoCalificacion.realizarCalificacionPublicacion("camilo", 3, publicacion.getId());

            //Assert
            //Revisa usuario
            Optional<Usuario> opcionalUsuario = repositorioUsuario.findById("camilo");
            assertFalse(opcionalUsuario.isEmpty(), "El usuario no aparece en la base de datos");

            Usuario usuarioModificado = opcionalUsuario.get();
            assertNotNull(usuarioModificado.getCalificaciones(), "El usuario tiene calificaciones en null");
            assertFalse(usuarioModificado.getCalificaciones().isEmpty(), "El usuario no tiene calificaciones");
            
            //Revisa la publicacion
            Optional<Publicacion> opcionalPublicacion = repositorioPublicacion.findById(publicacion.getId());
            assertFalse(opcionalPublicacion.isEmpty(), "La publicacion no aparece en la base de datos");

            Publicacion publicacionModificada = opcionalPublicacion.get();
            assertNotNull(publicacionModificada.getCalificaciones(), "La publicacion tiene calificaciones en null");
            assertFalse(publicacionModificada.getCalificaciones().isEmpty(), "La publicacion no tiene calificaciones");

            //Revisa la calificacion
            Optional<Calificacion> opcionalCalificacion = repositorioCalificacion.findById(idCalificacionPublicacion);
            assertFalse(opcionalCalificacion.isEmpty(), "La calificacion no aparece en la base de datos");

            Calificacion calificacionRealizada = opcionalCalificacion.get();
            assertNotNull(calificacionRealizada.getUsuario(), "El usuario no aparece en la calificacion");
            assertEquals(
                calificacionRealizada.getUsuario().getNombreUsuario(), 
                usuarioModificado.getNombreUsuario(), 
                "El usuario no es el mismo");

            assertNotNull(calificacionRealizada.getPublicacion(),"La calificacion no esta asociado a una publicacion");
            assertEquals(calificacionRealizada.getPublicacion().getId(), publicacionModificada.getId(), "Publicacion calificada incorrecta");
            
            assertEquals(calificacionRealizada.getNumCalificacion(), 3, "Numero de calificacion incorrecta");

        } catch (Exception e) {
            fail("No se califico la publicacion exitosamente: ", e);
        }
    }

    @Transactional
    @Test
    public void pruebaSubirCalificacionComentarioExitosamente(){
        try {
            //Arrange 
            Usuario usuario = new Usuario("camilo","juan","lina123","NOO","si");
            usuario = repositorioUsuario.save(usuario);
            Publicacion publicacion = new Publicacion("Asi es",false,false,new Timestamp(System.currentTimeMillis()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);
            Comentario comentario = new Comentario(
                "No, asi no es",
                new Timestamp(System.currentTimeMillis()),
                usuario,
                publicacion);
            comentario = repositorioComentario.save(comentario);
            publicacion.getComentarios().add(comentario);
            repositorioPublicacion.save(publicacion);

            //Act
            Long idCalificacionComentario = casosDeUsoCalificacion.realizarCalificacionComentario(
            "camilo", 
            2, 
            comentario.getId());

            //Assert
            //Revisa usuario
            Optional<Usuario> opcionalUsuario = repositorioUsuario.findById("camilo");
            assertFalse(opcionalUsuario.isEmpty(), "El usuario no aparece en la base de datos");

            Usuario usuarioModificado = opcionalUsuario.get();
            assertNotNull(usuarioModificado.getCalificaciones(), "El usuario tiene calificaciones en null");
            assertFalse(usuarioModificado.getCalificaciones().isEmpty(), "El usuario no tiene calificaciones");
            
            //Revisa publicacion
            Optional<Publicacion> opcionalPublicacion = repositorioPublicacion.findById(publicacion.getId());
            assertFalse(opcionalPublicacion.isEmpty(), "La publicacion no aparece en la base de datos");

            Publicacion publicacionModificada = opcionalPublicacion.get();
            assertNotNull(publicacionModificada.getComentarios(), "La publicacion tiene comentarios en null");
            assertFalse(publicacionModificada.getComentarios().isEmpty(), "La publicacion no tiene comentarios");

            //Revisa comentario
            Optional<Comentario> opcionalComentario = repositorioComentario.findById(comentario.getId());
            assertFalse(opcionalComentario.isEmpty(), "El comentario no aparece en la base de datos");

            Comentario comentarioCalificado = opcionalComentario.get();
            assertNotNull(comentarioCalificado.getPublicacion(),"El comentario no esta asociado a una publicacion");
            assertEquals(
                comentarioCalificado.getPublicacion().getId(), 
                publicacionModificada.getId(), 
                "La publicacion es incorrecta");
            assertNotNull(comentarioCalificado.getCalificaciones(), "El comentario tiene calificaciones en null");
            assertFalse(comentarioCalificado.getCalificaciones().isEmpty(), "El comentario no tiene calificaciones");

            //Revisa calificacion
            Optional<Calificacion> opcionalCalificacion = repositorioCalificacion.findById(idCalificacionComentario);
            assertFalse(opcionalCalificacion.isEmpty(), "La calificacion no aparece en la base de datos");

            Calificacion calificacionRealizada = opcionalCalificacion.get();
            assertNotNull(calificacionRealizada.getUsuario(), "El usuario no aparece en la calificacion");
            assertEquals(
                calificacionRealizada.getUsuario().getNombreUsuario(), 
                usuarioModificado.getNombreUsuario(), 
                "El usuario no es el mismo");

            assertNotNull(calificacionRealizada.getComentario(),"La calificacion no esta asociado a un comentario");
            assertEquals(calificacionRealizada.getComentario().getId(), comentarioCalificado.getId(), "Comentario calificado incorrecto");

            assertEquals(calificacionRealizada.getNumCalificacion(), 2, "Numero de calificacion incorrecta");

        } catch (Exception e) {
            fail("No se califico el comentario exitosamente: ", e);
        }
    }

    @Test
    public void pruebaSubirCalifacionPublicacionFueraDeRango(){
        try {
            //Arrange 
            Usuario usuario = new Usuario("camilo","juan","lina123","NOO","si");
            usuario = repositorioUsuario.save(usuario);
            Publicacion publicacion = new Publicacion("Asi es",false,false,new Timestamp(System.currentTimeMillis()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);

            //Act
            casosDeUsoCalificacion.realizarCalificacionPublicacion("camilo", 9, publicacion.getId());

            //Assert
            fail("Se califico la publicacion con un valor erroneo");
        } catch (Exception e) {
            //"OK: No se califico la publicacion con valor erroneo"
        }
        
    }
}
