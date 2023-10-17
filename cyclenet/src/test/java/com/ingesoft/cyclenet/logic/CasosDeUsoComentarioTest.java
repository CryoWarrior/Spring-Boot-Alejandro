package com.ingesoft.cyclenet.logic;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ingesoft.cyclenet.dataAccess.RepositorioCalificacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioComentario; 
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.domain.Comentario;
import com.ingesoft.cyclenet.domain.Usuario;

import jakarta.transaction.Transactional;

import com.ingesoft.cyclenet.domain.Publicacion;

@SpringBootTest
public class CasosDeUsoComentarioTest {
    
    @Autowired
    protected CasosDeUsoComentario casosDeUsoComentario; 
    @Autowired
    protected RepositorioComentario repositorioComentario; 
    @Autowired
    protected RepositorioUsuario repositorioUsuario;
    @Autowired
    protected RepositorioPublicacion repositorioPublicacion;
    @Autowired
    protected RepositorioCalificacion repositorioCalificacion;

    @BeforeEach
    public void prepararAmbienteDePruebas(){
        repositorioCalificacion.deleteAll();
        repositorioComentario.deleteAll();
        repositorioPublicacion.deleteAll();
        repositorioUsuario.deleteAll();
    }

    @Transactional
    @Test
    public void pruebaPublicarComentarioExitosamente(){
        try {
            // Arrange

            Usuario usuario = new Usuario("holaa", "HOLA", "jsdddd", "NOO", "si");
            usuario = repositorioUsuario.save(usuario);
            
            Publicacion publicacion = new Publicacion("Asi es",false,false,new Timestamp(System.currentTimeMillis()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);

            assertNotNull(usuario, "Usuario está en null");
            assertNotNull(publicacion, "La publicación aparece en null");

            // Act
            Long idNuevoComentario = casosDeUsoComentario.subirComentario("holaa", publicacion.getId(),"Este es un comentario" );
            
            // Assert

            //Revisa el usuario
            Optional<Usuario> opcionalUsuario = repositorioUsuario.findById("holaa");
            assertFalse(opcionalUsuario.isEmpty(), "El usuario no aparece en la base de datos");

            Usuario usuarioComentador = opcionalUsuario.get();
            assertNotNull(usuarioComentador.getComentarios(), "El usuario tiene comentarios en null");
            assertFalse(usuarioComentador.getComentarios().isEmpty(), "La publicacion no tiene comentarios");

            //Revisa la publicacion
            Optional<Publicacion> opcionalPublicacion = repositorioPublicacion.findById(publicacion.getId());
            assertFalse(opcionalPublicacion.isEmpty(), "La publicacion no aparece en la base de datos");

            Publicacion publicacionComentada = opcionalPublicacion.get();
            assertNotNull(publicacionComentada.getComentarios(), "La publicacion tiene comentarios en null");
            assertFalse(publicacionComentada.getComentarios().isEmpty(), "La publicacion no tiene comentarios");

            //Revisa comentario
            Optional<Comentario> optionalComentario = repositorioComentario.findById(idNuevoComentario);
            assertFalse(optionalComentario.isEmpty(), "El comentario no aparece en la base de datos");

            Comentario nuevoComentario = optionalComentario.get();
            assertNotNull(nuevoComentario.getUsuario(), "El usuario no aparece en el comentario");
            assertEquals(nuevoComentario.getUsuario().getNombreUsuario(), usuarioComentador.getNombreUsuario(), "El usuario no es el mismo");

            assertNotNull(nuevoComentario.getPublicacion(), "La publicación no aparece en el comentario");
            assertEquals(nuevoComentario.getPublicacion().getId(), publicacionComentada.getId(), "La publicación no es la misma");

            assertEquals(nuevoComentario.getMensaje(), "Este es un comentario", "El mensaje del comentario no coincide con el escrito");

            // OK: Se logró publicar un comentario exitosamente
        } catch (Exception e) {
            fail("No se logró publicar un comentario", e);
        }
    }

    @Test
    public void pruebaPublicarComentarioConUsuarioQueNoExiste(){
        try {
            //Arrange
            Usuario usuario = new Usuario("holaa", "HOLA", "jsdddd", "NOO", "si");
            usuario = repositorioUsuario.save(usuario);
            
            Publicacion publicacion = new Publicacion("Asi es",false,false,new Timestamp(System.currentTimeMillis()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);

            // Act
            casosDeUsoComentario.subirComentario("X", publicacion.getId(),"Este es un comentario" );
            
            // Assert
            fail("Se creo un comentario con un usuario que no existe");
        } catch (Exception e) {
            // OK: No se grabó el comentario con un usuario inexistente
        }
    }

    @Test
    public void pruebaPublicarComentarioConPublicacionQueNoExiste(){
        try {
            //Arrange
            Usuario usuario = new Usuario("holaa", "HOLA", "jsdddd", "NOO", "si");
            usuario = repositorioUsuario.save(usuario);
            
            Publicacion publicacion = new Publicacion("Asi es",false,false,new Timestamp(System.currentTimeMillis()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);

            // Act
            casosDeUsoComentario.subirComentario("holaa", -5L,"Este es un comentario" );
            
            // Assert
            fail("Se creo un comentario en una publicacion que no existe");
        } catch (Exception e) {
            // OK: No se grabó el comentario con una publicacion inexistente
        }
    }

    @Test
    @Transactional
    public void pruebaMostrarComentarios() throws ExcepcionPublicacion, ExcepcionComentario {
        
        try {
        // arrange
        repositorioComentario.deleteAll();
        repositorioPublicacion.deleteAll();
        repositorioUsuario.deleteAll();

        Usuario usuario = new Usuario("camilo","juan","lina123","NOO","si");
        usuario = repositorioUsuario.save(usuario);

        Publicacion publicacion = new Publicacion("Asi es",false,false,new Timestamp(System.currentTimeMillis()),usuario);
        publicacion = repositorioPublicacion.save(publicacion);
        

        casosDeUsoComentario.subirComentario("camilo",publicacion.getId(),"Hola aaasasas");
        casosDeUsoComentario.subirComentario("camilo",publicacion.getId(),"cahoo");

        // act
        List<Comentario> comentariosAMostrar = casosDeUsoComentario.mostrarComentarios("camilo");

        //Assert
        Optional<Usuario> opcionalUsuario = repositorioUsuario.findById("camilo");
        assertFalse(opcionalUsuario.isEmpty(), "El usuario no aparece en la base de datos");

        Usuario usuarioMostrado = opcionalUsuario.get();
        assertNotNull(usuarioMostrado.getComentarios(), "El usuario tiene comentarios en null");
        assertFalse(usuarioMostrado.getComentarios().isEmpty(), "El usuario no tiene comentarios");
        } catch (ExcepcionUsuarios e) {
            fail("No se pudieron mostrar los comentarios del usuario: ",e);
        }
    }

    @Test
    public void pruebaMostrarComentariosConUnUsuarioQueNoExiste() throws ExcepcionPublicacion, ExcepcionComentario {
        
        try {
            // arrange
            repositorioComentario.deleteAll();
            repositorioPublicacion.deleteAll();
            repositorioUsuario.deleteAll();
    
            // act
            casosDeUsoComentario.mostrarComentarios("camilo");
            
            // Si no se lanza ninguna excepción, la prueba debe fallar
            fail("Se esperaba una UsuarioNoExisteException debido a la ausencia del usuario");
        } catch (ExcepcionUsuarios e) {
            // La excepción esperada fue lanzada, la prueba es exitosa
        }

    }
}