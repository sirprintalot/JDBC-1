package com.alura.jdbc.modelo;

/**
 * The type Producto.
 */
public class Producto {

    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private Integer categoriaId;


    /**
     * Instantiates a new Producto.
     *
     * @param nombre      the nombre
     * @param descripcion the descripcion
     * @param cantidad    the cantidad
     */
    public Producto(String nombre, String descripcion, Integer cantidad) {
         this.nombre = nombre;
         this.descripcion = descripcion;
         this.cantidad = cantidad;
    }

    /**
     * Instantiates a new Producto.
     *
     * @param id          the id
     * @param nombre      the nombre
     * @param descripcion the descripcion
     * @param cantidad    the cantidad
     */
    public Producto(Integer id, String nombre, String descripcion, Integer cantidad){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    /**
     * Instantiates a new Producto.
     *
     * @param id       the id
     * @param nombre   the nombre
     * @param cantidad the cantidad
     */
    public Producto(int id, String nombre, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    /**
     * Gets nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets nombre.
     *
     * @param nombre the nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets descripcion.
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets descripcion.
     *
     * @param descripcion the descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets cantidad.
     *
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Sets cantidad.
     *
     * @param cantidad the cantidad
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get id int.
     *
     * @return the int
     */
    public int getId(){
        return id;
    }

    /**
     * Gets categoria id.
     *
     * @return the categoria id
     */
    public int getCategoriaId() {
        return this.categoriaId;
    }

    /**
     * Sets categoria id.
     *
     * @param categoriaId the categoria id
     */
    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
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
