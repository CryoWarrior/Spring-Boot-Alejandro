package com.ingesoft.cyclenet.logic;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ingesoft.cyclenet.dataAccess.RepositorioUsuario;
import com.ingesoft.cyclenet.domain.Usuario;

@Service
public class CasosDeUsoUsuarios {
    
    @Autowired
    protected RepositorioUsuario repositorioUsuario;

    
    
    public void iniciarSesion(String login, String password) throws ExcepcionUsuarios{

        // Valida que exista un registro con ese usuario 
        Optional<Usuario> u = repositorioUsuario.findById(login);
        if(u.isEmpty()){
            throw new ExcepcionUsuarios("Usuario no existe");
        }

        //Valida que la contraseña es correcta
        if(!u.get().getContraseña().equals(password)){
            throw new ExcepcionUsuarios("Contraseña no coincide"); 
        }
        return;
    }

    public void registrarUsuario(String nombreUsuario, String nombre, String contraseña, String correo, String celular) throws ExcepcionUsuarios{
        
        Optional<Usuario> optional = repositorioUsuario.findById(nombreUsuario);
        if(!optional.isEmpty()){
            throw new ExcepcionUsuarios("Nombre de usuario ya existe");
        }

        if(contraseña.trim().length()<4){
            throw new ExcepcionUsuarios("La contrasena debe tener mas de 3 letras");
        }

        if(!correo.contains("@")){
            throw new ExcepcionUsuarios("El correo debe contener un @");
        }
        if(!correo.contains(".")){
            throw new ExcepcionUsuarios("El correo debe contener un punto (.)");
        }

        //Guardar usuario
        Usuario u = new Usuario(nombreUsuario, nombre, contraseña, correo, celular);
        repositorioUsuario.save(u);

        return;
    }

    public void realizarComentario(){

    }

}
