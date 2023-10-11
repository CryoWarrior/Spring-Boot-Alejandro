package com.ingesoft.cyclenet.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
 public class Publicacion {

    @Id
    @GeneratedValue
    private Long id;

    protected String mensaje;
    protected Boolean foto;
    protected Boolean lugar;

    @Temporal(TemporalType.DATE)
    protected Date fecha;

    @ManyToOne
    protected Usuario usuario;

    @OneToMany(mappedBy = "publicacion")
    protected List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "publicacion")
    protected List<Calificacion> calificaciones = new ArrayList<>();

    // Constructor con parámetros sin ID
    public Publicacion(String mensaje, Boolean foto, Boolean lugar, Date fecha, Usuario usuario) {
        this.mensaje = mensaje;
        this.foto = foto;
        this.lugar = lugar;
        this.fecha = fecha;
        this.usuario = usuario;
    }

}
