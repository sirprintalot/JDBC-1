package com.alura.jdbc.factory;

import java.sql.*;

public class ConnectionFactory {
    public Connection recuperaConexion() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/stock_control?useTimeZone=true&serverTimeZone=UTC",
                "root",
                "maucaralar");
    }
}
