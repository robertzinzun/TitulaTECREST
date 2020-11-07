/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DTO;

/**
 *
 * @author roberto
 */
public class Carrera {
    private int idCarrera;
    private String nombre;

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public String getNombre() {
        return nombre;
    }
    
}
