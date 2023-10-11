package com.ingesoft.cyclenet.logic;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
/* 
    //Arrange
    @BeforeAll
    public void prepararAmbienteDeTodaLaSuite(){
        System.out.println("Antes de todas la pruebas");
        System.out.println();
    }*/
    
    @BeforeEach
    public void prepararAmbienteDePruebas(){
        System.out.println("Antes de cada prueba");
        System.out.println();
        repositorioPublicacion.deleteAll();
    }

    //Casos de uso
    //Test login
    @Test
    public void pruebaLoginFallida(){
        try {
            casosDeUsoUsuarios.iniciarSesion("X", "Y");
            fail("Inicio sesion");
            assertEquals(1, 0, "Fallo2");                 
        } catch (Exception e) {
            // TODO: handle exception
            //fail("OK: No inicio sesion");
        }
    }

    public void pruebaLoginAcertada(){
        try {
            //arange
            repositorioUsuario.deleteAll();
            Usuario usuario = new Usuario("holaa","HOLA","arma159","NOO","si");
            repositorioUsuario.save(usuario);

            //act
            casosDeUsoUsuarios.iniciarSesion("holaa", "armaa159");

            //assert
            //fail("OK: Se logro iniciar sesion");

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
            List<Usuario> usuariosConNombreJaime = repositorioUsuario.findByNombreUsuario("Jaime");
            if(usuariosConNombreJaime.size() == 0){
                fail("No se grabo el coso");
            }

        } catch (ExcepcionUsuarios e) {
            // TODO: handle exception
            fail("No se genero >:( ");
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
            casosDeUsoUsuarios.registrarUsuario("Jaime", "Jaime", "Jaime", "Jaime", "Jaime");

            //Assert
            fail("Dejo grabar otro usuario con un login que ya existia");
        } catch (ExcepcionUsuarios e) {
            // TODO: handle exception
            //fail("OK - No se registra usuario con login que ya existe");
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
            // TODO: handle exception
            //fail("OK - No dejo grabar usuario con contrasena de menos de lettras ");
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
