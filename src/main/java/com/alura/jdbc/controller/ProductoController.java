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

//            TODO use the ejecutaRegistro() for this
            preparedStatement = con.prepareStatement(updateSQl);
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, descripcion);
            preparedStatement.setInt(3, cantidad);
            Statement statement = con.createStatement();

//            int updateCount = statement.getUpdateCount();
//            System.out.println(updateCount);

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

// 2023/08/23

    public void guardar(Map<String, String> producto) throws SQLException {
        Connection con;
        String nombre = producto.get("NOMBRE");
        String descripcion = producto.get("DESCRIPCION");
        int cantidad = Integer.parseInt(producto.get("CANTIDAD"));
        int cantidadMaxima = 50;

        con = new ConnectionFactory().recuperaConexion();
        con.setAutoCommit(false);
        String insertSQL = "INSERT INTO products(NOMBRE, DESCRIPCION, CANTIDAD) VALUES(?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

        try {
            do {
                int cantidadParaGuardar = Math.min(cantidad, cantidadMaxima);
                ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, statement);
                cantidad -= cantidadMaxima;
            } while (cantidad > 0);
            con.commit();
        }
        catch(Exception e){
            con.rollback();
        }
        finally {
            if (statement != null) {
                statement.close();
            }
            con.close();
        }
    }

    private void ejecutaRegistro(String nombre, String descripcion, int cantidad, PreparedStatement statement) throws SQLException {

        ResultSet resultSet = null;

//        if(cantidad < 50){
//            throw new RuntimeException("error de prueba ");
//        }

        try {
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, cantidad);

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 1) {
                resultSet = statement.getGeneratedKeys();
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
        }
    }


}

