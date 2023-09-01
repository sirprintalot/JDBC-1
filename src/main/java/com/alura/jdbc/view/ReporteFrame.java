package com.alura.jdbc.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import com.alura.jdbc.controller.*;
import com.alura.jdbc.modelo.*;

/**
 * The type Reporte frame.
 */
public class ReporteFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tablaReporte;
    private DefaultTableModel modelo;
    private JScrollPane scroll_table;
    
//    private ProductoController productoController;

    private CategoriaController categoriaController;

    /**
     * Instantiates a new Reporte frame.
     *
     * @param controlDeStockFrame the control de stock frame
     */
    public ReporteFrame(ControlDeStockFrame controlDeStockFrame) {
        super("Reporte de produtos del stock");


        this.categoriaController = new CategoriaController();
//        this.productoController = new ProductoController();

        Container container = getContentPane();
        setLayout(null);
        
        tablaReporte = new JTable();
//        tablaReporte.setBounds(0, 0, 600, 400);
//        tablaReporte.setBorder(BorderFactory.createLineBorder(Color.black));
        container.add(tablaReporte);

        scroll_table = new JScrollPane(tablaReporte);
        scroll_table.setBounds(0, 0, 650, 420);
        scroll_table.setBorder(new EmptyBorder(20, 10, 30, 10));
        container.add(scroll_table);

        modelo = (DefaultTableModel) tablaReporte.getModel();
        modelo.addColumn("");
        modelo.addColumn("");
        modelo.addColumn("");
        modelo.addColumn("");

        cargaReporte();

        setSize(650, 450);
        setVisible(true);
        setLocationRelativeTo(controlDeStockFrame);
        setResizable(false);
    }

    private void cargaReporte() {
//         add product enumeration
        
        modelo.addRow(new Object[]{"CATEGORIA", "PRODUCTO", "CANTIDAD"});
        modelo.addRow(new Object[]{""});

        var contenido = categoriaController.cargaReporte();

        contenido.forEach(categoria -> {modelo
                .addRow(new Object[] {categoria});
            
//        var productos = this.productoController.listar(categoria); reemplazamos esto con el nuevo metodo
//            de categoria y categoriaDAO

            var productos = categoria.getProductos();
        int counter = 1;

            for (Producto producto : productos) {
                 modelo.addRow(new Object[]{
                         counter,
                         producto.getNombre(),
                         producto.getCantidad()
                 });
              counter++;
            }
        });
    }

}
