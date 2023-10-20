package com.ingesoft.cyclenet.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ingesoft.cyclenet.dataAccess.RepositorioComentario;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ComentarioTests {

    @Autowired  
    RepositorioUsuario usuarios;

    @Autowired
    RepositorioPublicacion publicaciones;

    @Autowired
    RepositorioComentario comentarios;

    // Antes de cada prueba, elimina todos los datos
    @BeforeEach
    public void inicializaBaseDatos() {
        comentarios.deleteAll();
        publicaciones.deleteAll();
        usuarios.deleteAll();
    }    


    // Instrucciones para crear un usuario -- usando solo JPA
    @Test
    public void creaUsuario() {

        try {

            // crea el objeto de tipo usuario
            Usuario usuario = new Usuario(
                "bill",
                "Bill Gates",
                "ILoveApple",
                "billg@microsoft.com",
                "300"
            );

            // almacena el usuario y obtiene el objeto ya "enlazado" a la base de datos
            // el nuevo objeto tiene, por ejemplo, valores generados por la base de datos
            // y colecciones que se conectan a la base de datos
            usuario = usuarios.save(usuario);

            // revisa el usuario
            assertNotNull(usuario, "El usuario aparece en null");
            assertEquals(usuario.getNombreUsuario(), "bill", "El nombre del usuario no coincide");

            // revisa si el usuario se creó en la base de datos
            Optional<Usuario> usuarioEnBaseDatos = usuarios.findById("bill");
            assertFalse(usuarioEnBaseDatos.isEmpty(), "El usuario no aparece en la base de datos");
            assertEquals(usuarioEnBaseDatos.get().getNombreUsuario(), "bill", "El nombre de usuario en la BD no coincide");

        } catch (Exception e) {
            // algún error?
            fail("La prueba falla", e);
        }

    }


    // Instrucciones para crear un usuario y una publicacion -- usando solo JPA
    @Test
    @Transactional
    public void creaUsuarioYPublicaciones() {

        try {

            // crea el objeto de tipo usuario
            Usuario usuario = new Usuario(
                "bill",
                "Bill Gates",
                "ILoveApple",
                "billg@microsoft.com",
                "300"
            );

            // almacena el usuario y obtiene el objeto ya "enlazado" a la base de datos
            // el nuevo objeto tiene, por ejemplo, valores generados por la base de datos
            // y colecciones que se conectan a la base de datos
            usuario = usuarios.save(usuario);

            // revisa el usuario
            assertNotNull(usuario, "El usuario aparece en null");
            assertEquals(usuario.getNombreUsuario(), "bill", "El nombre del usuario no coincide");

            // crea el objeto publicacion
            Publicacion publicacion = new Publicacion(
                "Hola mundo",
                false,
                false,
                new Date(),
                usuario
            );

            // almacena el objeto y obtiene el objeto enlazado a la base de datos
            publicacion = publicaciones.save(publicacion);

            // actualiza el objeto de usuarios (relación bidireccional)
            usuario.getPublicaciones().add(publicacion);
            usuario = usuarios.save(usuario);

            // revisa la publicacion
            assertNotNull(publicacion, "La publicacion aparece en null");
            assertNotNull(publicacion.getId(), "No asignó el id generado por la base de datos");

            // revisa si el usuario está conectado a la publicacion
            assertNotNull(publicacion.getUsuario(), "El usuario no aparece en la publicacion");
            assertEquals(publicacion.getUsuario().getNombreUsuario(), "bill", "El usuario en la publicacion no coincide");

            // revisa si el usuario tiene ahora un listado de publicaciones con datos
            Optional<Usuario> opcionalUsuarioEnBD = usuarios.findById("bill");
            assertFalse(opcionalUsuarioEnBD.isEmpty(), "El usuario no aparece en la base de datos");

            Usuario usuarioEnBD = opcionalUsuarioEnBD.get();
            assertNotNull(usuarioEnBD.getPublicaciones(), "El usuario en BD tiene publicaciones en NULL");
            assertFalse(usuarioEnBD.getPublicaciones().isEmpty(), "El usuario en BD no tiene publicaciones");
            assertEquals(usuarioEnBD.getPublicaciones().size(), 1, "El usuario en BD tiene más de una publicación");

            Publicacion publicacionEnBD = usuarioEnBD.getPublicaciones().get(0);
            assertNotNull(publicacionEnBD, "La primera publicación del usuario en BD es NULL");
            assertEquals(publicacionEnBD.getMensaje(), "Hola mundo", "El mensaje de la publicación en BD no coincide");

        } catch (Exception e) {
            // algún error?
            fail("La prueba falla", e);
        }

    }

    // Instrucciones para crear un usuario, publicacion y comentario 
    // -- usando solo JPA
    @Test
    @Transactional
    public void creaUsuarioPublicacionYComentario() {

        try {

            // -- Usuario

            // crea el objeto de tipo usuario
            Usuario usuario = new Usuario(
                "bill",
                "Bill Gates",
                "ILoveApple",
                "billg@microsoft.com",
                "300"
            );

            // almacena el usuario y obtiene el objeto ya "enlazado" a la base de datos
            // el nuevo objeto tiene, por ejemplo, valores generados por la base de datos
            // y colecciones que se conectan a la base de datos
            usuario = usuarios.save(usuario);

            // revisa el usuario
            assertNotNull(usuario, "El usuario aparece en null");
            assertEquals(usuario.getNombreUsuario(), "bill", "El nombre del usuario no coincide");

            // -- Publicación

            // crea el objeto publicacion
            Publicacion publicacion = new Publicacion(
                "Hola mundo",
                false,
                false,
                new Date(),
                usuario
            );

            // almacena el objeto y obtiene el objeto enlazado a la base de datos
            publicacion = publicaciones.save(publicacion);

            // actualiza el objeto de usuarios (relación bidireccional)
            usuario.getPublicaciones().add(publicacion);
            usuario = usuarios.save(usuario);

            // revisa la publicacion
            assertNotNull(publicacion, "La publicacion aparece en null");
            assertNotNull(publicacion.getId(), "No asignó el id generado por la base de datos");

            // -- Comentario

            Comentario comentario = new Comentario(
                "Buena publicación",
                new Date(),
                usuario,
                publicacion  
            );

            // almacena el objeto
            usuario.getComentarios().add(comentario);
            usuario = usuarios.save(usuario);

            publicacion.getComentarios().add(comentario);
            publicacion = publicaciones.save(publicacion);

            comentario = comentarios.save(comentario);

            // revisa el comentario
            assertNotNull(comentario, "El comentario aparece en null");
            assertNotNull(comentario.getId(), "No asignó el id generado al comentario");



        } catch (Exception e) {
            // algún error?
            fail("La prueba falla", e);
        }

    }

}