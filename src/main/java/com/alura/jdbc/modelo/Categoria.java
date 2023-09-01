package com.alura.jdbc.modelo;

import java.util.*;

/**
 * The type Categoria.
 */
public class Categoria {

    private int id;
    private String nombre;

    private List<Producto> productos;


    /**
     * Instantiates a new Categoria.
     *
     * @param id     the id
     * @param nombre the nombre
     */
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    @Override
    public String toString() {

        return this.nombre;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Agregar.
     *
     * @param producto the producto
     */
    public void agregar(Producto producto) {
        if (this.productos == null){
            this.productos = new ArrayList<>();
        }
        productos.add(producto);
    }

    /**
     * Gets productos.
     *
     * @return the productos
     */
    public List<Producto> getProductos() {
        return this.productos;
    }
}
