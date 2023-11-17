package com.cecilia.biblioteca.servicios;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cecilia.biblioteca.repositorios.AutorRepositorio;
import com.cecilia.biblioteca.entidades.Autor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.cecilia.biblioteca.excepciones.MiException;

@Service
public class AutorServicio {

    @Autowired // permite que la clase AutorServicio acceda al repositorio de autores para
               // realizar operaciones en la base de datos.
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void crearAutor(String nombre) throws MiException {
        validar(nombre);
        Autor autor = new Autor();

        autor.setNombre(nombre);

        autorRepositorio.save(autor);

    }

    @Transactional
    public List<Autor> listarAutores() {
        List<Autor> autores = new ArrayList<Autor>();
        autores = autorRepositorio.findAll();
        return autores;
    }

    @Transactional
    public void modificarAutor(String nombre, String idAutor) throws MiException {

        validar(nombre);
        
        Optional<Autor> respuesta = autorRepositorio.findById(idAutor);

        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();

            autor.setNombre(nombre);

            autorRepositorio.save(autor);
        }

    }

    @Deprecated
    @Transactional(readOnly = true)
    public Autor getAutorPorId(String idAutor) {
         return autorRepositorio.getOne(idAutor);
    }

    public void validar(String nombre) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }

    }

}
