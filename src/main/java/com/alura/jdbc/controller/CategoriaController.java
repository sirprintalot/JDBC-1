package com.alura.jdbc.controller;

import com.alura.jdbc.DAO.*;
import com.alura.jdbc.factory.*;
import com.alura.jdbc.modelo.*;

import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    private CategoriaDao categoriaDao;

    public CategoriaController(){
        var factory = new ConnectionFactory();
        this.categoriaDao = new CategoriaDao(factory.recuperaConexion());
    }

	public List<Categoria> listar() {
		return categoriaDao.listar();
	}

    public List<?> cargaReporte() {
        // TODO
        return new ArrayList<>();
    }

}
