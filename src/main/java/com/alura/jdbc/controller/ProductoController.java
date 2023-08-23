package com.alura.jdbc.controller;

import com.alura.jdbc.factory.*;
import com.mysql.cj.jdbc.*;

import javax.print.attribute.standard.*;
import java.sql.*;
import java.util.*;

public class ProductoController {

    public void modificar(String nombre, String descripcion, Integer id) {
        // TODO
    }

    public void eliminar(Integer id) {
        // TODO
    }


//    21/08/2023
    
    public List<Map<String, String>> listar() throws SQLException {
        Connection con = new ConnectionFactory().recuperaConexion();

        Statement statement = con.createStatement();
        boolean result = statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTS");

        ResultSet resultSet = statement.getResultSet();
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


     public void guardar(Map<String, String> producto) throws SQLException{
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

               if (rowsAffected == 1){
                   resultSet = preparedStatement.getGeneratedKeys();
                   while(resultSet.next()){
                       System.out.printf("Producto insertado con ID: ", resultSet.getInt(1));
                   }
               } else {
                   throw new SQLException("product insert failed.");
               }
           } finally {
               if(resultSet != null){
                   resultSet.close();
               }
               if (preparedStatement != null){
                   preparedStatement.close();
               }
               if (con != null){
                   con.close();
               }
           }
     }

     
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
}
