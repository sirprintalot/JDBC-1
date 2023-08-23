package com.alura.jdbc.controller;

import com.alura.jdbc.factory.*;
import com.mysql.cj.jdbc.*;

import javax.print.attribute.standard.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class ProductoController {

    public void modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = new ConnectionFactory().recuperaConexion();
            String updateSQl = "UPDATE products SET NOMBRE = ?, DESCRIPCION = ?, CANTIDAD = ? WHERE ID = " + id;

            preparedStatement = con.prepareStatement(updateSQl);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, descripcion);
            preparedStatement.setInt(3, cantidad);

            Statement statement = con.createStatement();
            int updateCount = statement.getUpdateCount();

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                JOptionPane.showMessageDialog(null, "Producto modificado correctamente.");
                System.out.println("Producto modificado");
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo modificar");
            }
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public int eliminar(List<Integer> ids) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = new ConnectionFactory().recuperaConexion();

            String updateSQL = "DELETE FROM PRODUCTS WHERE ID IN (";
            for (int i = 0; i < ids.size(); i++) {
                updateSQL += "?";
                if (i < ids.size() - 1) {
                    updateSQL += ",";
                }
            }
            updateSQL += ")";
            preparedStatement = con.prepareStatement(updateSQL);

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

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

//    21/08/2023

    public List<Map<String, String>> listar() throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();

        Statement statement = con.createStatement();
        PreparedStatement preparedStatement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM " +
                "PRODUCTS");

        preparedStatement.execute();

        ResultSet resultSet = preparedStatement.getResultSet();
        List<Map<String, String>> resultList = new ArrayList<>();

        while (resultSet.next()) {
            Map<String, String> row = new HashMap<>();

            row.put("ID", String.valueOf(resultSet.getInt("ID")));
            row.put("NOMBRE", resultSet.getString("NOMBRE"));
            row.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
            row.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));

            resultList.add(row);

        }
        con.close();
        return resultList;
    }


    public void guardar(Map<String, String> producto) throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            con = new ConnectionFactory().recuperaConexion();
            String insertSQL = "INSERT INTO products(NOMBRE, DESCRIPCION, CANTIDAD) VALUES(?, ?, ?)";

            preparedStatement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, producto.get("NOMBRE"));
            preparedStatement.setString(2, producto.get("DESCRIPCION"));
            preparedStatement.setInt(3, Integer.parseInt(producto.get("CANTIDAD")));

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
//                    System.out.printf("Producto insertado con ID: ", resultSet.getInt(1));
                    System.out.println("Producto insertado con ID: " + resultSet.getInt(1));
                }
            } else {
                throw new SQLException("product insert failed.");
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}

//     PRIMER METODO
//    public void guardar(Map<String, String> producto) throws SQLException{
//        Connection con = new ConnectionFactory().recuperaConexion();
//
//        Statement statement = con.createStatement();
//
//        statement.execute( "INSERT INTO products(NOMBRE, DESCRIPCION, CANTIDAD) " +   "VALUES('"+ producto.get(
//                "NOMBRE") + "' , '"+ producto.get("DESCRIPCION") + "', "+ producto.get("CANTIDAD") + ")",
//                Statement.RETURN_GENERATED_KEYS);
//
//        ResultSet resultSet = statement.getGeneratedKeys();
//
//        while(resultSet.next()){
//            System.out.printf("Insertado el producto con ID %d%n",
//                    resultSet.getInt(1));
//        }
//
//    }
