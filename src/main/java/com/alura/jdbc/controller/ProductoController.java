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

// 28/08/2023
    public int eliminar(List<Integer> ids) {
         return productoDAO.eliminarProductos(ids);
    }

//    21/08/2023

    public List<Producto> listar() {
        return productoDAO.listarProducto();
    }

// 2023/08/23

    public void guardar(Producto producto) {
        productoDAO.guardarProducto(producto);
       
    }

}



