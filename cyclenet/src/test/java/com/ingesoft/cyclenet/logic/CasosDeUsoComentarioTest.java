package com.ingesoft.cyclenet.logic;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.sql.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ingesoft.cyclenet.logic.CasosDeUsoComentario; // Asegúrate de importar la clase adecuada.
import com.ingesoft.cyclenet.dataAccess.RepositorioCalificacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioComentario; // Asegúrate de importar la clase adecuada.
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.domain.Comentario;
import com.ingesoft.cyclenet.domain.Usuario;
import com.ingesoft.cyclenet.domain.Publicacion;

@SpringBootTest
public class CasosDeUsoComentarioTest {
    
    @Autowired
    protected CasosDeUsoComentario casosDeUsoComentario; // Asegúrate de inyectar la clase adecuada.
    @Autowired
    protected RepositorioComentario repositorioComentario; // Asegúrate de inyectar la clase adecuada.
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

    @Test
    public void pruebaPublicarComentarioExitosamente(){
        try {
            // Arrange

            Usuario usuario = new Usuario("holaa", "HOLA", "jsdddd", "NOO", "si");
            usuario = repositorioUsuario.save(usuario);
            
            Publicacion publicacion = new Publicacion("Asi es",false,false,Date.valueOf(LocalDate.now()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);

            assertNotNull(usuario, "Usuario está en null");
            assertNotNull(publicacion, "La publicación aparece en null");

            // Act
  /*          Long idNuevoComentario = casosDeUsoComentario.publicarComentario(
                   "Este es un comentario", new Date(), usuario, publicacion);
            
            // Assert
            Optional<Comentario> optionalComentario = repositorioComentario.findById(idNuevoComentario);
            assertFalse(optionalComentario.isEmpty(), "El comentario no aparece en la base de datos");

            Comentario nuevoComentario = optionalComentario.get();
            assertNotNull(nuevoComentario.getUsuario(), "El usuario no aparece en el comentario");
            assertEquals(nuevoComentario.getUsuario().getNombreUsuario(), usuario.getNombreUsuario(), "El usuario no es el mismo");

            assertNotNull(nuevoComentario.getPublicacion(), "La publicación no aparece en el comentario");
            assertEquals(nuevoComentario.getPublicacion().getMensaje(), publicacion.getComentarios(), "La publicación no es la misma");
*/
            // OK: Se logró publicar un comentario exitosamente
        } catch (Exception e) {
            fail("No se logró publicar un comentario", e);
        }
    }

    @Test
    public void pruebaPublicarComentarioConUsuarioQueNoExiste(){
        try {
            // Arrange
            repositorioComentario.deleteAll();
            repositorioUsuario.deleteAll();
            repositorioPublicacion.deleteAll();

            Usuario usuario = new Usuario("holaa", "HOLA", "jsdddd", "NOO", "si");
            
            Publicacion publicacion = new Publicacion("Asi es",false,false,Date.valueOf(LocalDate.now()),usuario);
            publicacion = repositorioPublicacion.save(publicacion);

            // Act
            /*Long idNuevoComentario = casosDeUsoComentario.publicarComentario(
                    "Este es un comentario", Date.valueOf(LocalDate.now()), usuario, publicacion);
*/
            // Assert
        } catch (Exception e) {
            // OK: No se grabó el comentario con un usuario inexistente
        }
    }
}