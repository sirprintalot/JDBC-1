package com.alura.jdbc.DAO;

import com.alura.jdbc.factory.*;
import com.alura.jdbc.modelo.*;

import javax.swing.*;
import java.sql.*;
import java.util.*;

/**
 * The type Producto dao.
 */
public class ProductoDAO {

    private final Connection con;

    /**
     * Instantiates a new Producto dao.
     *
     * @param con the con
     */
    public ProductoDAO(Connection con) {
        this.con = con;
    }

    /**
     * Guardar producto.
     *
     * @param producto the producto
     */
    public void guardarProducto(Producto producto) {

        int cantidad = producto.getCantidad();
        final int CANTIDAD_MAXIMA = 50;
        final Connection con = new ConnectionFactory().recuperaConexion();
        try (con) {
            con.setAutoCommit(false);
            String insertSQL = "INSERT INTO products(NOMBRE, " +
                    "DESCRIPCION, " +
                    "CANTIDAD, " +
                    "categoria_Id) " +
                    "VALUES(?, ?, ?, ?)";
            
            final PreparedStatement preparedStatement = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            try (preparedStatement) {
                do {
                    int cantidadParaGuardar = Math.min(cantidad, CANTIDAD_MAXIMA);
                    ejecutaRegistro(producto, preparedStatement);
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
        statement.setInt(4, producto.getCategoriaId());
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

    /**
     * Listar producto list.
     *
     * @return the list
     */
    public List<Producto> listarProducto() {
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

    /**
     * Eliminar productos int.
     *
     * @param ids the ids
     * @return the int
     */
    public int eliminarProductos(List<Integer> ids) {

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
                return preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Modificar producto.
     *
     * @param producto the producto
     */
    public void modificarProducto(Producto producto) {
// revisar bug conexion.
        final Connection con = new ConnectionFactory().recuperaConexion();
        try (con) {
            String updateSQl = "UPDATE products SET NOMBRE = ?, DESCRIPCION = ?, CANTIDAD = ? WHERE ID = " + producto.getId();

            final PreparedStatement preparedStatement = con.prepareStatement(updateSQl, Statement.RETURN_GENERATED_KEYS);
            try (preparedStatement) {
                preparedStatement.setString(1, producto.getNombre());
                preparedStatement.setString(2, producto.getDescripcion());
                preparedStatement.setInt(3, producto.getCantidad());
                Statement statement = con.createStatement();

//                ejecutaRegistro(producto, preparedStatement);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 1) {
                    JOptionPane.showMessageDialog(null, "Producto modificado correctamente.");
                    System.out.println("Producto modificado");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Listar producto list.
     *
     * @param categoriaId the categoria id
     * @return the list
     */
// overloaded method
    public List<Producto> listarProducto(int categoriaId) {
        
        final Connection con = new ConnectionFactory().recuperaConexion();
        List<Producto> resultList = new ArrayList<>();
        try (con) {

            var sqlQuery =  "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM " +
                    "PRODUCTS WHERE categoria_id = ?";
            final PreparedStatement preparedStatement = con.prepareStatement(sqlQuery);

            try (preparedStatement) {
                preparedStatement.setInt(1, categoriaId);
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getResultSet();

                while (resultSet.next()) {
                    System.out.println(sqlQuery);
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
