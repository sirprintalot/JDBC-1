package com.alura.jdbc.DAO;

import com.alura.jdbc.modelo.*;

import java.sql.*;
import java.util.*;

public class CategoriaDao {

    private Connection con;

    public CategoriaDao(Connection con) {
        this.con = con;
    }

    public List<Categoria> listar() {
         List<Categoria> resultado = new ArrayList<>();

        try {
            final PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT id, nombre from categoria"
            ) ;
                try(preparedStatement){
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    try (resultSet) {
                        while (resultSet.next()){
                           var categoriaListada = new Categoria(resultSet.getInt("ID"), resultSet.getString("nombre"));
                           resultado.add(categoriaListada);
                        }
                    }
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultado;
    }
}
