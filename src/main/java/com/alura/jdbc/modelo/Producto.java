package com.alura.jdbc.modelo;

public class Producto {

    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;


    public Producto(String nombre, String descripcion, Integer cantidad) {
         this.nombre = nombre;
         this.descripcion = descripcion;
         this.cantidad = cantidad;
    }

    public Producto(Integer id, String nombre, String descripcion, Integer cantidad){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString() {
        return String.format(
                "{ID: %d, NOMBRE: %s, DESCRIPCION: %s, CANTIDAD: %d}",
                this.id,
                this.nombre,
                this.descripcion,
                this.cantidad
        );
    }
}
