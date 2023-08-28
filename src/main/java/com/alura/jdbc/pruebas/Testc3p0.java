package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.*;

import java.sql.*;

public class Testc3p0 {
    public static void main(String[] args) throws SQLException {
        ConnectionFactory cf = new ConnectionFactory();

        for(int i = 0; i < 15; i++){
            Connection conexion = cf.recuperaConexion();
            System.out.println("Abriendo conexion # " + (i + 1));
        }



    }
}
