package com.ingesoft.cyclenet.logic;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ingesoft.cyclenet.logic.CasosDeUsoPublicacionTest;
import com.ingesoft.cyclenet.dataAccess.RepositorioPublicacion;
import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Usuario;

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
    public void pruebaP(){
        try {
            //arrange
            repositorioPublicacion.deleteAll();
            repositorioUsuario.deleteAll();
            Usuario usuario = new Usuario("holaa","HOLA","jsdddd","NOO","si");
                repositorioUsuario.save(usuario);

        

            //act
            casosDeUsoPublicacion.subirPublicacion(usuario,"Hola a todos", false, false);

            //assert
            fail("Se logro subir una publicacion exitosamente");
        
        } catch (Exception e) {
            fail("No se logro guardar una publicacion");
        }
    }
    
}
