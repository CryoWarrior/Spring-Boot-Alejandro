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

import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Usuario;



@SpringBootTest
public class CasosDeUsoUsuarioTest {
    
    @Autowired
    protected CasosDeUsoUsuarios casosDeUsoUsuarios;   
    
    @Autowired
    protected RepositorioUsuario repositorioUsuario;

    //Arrange
    @BeforeAll
    public void prepararAmbienteDeTodaLaSuite(){
        System.out.println("Antes de todas la pruebas");
        System.out.println();
    }
    
    @BeforeEach
    public void prepararAmbienteDePruebas(){
        System.out.println("Antes de cada prueba");
        System.out.println();
    }


    //Casos de uso
    @Test
    public void pruebaLogin(){
        try {
            casosDeUsoUsuarios.iniciarSesion("X", "Y");
                
        } catch (Exception e) {
            // TODO: handle exception
            fail("Fallo");
            assertEquals(1, 0, "Fallo2");   
        }
    }

    @Test
    public void registrarUsuarioSinErrores(){
        try {

            //arrange
            repositorioUsuario.deleteAll();

            //act
            casosDeUsoUsuarios.registrarUsuario("Jaime","Jaime Lombo","Gola123","juan@hola.net","31565431");

            //assert
            List<Usuario> usuariosConNombreJaime = repositorioUsuario.findById("Jaime");
            if(usuariosConNombreJaime.size() == 0){
                fail("No se grabo el coso");
            }

        } catch (ExcepcionUsuarios e) {
            // TODO: handle exception
            fail("Se genero usuario y no deberia", e);
        }
    }

    @Test
    public void registrarUsuarioConLoginQueYaExiste(){
        try {

            //Arrange
            repositorioUsuario.deleteAll();
            Usuario u = new Usuario("jaime","jaime","jaime","jaime","jaime");
            
            //Act
            casosDeUsoUsuarios.registrarUsuario("Jaime", "Jaime", "Jaime", "Jaime", "Jaime");

            //Assert
            fail("Dejo grabar otro usuario con un login que ya existia");
        } catch (ExcepcionUsuarios e) {
            // TODO: handle exception
            fail("OK");
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
            fail("Dejo grabar usuario con una contrasena de menos de 5 letra    s");


        } catch (ExcepcionUsuarios e) {
            // TODO: handle exception
            fail("OK");
        }


    }

    //Clean up

    @AfterEach
    public void despuesDeLaPrueba(){
        System.out.println("Luego de cada prueba");
        System.out.println();
    }

    @AfterAll
    public void despuesDeTodasLasPruebas(){
        System.out.println("Despues de todas las pruebas");
        System.out.println();
    }
}
