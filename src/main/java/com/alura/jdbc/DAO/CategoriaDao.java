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
            var sqlQuery = "SELECT id, nombre from categoria";
            final PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);

                try(preparedStatement){
                    System.out.println(sqlQuery); 
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

    // new method
    
    public List<Categoria> listarConProductos() {
        List<Categoria> resultado = new ArrayList<>();
        try {
            // se une la tabla products u categoria linkeandolas con la clave primaria de products (categoria_id) y la clave foranea de categoria (id)
            var sqlQuery = "SELECT C.id, C.nombre, P.id, P.nombre, P.cantidad from categoria C INNER JOIN PRODUCTS P" +
                    " ON C.id = P.categoria_id";
            final PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);

            try(preparedStatement){
//                System.out.println(sqlQuery);
                final ResultSet resultSet = preparedStatement.executeQuery();
                try (resultSet) {
                    while (resultSet.next()){
                        int categoriaId = resultSet.getInt("C.ID") ;
                        String categoriaNombre = resultSet.getString("C.NOMBRE");
                        
                        var categoria = resultado.stream()
                                .filter(cat -> Objects.equals(cat.getId(), categoriaId))
                                .findAny().orElseGet(() -> {
                                    var cat =  new Categoria (categoriaId, categoriaNombre);
                                    resultado.add(cat);
                                    return  cat;
                                } ) ;

                        Producto producto = new Producto(resultSet.getInt("P.ID"), resultSet.getString("P.NOMBRE"),
                                resultSet.getInt("P.CANTIDAD"));

                        categoria.agregar(producto);
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
