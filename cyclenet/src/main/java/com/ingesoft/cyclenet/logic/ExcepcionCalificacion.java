package com.ingesoft.cyclenet.logic;

public class ExcepcionCalificacion extends Exception{
    public ExcepcionCalificacion(){
        super();
    }

    public ExcepcionCalificacion(String mensaje){
        super(mensaje);
    }

    public ExcepcionCalificacion(String mensaje, Throwable cause){
        super(mensaje, cause);
    }
    
}
