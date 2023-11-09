package com.ingesoft.cyclenet.logic;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ingesoft.cyclenet.logic.CasosDeUsoPublicacionTest;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Usuario;
import com.ingesoft.cyclenet.domain.Publicacion;

@SpringBootTest
public class CasosDeUsoPublicacionTest {
    
    @Autowired
    protected CasosDeUsoPublicacion casosDeUsoPublicacion;
    @Autowired
    protected CasosDeUsoUsuarios casosDeUsoUsuarios;

    @Autowired
    protected RepositorioPublicacion repositorioPublicacion;

    @Autowired
    protected RepositorioUsuario repositorioUsuario;

    @Test
    @Tag("Publicacion")
    @Transactional
    public void pruebaSubirPublicacionExitosamente(){
        try {

            //arrange
            repositorioPublicacion.deleteAll();
            repositorioUsuario.deleteAll();

            Usuario usuario = new Usuario("holaa","HOLA","jsdddd","NOO","si");
            
            // actualiza el usuario con la información luego de guardar en la base de datos
            usuario = repositorioUsuario.save(usuario);

            assertNotNull(usuario, "Usuario está en null");
            assertNotNull(usuario.getPublicaciones(), "El listado de publicaciones aparece en null");
            assertNotNull(usuario.getCalificaciones(), "El listado de calificaciones aparece en null");

            //act
            Long idNuevaPublicacion = casosDeUsoPublicacion.subirPublicacion(
                    "holaa",
                    "Hola a todos", 
                    false, false);
            
        
            //assert
            //Revisa usuario
            Optional<Usuario> opcionalUsuario = repositorioUsuario.findById("holaa");
            assertFalse(opcionalUsuario.isEmpty(), "El usuario no aparece en la base de datos");

            Usuario usuarioModificado = opcionalUsuario.get();
            assertNotNull(usuarioModificado.getPublicaciones(), "El usuario tiene calificaciones en null");
            assertFalse(usuarioModificado.getPublicaciones().isEmpty(), "El usuario no tiene publicaciones");
            
            //Revisa Publiacion
            Optional<Publicacion> opcionalPublicacion = repositorioPublicacion.findById(idNuevaPublicacion);
            assertFalse(opcionalPublicacion.isEmpty(), "La publicacion no aparece en la base de datos");

            Publicacion nuevaPublicacion = opcionalPublicacion.get();
            assertNotNull(nuevaPublicacion.getUsuario(), "El usuario no aparece en la publicacion");
            assertEquals(
                nuevaPublicacion.getUsuario().getNombreUsuario(), 
                usuarioModificado.getNombreUsuario(), 
                "El usuario no es el mismo");


            assertEquals(nuevaPublicacion.getMensaje(), "Hola a todos", "El mensaje de la publicación no coincide con el escrito");

            // OK: Se logro subir una publicacion exitosamente
        
        } catch (Exception e) {
            fail("No se logro guardar una publicacion", e);
        }
    }

    @Test
    @Tag("Publicacion")
    public void pruebaSubirPublicacionConUnUsuarioQueNoExiste(){
        try {
            //arrange
            repositorioPublicacion.deleteAll();
            repositorioUsuario.deleteAll();
            
            //act
            Long idNuevaPublicacion = casosDeUsoPublicacion.subirPublicacion(
                    "ss",
                    "Hola a todos", 
                    false, false);

            //assert
            fail("Se logro subir la publicacion a pesar de la auscencia del usuario");
        
        } catch (Exception e) {
            //"OK: No se graba publicacion con usuario inexistente"
        }
    }

    @Test
    @Tag("Publicacion")
    @Transactional
    public void pruebaMostrarPublicaciones() throws ExcepcionPublicacion {
        
        try {
        // arrange
        repositorioPublicacion.deleteAll();
        repositorioUsuario.deleteAll();

        Usuario usuario = new Usuario("camilo","juan","lina123","NOO","si");
        usuario = repositorioUsuario.save(usuario);

        casosDeUsoPublicacion.subirPublicacion("camilo","Hola a todos", false, false);
        casosDeUsoPublicacion.subirPublicacion("camilo","CHaooo", false, false);

        // act
        List<Publicacion> publicacionesAMostrar = casosDeUsoPublicacion.mostrarPublicaciones("camilo");

        //Assert
        Optional<Usuario> opcionalUsuario = repositorioUsuario.findById("camilo");
        assertFalse(opcionalUsuario.isEmpty(), "El usuario no aparece en la base de datos");

        Usuario usuarioMostrado = opcionalUsuario.get();
        assertNotNull(usuarioMostrado.getPublicaciones(), "El usuario tiene publicaciones en null");
        assertFalse(usuarioMostrado.getPublicaciones().isEmpty(), "El usuario no tiene publicaciones");

        } catch (ExcepcionUsuarios e) {
            fail("No se pudieron mostrar las publicaciones del usuario: ",e);
        }
    }

    @Test
    @Tag("Publicacion")
    public void pruebaMostrarPublicacionConUnUsuarioQueNoExiste() throws ExcepcionPublicacion {
        
        try {
                    // arrange
        repositorioPublicacion.deleteAll();
        repositorioUsuario.deleteAll();
            // act
            casosDeUsoPublicacion.mostrarPublicaciones("holaa");

            // Si no se lanza ninguna excepción, la prueba debe fallar
            fail("Se esperaba una UsuarioNoExisteException debido a la ausencia del usuario");
        } catch (ExcepcionUsuarios e) {
            // La excepción esperada fue lanzada, la prueba es exitosa
        }

    }

    
}


