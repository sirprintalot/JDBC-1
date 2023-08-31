package com.alura.jdbc.controller;

import com.alura.jdbc.factory.*;
import com.alura.jdbc.modelo.*;
import com.alura.jdbc.DAO.*;

import javax.swing.*;
import java.sql.*;
import java.util.*;

//TODO make controlDeStockFrame handle the messages
public class ProductoController {

    private final ProductoDAO productoDAO;

    public ProductoController(){
        this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
    }

    public void modificar(Producto producto)  {
         productoDAO.modificarProducto(producto);
    }


    public int eliminar(List<Integer> ids) {
         return productoDAO.eliminarProductos(ids);
    }


    public List<Producto> listar() {
        return productoDAO.listarProducto();
    }

//    29/08/2023
    public List<Producto> listar(Categoria categoria){
        return productoDAO.listarProducto(categoria.getId());
    }


    public void guardar(Producto producto, Integer categoriaId) {
        producto.setCategoriaId(categoriaId);
        productoDAO.guardarProducto(producto);
       
    }

}



