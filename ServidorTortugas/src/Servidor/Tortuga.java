package Servidor;

import java.io.Serializable;

public class Tortuga implements Serializable {

    private String nombre;
    private int dorsal;

    //Constructor objeto Tortuga sin parámetros
    public Tortuga() {
        nombre = "Pablo";
        dorsal = 05;
    }

    //Constructor objeto Tortuga con parámetros de entrada (nombre y dorsal)
    public Tortuga(String nombre, int dorsal) {
        this.nombre = nombre;
        this.dorsal = dorsal;
    }

    //Métodos Getters
    public String getNombre() {
        return nombre;
    }

    public int getDorsal() {
        return dorsal;
    }

    //Métodos Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDorsal(int dorsal) {
        this.dorsal = dorsal;
    }

    //Método toString
    @Override
    public String toString() {
        return "Tortuga{" + "nombre=" + nombre + ", dorsal=" + dorsal + '}';
    }
}
