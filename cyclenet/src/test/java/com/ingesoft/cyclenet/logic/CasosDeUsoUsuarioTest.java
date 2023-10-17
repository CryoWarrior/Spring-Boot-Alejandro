package com.ingesoft.cyclenet.logic;

import java.util.Optional;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ingesoft.cyclenet.dataAccess.RepositorioCalificacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioComentario;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Usuario;


@SpringBootTest
public class CasosDeUsoUsuarioTest {
    
    @Autowired
    protected CasosDeUsoUsuarios casosDeUsoUsuarios;   
    
    @Autowired
    protected RepositorioUsuario repositorioUsuario;
    @Autowired
    protected RepositorioPublicacion repositorioPublicacion;
    @Autowired
    protected RepositorioCalificacion repositorioCalificacion;
    @Autowired
    protected RepositorioComentario repositorioComentario;

    @BeforeEach
    public void prepararAmbienteDePruebas(){
        System.out.println("Antes de cada prueba");
        System.out.println();
        repositorioCalificacion.deleteAll();
        repositorioComentario.deleteAll();
        repositorioPublicacion.deleteAll();

    }

    //Casos de uso
    //Test login
    @Test
    public void pruebaLoginFallidaPorUsuario(){
        try {
            //Arrange
            repositorioUsuario.deleteAll();
            Usuario usuario = new Usuario("camilo","HOLA","arma159","NOO","si");
            repositorioUsuario.save(usuario);

            //Act
            casosDeUsoUsuarios.iniciarSesion("X", "arma159");

            //Assert
            fail("Inicio sesion con usuario incorrecto");
        } catch (Exception e) {
            // OK -- No inicio sesion
        }
    }

    @Test
    public void pruebaLoginFallidaPorContrasena(){
        try {
            //Arrange
            repositorioUsuario.deleteAll();
            Usuario usuario = new Usuario("camilo","HOLA","arma159","NOO","si");
            repositorioUsuario.save(usuario);

            //Act
            casosDeUsoUsuarios.iniciarSesion("camilo", "Y");

            //Assert
            fail("Inicio sesion con contrasena incorrecta");
        } catch (Exception e) {
            // OK -- No inicio sesion
        }
    }

    @Test
    public void pruebaLoginAcertada(){
        try {
            //arange
            repositorioUsuario.deleteAll();
            Usuario usuario = new Usuario("holaa","HOLA","armaa159","NOO","si");
            repositorioUsuario.save(usuario);

            //act
            casosDeUsoUsuarios.iniciarSesion("holaa", "armaa159");

            //assert
            Optional<Usuario> usuariosConNombreJaime = repositorioUsuario.findById("holaa");
            if(usuariosConNombreJaime.isEmpty()){
                fail("Usuario al que se inicio sesion no existe");
            }
            Usuario u = usuariosConNombreJaime.get();
            assertEquals(u.getContraseña(), "armaa159", "Se entro con una contrasena incorrecta");

        } catch (Exception e) {
            fail("No se logro iniciar sesion :(");
        }
    }


    //Tests registrar usuario
    @Test
    public void registrarUsuarioSinErrores(){
        try {

            //arrange
            repositorioUsuario.deleteAll();

            //act
            casosDeUsoUsuarios.registrarUsuario("Jaime","Jaime Lombo","Gola123","juan@hola.net","31565431");


            //assert
            Optional<Usuario> usuariosConNombreJaime = repositorioUsuario.findById("Jaime");
            if(usuariosConNombreJaime.isEmpty()){
                fail("No se grabo el usuario");
            }

            Usuario u = usuariosConNombreJaime.get();
            assertNotNull(u, "El usuario aparece en null");
            assertNotNull(u.getPublicaciones(), "El listado de publicaciones aparece en null");
            assertNotNull(u.getCalificaciones(), "El listado de calificaciones aparece en null");

        } catch (ExcepcionUsuarios e) {
             // OK
        }
    }

    @Test
    public void registrarUsuarioConLoginQueYaExiste(){
        try {

            //Arrange
            repositorioUsuario.deleteAll();
            Usuario u = new Usuario("jaime","jaime","jaime","jaime","jaime");
            repositorioUsuario.save(u);

            //Act
            casosDeUsoUsuarios.registrarUsuario("jaime", "Jaime", "Jaime", "Jaime@voa.net", "31000");

            //Assert
            fail("Dejo grabar otro usuario con un login que ya existia");
        } catch (ExcepcionUsuarios e) {
             // OK
        }
    }

    @Test
    public void registrarUsuarioConContrasenaDeMenosDe4Letras(){
        try {
            //Arrange
            repositorioUsuario.deleteAll();
        
            //Act
            casosDeUsoUsuarios.registrarUsuario("Jaime","Jaime Lombo","l2","juan@hola.net","31565431");
        
            //Assert
            fail("Dejo grabar usuario con una contrasena de menos de 5 letras");
        } catch (ExcepcionUsuarios e) {
            // OK - No dejo grabar usuario con contrasena de menos de lettras
        }
    }

    @Test
    public void registrarUsuarioConCorreoSinArroba(){
        try {
            //Arrange
            repositorioUsuario.deleteAll();
        
            //Act
            casosDeUsoUsuarios.registrarUsuario("Jaime","Jaime Lombo","l232123","juan.net","31565431");
        
            //Assert
            fail("Dejo grabar usuario con una correo sin arroba");
        } catch (ExcepcionUsuarios e) {
            // OK - No dejo grabar usuario con correo sin arroba
        }
    }

    @Test
    public void registrarUsuarioConCorreoSinPunto(){
        try {
            //Arrange
            repositorioUsuario.deleteAll();
        
            //Act
            casosDeUsoUsuarios.registrarUsuario("Jaime","Jaime Lombo","l232123","juan@homral","31565431");
        
            //Assert
            fail("Dejo grabar usuario con una correo sin punto");
        } catch (ExcepcionUsuarios e) {
            // OK - No dejo grabar usuario con  con correo sin punto
        }
    }

    //Clean up

    @AfterEach
    public void despuesDeLaPrueba(){
        System.out.println("Luego de cada prueba");
        System.out.println();
    }
/* 
    @AfterAll
    public void despuesDeTodasLasPruebas(){
        System.out.println("Despues de todas las pruebas");
        System.out.println();
    }*/
}
