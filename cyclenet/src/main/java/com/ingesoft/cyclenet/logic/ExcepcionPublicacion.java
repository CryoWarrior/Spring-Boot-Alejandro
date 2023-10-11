package com.ingesoft.cyclenet.logic;

public class ExcepcionPublicacion extends Exception{

    public ExcepcionPublicacion(){
        super();
    }

    public ExcepcionPublicacion(String mensaje){
        super(mensaje);
    }

    public ExcepcionPublicacion(String mensaje, Throwable cause){
        super(mensaje, cause);
    }
    
}