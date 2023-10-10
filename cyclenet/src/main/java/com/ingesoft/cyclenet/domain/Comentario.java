package com.ingesoft.cyclenet.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue
    protected Long id;

    protected String mensaje;
    protected Date fecha;

    @ManyToOne
    protected Publicacion publicacion;
    
    @ManyToOne
    protected Usuario usuario;

    @OneToMany(mappedBy = "comentario")
    protected List<Calificacion> calificaciones = new ArrayList<>();

    public Comentario(String mensaje, Date fecha, Usuario usuario , Publicacion publicacion){
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.usuario = usuario;
        this.publicacion = publicacion;
    }
}
