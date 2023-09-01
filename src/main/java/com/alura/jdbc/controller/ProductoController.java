package com.alura.jdbc.controller;

import com.alura.jdbc.factory.*;
import com.alura.jdbc.modelo.*;
import com.alura.jdbc.DAO.*;

import javax.swing.*;
import java.sql.*;
import java.util.*;

/**
 * The type Producto controller.
 */
//TODO make controlDeStockFrame handle the messages
public class ProductoController {

    private final ProductoDAO productoDAO;

    /**
     * Instantiates a new Producto controller.
     */
    public ProductoController(){
        this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
    }

    /**
     * Modificar.
     *
     * @param producto the producto
     */
    public void modificar(Producto producto)  {
         productoDAO.modificarProducto(producto);
    }


    /**
     * Eliminar int.
     *
     * @param ids the ids
     * @return the int
     */
    public int eliminar(List<Integer> ids) {
         return productoDAO.eliminarProductos(ids);
    }


    /**
     * Listar list.
     *
     * @return the list
     */
    public List<Producto> listar() {
        return productoDAO.listarProducto();
    }

    /**
     * Listar list.
     *
     * @param categoria the categoria
     * @return the list
     */
//    29/08/2023
    public List<Producto> listar(Categoria categoria){
        return productoDAO.listarProducto(categoria.getId());
    }


    /**
     * Guardar.
     *
     * @param producto    the producto
     * @param categoriaId the categoria id
     */
    public void guardar(Producto producto, Integer categoriaId) {
        producto.setCategoriaId(categoriaId);
        productoDAO.guardarProducto(producto);
       
    }

}



