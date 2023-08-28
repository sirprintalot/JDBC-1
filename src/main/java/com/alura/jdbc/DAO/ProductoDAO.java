package com.alura.jdbc.DAO;

import com.alura.jdbc.factory.*;
import com.alura.jdbc.modelo.*;

import java.sql.*;
import java.util.*;

public class ProductoDAO {

    private final Connection con;

    public ProductoDAO(Connection con) {
        this.con = con;
    }

    public void guardarProducto(Producto producto) {
        int cantidad = producto.getCantidad();
        final int CANTIDAD_MAXIMA = 50;
//        final Connection con = new ConnectionFactory().recuperaConexion();

        try (con) {
            con.setAutoCommit(false);
            String insertSQL = "INSERT INTO products(NOMBRE, DESCRIPCION, CANTIDAD) VALUES(?, ?, ?)";
            final PreparedStatement statement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            try (statement) {
                do {
                    int cantidadParaGuardar = Math.min(cantidad, CANTIDAD_MAXIMA);
                    ejecutaRegistro(producto, statement);
                    cantidad -= CANTIDAD_MAXIMA;
                } while (cantidad > 0);
                con.commit();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void ejecutaRegistro(Producto producto, PreparedStatement statement) throws SQLException {

        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected == 1) {
            final ResultSet resultSet = statement.getGeneratedKeys();
            try (resultSet) {
                while (resultSet.next()) {
                    producto.setId(resultSet.getInt(1));
                    System.out.println(producto);
                }
            }
        } else {
            throw new SQLException("Error");
        }
    }

    public List<Producto> listar() {
        final Connection con = new ConnectionFactory().recuperaConexion();
        List<Producto> resultList = new ArrayList<>();
        
        try (con) {
            final PreparedStatement preparedStatement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM " +
                    "PRODUCTS");
            try (preparedStatement) {
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getResultSet();

                while (resultSet.next()) {

                    Producto fila = new Producto(resultSet.getInt("ID"),
                            resultSet.getString("NOMBRE"),
                            resultSet.getString("DESCRIPCION"),
                            resultSet.getInt("CANTIDAD")
                            );

                    resultList.add(fila);
                }
                return resultList;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
