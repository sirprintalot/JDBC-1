package com.alura.jdbc.factory;

import com.mchange.v2.c3p0.*;

import javax.sql.*;
import java.sql.*;

public class ConnectionFactory {

    private DataSource dataSource;

    public ConnectionFactory(){
        var pooleDataSource = new ComboPooledDataSource();
        pooleDataSource.setJdbcUrl("jdbc:mysql://localhost/stock_control?useTimeZone=true&serverTimeZone=UTC");
        pooleDataSource.setUser("root");
        pooleDataSource.setPassword("maucaralar");
        pooleDataSource.setMaxPoolSize(10);

        this.dataSource = pooleDataSource;
    }
    
    public Connection recuperaConexion()  {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
