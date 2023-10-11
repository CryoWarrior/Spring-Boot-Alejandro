package com.ingesoft.cyclenet.logic;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
                    usuario,
                    "Hola a todos", 
                    false, false);
            //assert

            Optional<Usuario> opcionalUsuario = repositorioUsuario.findById("holaa");
            assertFalse(opcionalUsuario.isEmpty(), "El usuario no aparece en la base de datos");

            Usuario usuarioModificado = opcionalUsuario.get();
            assertNotNull(usuarioModificado.getPublicaciones(), "El usuario no tiene publicaciones");

            Optional<Publicacion> opcionalPublicacion = repositorioPublicacion.findById(idNuevaPublicacion);
            assertFalse(opcionalPublicacion.isEmpty(), "La publicacion no aparece en la base de datos");

            Publicacion nuevaPublicacion = opcionalPublicacion.get();
            assertNotNull(nuevaPublicacion.getUsuario(), "El usuario no aparece en la publicacion");
            assertEquals(
                nuevaPublicacion.getUsuario().getNombreUsuario(), 
                usuarioModificado.getNombreUsuario(), 
                "El usuario no es el mismo");

            // OK: Se logro subir una publicacion exitosamente
        
        } catch (Exception e) {
            fail("No se logro guardar una publicacion", e);
        }
    }

    @Test
    public void pruebaSubirPublicacionConUnUsuarioQueNoExiste(){
        try {
            //arrange
            System.out.println("Segundo caso");
            repositorioPublicacion.deleteAll();
            repositorioUsuario.deleteAll();

            Usuario usuario = new Usuario("holaa","HOLA","jsdddd","NOO","si");
            
            usuario = repositorioUsuario.save(usuario);

            assertNotNull(usuario, "Usuario está en null");
            assertNotNull(usuario.getPublicaciones(), "El listado de publicaciones aparece en null");
            assertNotNull(usuario.getCalificaciones(), "El listado de calificaciones aparece en null");

            //act
            Long idNuevaPublicacion = casosDeUsoPublicacion.subirPublicacion(
                    usuario,
                    "Hola a todos", 
                    false, false);

            //assert
            fail("Se logro subir la publicacion publicacion a pesar de la auscencia del usuario");
        
        } catch (Exception e) {
            //fail("OK: No se graba publicacion con usuario inexistente");
        }
    }
    
}
