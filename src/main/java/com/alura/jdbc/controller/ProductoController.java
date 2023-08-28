package com.alura.jdbc.controller;

import com.alura.jdbc.factory.*;

import javax.swing.*;
import java.sql.*;
import java.util.*;

//TODO make controlDeStockFrame handle the messages
public class ProductoController {

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

    public List<Map<String, String>> listar() throws SQLException {

        final Connection con = new ConnectionFactory().recuperaConexion();
        try (con) {

            final PreparedStatement preparedStatement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM " +
                    "PRODUCTS");
            try (preparedStatement) {

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
                return resultList;
            }
        }
    }

// 2023/08/23

    public void guardar(Map<String, String> producto) throws SQLException {
        String nombre = producto.get("NOMBRE");
        String descripcion = producto.get("DESCRIPCION");
        int cantidad = Integer.parseInt(producto.get("CANTIDAD"));
        final int CANTIDAD_MAXIMA = 50;
        final Connection con = new ConnectionFactory().recuperaConexion();

        try (con) {
            con.setAutoCommit(false);
            String insertSQL = "INSERT INTO products(NOMBRE, DESCRIPCION, CANTIDAD) VALUES(?, ?, ?)";

            final PreparedStatement statement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            try (statement) {
                do {
                    int cantidadParaGuardar = Math.min(cantidad, CANTIDAD_MAXIMA);
                    ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, statement);
                    cantidad -= CANTIDAD_MAXIMA;
                } while (cantidad > 0);
                con.commit();
            } catch (Exception e) {
                con.rollback();
            }
        }
    }

    private void ejecutaRegistro(String nombre, String descripcion, int cantidad, PreparedStatement statement) throws SQLException {

        statement.setString(1, nombre);
        statement.setString(2, descripcion);
        statement.setInt(3, cantidad);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 1) {
            final ResultSet resultSet = statement.getGeneratedKeys();
            try (resultSet) {
                while (resultSet.next()) {
                    System.out.println("producto ingresado correctamente");
                }
            }
        } else {
            throw new SQLException("Error");
        }
    }


}

