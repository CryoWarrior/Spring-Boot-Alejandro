package com.ingesoft.cyclenet.logic;

public class ExcepcionComentario extends Exception{
    public ExcepcionComentario(){
        super();
    }

    public ExcepcionComentario(String mensaje){
        super(mensaje);
    }

    public ExcepcionComentario(String mensaje, Throwable cause){
        super(mensaje, cause);
    }
}
