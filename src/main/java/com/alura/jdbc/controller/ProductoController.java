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

    public void modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
        final Connection con = new ConnectionFactory().recuperaConexion();
        try (con) {
//            TODO use preparedStatement
            String updateSQl = "UPDATE products SET NOMBRE = ?, DESCRIPCION = ?, CANTIDAD = ? WHERE ID = " + id;

//          TODO use the ejecutaRegistro() for this
            final PreparedStatement preparedStatement = con.prepareStatement(updateSQl);
            try (preparedStatement) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, descripcion);
                preparedStatement.setInt(3, cantidad);
                Statement statement = con.createStatement();

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 1) {
                    JOptionPane.showMessageDialog(null, "Producto modificado correctamente.");
                    System.out.println("Producto modificado");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar");
                }
            }
        }
    }

    public int eliminar(List<Integer> ids) throws SQLException {
        final Connection con = new ConnectionFactory().recuperaConexion();
        try (con) {
            StringBuilder updateSQL = new StringBuilder("DELETE FROM PRODUCTS WHERE ID IN (");
            for (int i = 0; i < ids.size(); i++) {
                updateSQL.append("?");
                if (i < ids.size() - 1) {
                    updateSQL.append(",");
                }
            }
            updateSQL.append(")");

            final PreparedStatement preparedStatement = con.prepareStatement(updateSQL.toString());
            try (preparedStatement) {
                for (int i = 0; i < ids.size(); i++) {
                    preparedStatement.setInt(i + 1, ids.get(i));
                }
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, rowsAffected + " Productos eliminado correctamente");
                    System.out.println("producto eliminado con ID: " + ids);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar nungún producto.");
                }
                return rowsAffected;
            }
        }
    }

//    21/08/2023

    public List<Producto> listar() {
        return productoDAO.listar();

    }

// 2023/08/23

    public void guardar(Producto producto) {

//        ProductoDAO productoDao = new ProductoDAO(new ConnectionFactory().recuperaConexion());
        productoDAO.guardarProducto(producto);
       
    }

}



