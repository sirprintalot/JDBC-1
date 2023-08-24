package com.alura.jdbc.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.alura.jdbc.controller.CategoriaController;
import com.alura.jdbc.controller.ProductoController;

public class ControlDeStockFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JLabel labelNombre, labelDescripcion, labelCantidad, labelCategoria;
    private JTextField textoNombre, textoDescripcion, textoCantidad;
    private JComboBox<Object> comboCategoria;
    private JButton botonGuardar, botonModificar, botonLimpiar, botonEliminar, botonReporte;
    private JTable tabla;
    private DefaultTableModel modelo;
    private ProductoController productoController;
    private CategoriaController categoriaController;

    public ControlDeStockFrame() {
        super("Productos");

        this.categoriaController = new CategoriaController();
        this.productoController = new ProductoController();

        Container container = getContentPane();
        setLayout(null);

        configurarCamposDelFormulario(container);

        configurarTablaDeContenido(container);

        configurarAccionesDelFormulario();
    }

    private void configurarTablaDeContenido(Container container) {
        tabla = new JTable();

        modelo = (DefaultTableModel) tabla.getModel();
        modelo.addColumn("Identificador del Producto");
        modelo.addColumn("Nombre del Producto");
        modelo.addColumn("Descripción del Producto");
        modelo.addColumn("Cantidad");

        cargarTabla();

        tabla.setBounds(10, 205, 760, 280);

        botonEliminar = new JButton("Eliminar");
        botonModificar = new JButton("Modificar");
        botonReporte = new JButton("Ver Reporte");
        botonEliminar.setBounds(10, 500, 80, 20);
        botonModificar.setBounds(100, 500, 80, 20);
        botonReporte.setBounds(190, 500, 80, 20);

        container.add(tabla);
        container.add(botonEliminar);
        container.add(botonModificar);
        container.add(botonReporte);

        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void configurarCamposDelFormulario(Container container) {
        labelNombre = new JLabel("Nombre del Producto");
        labelDescripcion = new JLabel("Descripción del Producto");
        labelCantidad = new JLabel("Cantidad");
        labelCategoria = new JLabel("Categoría del Producto");

        labelNombre.setBounds(10, 10, 240, 15);
        labelNombre.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        labelDescripcion.setBounds(10, 50, 240, 15);
        labelCantidad.setBounds(10, 90, 240, 15);
        labelCategoria.setBounds(10, 130, 240, 15);

        labelNombre.setForeground(Color.BLACK);
        labelDescripcion.setForeground(Color.BLACK);
        labelCategoria.setForeground(Color.BLACK);

        textoNombre = new JTextField();
        textoDescripcion = new JTextField();
        textoCantidad = new JTextField();
        comboCategoria = new JComboBox<>();
        comboCategoria.addItem("Elige una Categoría");

        // TODO
        var categorias = this.categoriaController.listar();
        // categorias.forEach(categoria -> comboCategoria.addItem(categoria));

        textoNombre.setBounds(10, 25, 265, 20);
        textoDescripcion.setBounds(10, 65, 265, 20);
        textoCantidad.setBounds(10, 105, 265, 20);
        comboCategoria.setBounds(10, 145, 265, 20);

        botonGuardar = new JButton("Guardar");
        botonLimpiar = new JButton("Limpiar");
        botonGuardar.setBounds(10, 175, 80, 20);
        botonLimpiar.setBounds(100, 175, 80, 20);

        container.add(labelNombre);
        container.add(labelDescripcion);
        container.add(labelCantidad);
        container.add(labelCategoria);
        container.add(textoNombre);
        container.add(textoDescripcion);
        container.add(textoCantidad);
        container.add(comboCategoria);
        container.add(botonGuardar);
        container.add(botonLimpiar);
    }

    private void configurarAccionesDelFormulario() {
        botonGuardar.addActionListener(e -> {
            try {
                guardar();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            limpiarTabla();
            cargarTabla();
        });

        botonLimpiar.addActionListener(e -> limpiarFormulario());

        botonEliminar.addActionListener(e -> {
            eliminarProducto();
            limpiarTabla();
            cargarTabla();
        });

        botonModificar.addActionListener(e -> {
            modificar();
            limpiarTabla();
            cargarTabla();
        });

        botonReporte.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirReporte();
            }
        });
    }

    private void abrirReporte() {
        new ReporteFrame(this);
    }

    private void limpiarTabla() {
        modelo.getDataVector().clear();
    }

    private boolean tieneFilaElegida() {
        return tabla.getSelectedRowCount() == 0 || tabla.getSelectedColumnCount() == 0;
    }

    private void modificar() {

        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }
        Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
                .ifPresentOrElse(fila -> {
                    Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
                    String nombre = (String) modelo.getValueAt(tabla.getSelectedRow(), 1);
                    String descripcion = (String) modelo.getValueAt(tabla.getSelectedRow(), 2);
                    Integer cantidad = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 3).toString());

                    try {
                        this.productoController.modificar(nombre, descripcion, cantidad, id);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
    }

    private void eliminarProducto() {
        int[] selectedRows = tabla.getSelectedRows();
        
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }


        int confirmaEleminar = JOptionPane.showConfirmDialog(this,
                "Esta seguro que desea eliminar " + selectedRows.length + " elementos?", "Eliminar",
                JOptionPane.YES_NO_OPTION );

        if(confirmaEleminar != JOptionPane.YES_OPTION) {
            return;
        }

        List<Integer> idsToDelete = new ArrayList<>();
        
//        System.out.println("Selected Rows: " + Arrays.toString(selectedRows));
//        System.out.println("Model Row Count: " + modelo.getRowCount());
//
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "selecione al menos un producto.");
        }

        for (int selectedRow : tabla.getSelectedRows()) {
            Integer id = Integer.valueOf(modelo.getValueAt(selectedRow, 0).toString());
            idsToDelete.add(id);
        }

        int cantidadEliminada;

        try {
            cantidadEliminada = this.productoController.eliminar(idsToDelete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (int i = selectedRows.length - 1; i >= 0; i--) {
            modelo.removeRow(selectedRows[i]);
        }

        System.out.println("Productos eliminados correctamente.");

//        Optional.ofNullable(modelo.getValueAt(tabla.getSelectedRow(), tabla.getSelectedColumn()))
//                .ifPresentOrElse(fila -> {
//                    Integer id = Integer.valueOf(modelo.getValueAt(tabla.getSelectedRow(), 0).toString());
//                    int cantidadEliminada;
//                    try {
//                        cantidadEliminada = this.productoController.eliminar(id);
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    }
//                    modelo.removeRow(tabla.getSelectedRow())
//                    JOptionPane.showMessageDialog(this, cantidadEliminada + " item eliminado con éxito!");
//                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
    }

    private void cargarTabla() {
        try {
            var productos = this.productoController.listar();
            try {
                productos.forEach(producto -> modelo.addRow(new Object[]{producto.get("ID"),
                        producto.get("NOMBRE"), producto.get("DESCRIPCION"),
                        producto.get("CANTIDAD")}));
            } catch (Exception e) {
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void guardar() throws SQLException {
        if (textoNombre.getText().isBlank() || textoDescripcion.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Los campos Nombre y Descripción son requeridos.");
            return;
        }
        Integer cantidadInt;
        try {
            cantidadInt = Integer.parseInt(textoCantidad.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, String
                    .format("El campo cantidad debe ser numérico dentro del rango %d y %d.", 0, Integer.MAX_VALUE));
            return;
        }
        // TODO
        var producto = new HashMap<String, String>();
        producto.put("NOMBRE", textoNombre.getText());
        producto.put("DESCRIPCION", textoDescripcion.getText());
        producto.put("CANTIDAD", String.valueOf(cantidadInt));

        var categoria = comboCategoria.getSelectedItem();

        try {
            this.productoController.guardar(producto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        JOptionPane.showMessageDialog(this, "Registrado con éxito!");

        this.limpiarFormulario();
    }

    private void limpiarFormulario() {
        this.textoNombre.setText("");
        this.textoDescripcion.setText("");
        this.textoCantidad.setText("");
        this.comboCategoria.setSelectedIndex(0);
    }

}
