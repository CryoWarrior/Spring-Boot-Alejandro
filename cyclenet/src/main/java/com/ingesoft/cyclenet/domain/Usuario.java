package com.ingesoft.cyclenet.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter @Setter
@EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
public class Usuario {
    
    @Id
    protected String nombreUsuario;
    
    protected String nombre;
    protected String contraseña;
    protected String correo;
    protected String celular;

    protected boolean log = false;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    protected List<Comentario> comentarios = new ArrayList<>();
    
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    protected List<Publicacion> publicaciones = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    protected List<Calificacion> calificaciones = new ArrayList<>();

    public Usuario(String nombreUsuario, String nombre, String contraseña, String correo, String celular){
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.celular = celular;
        this.log = true;
    }

}
