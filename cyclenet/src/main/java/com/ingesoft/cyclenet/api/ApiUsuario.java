package com.ingesoft.cyclenet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingesoft.cyclenet.logic.CasosDeUsoUsuarios;

@RestController
public class ApiUsuario    
{
    @Autowired
    protected CasosDeUsoUsuarios casosDeUsoUsuarios;

    @PostMapping("/api/usuarios")
    public void crearUsuario(String nombre){
        //casosDeUsoUsuarios.crearUsuario(nombre);
    }

    @GetMapping("/api/hola")
    public String holaMundo(){
        return "holaMundo";
    }
    
    @PostMapping("/api/seguir")
    public void seguimientoUsuario(){
        //Usuario usuarioActual = obtenerUsuarioActual(); // Obtener el usuario actual
        //dejarDeSeguirUsuario( uActual, "xx");
    }

}
