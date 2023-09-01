package com.alura.jdbc.modelo;

import java.util.*;

public class Categoria {

    private int id;
    private String nombre;

    private List<Producto> productos;


    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    @Override
    public String toString() {

        return this.nombre;
    }

    public int getId() {
        return this.id;
    }

    public void agregar(Producto producto) {
        if (this.productos == null){
            this.productos = new ArrayList<>();
        }
        productos.add(producto);
    }

    public List<Producto> getProductos() {
        return this.productos;
    }
}
