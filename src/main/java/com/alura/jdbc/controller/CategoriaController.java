package com.alura.jdbc.controller;

import com.alura.jdbc.DAO.*;
import com.alura.jdbc.factory.*;
import com.alura.jdbc.modelo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Categoria controller.
 */
public class CategoriaController {

    private CategoriaDao categoriaDao;

    /**
     * Instantiates a new Categoria controller.
     */
    public CategoriaController(){
        var factory = new ConnectionFactory();
        this.categoriaDao = new CategoriaDao(factory.recuperaConexion());
    }

    /**
     * Listar list.
     *
     * @return the list
     */
    public List<Categoria> listar() {
		return categoriaDao.listar();
	}

    /**
     * Carga reporte list.
     *
     * @return the list
     */
//    31/08/2023
    public List<Categoria> cargaReporte() {

//        return this.listar(); n+1 method
        return this.categoriaDao.listarConProductos();
    }



}
