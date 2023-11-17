package com.cecilia.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.cecilia.biblioteca.entidades.Autor;
import com.cecilia.biblioteca.entidades.Editorial;
import com.cecilia.biblioteca.entidades.Libro;
import com.cecilia.biblioteca.excepciones.MiException;
import com.cecilia.biblioteca.repositorios.LibroRepositorio;
import com.cecilia.biblioteca.repositorios.AutorRepositorio;
import com.cecilia.biblioteca.repositorios.EditorialRepositorio;

@Service
public class LibroServicio {
    @Autowired
    public LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Transactional // si el método se ejecuta sin excepciones se realiza un commit a la base de
                   // datos, en cambio si el método lanza una expception y no es atrapada
    public void crearLibro(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor,
            String idEditorial)
            throws MiException {
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }
        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }
        Libro libro = new Libro();

        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        libroRepositorio.save(libro);

    }

    @Transactional(readOnly = true)
    public List<Libro> listarLibros() {
        List<Libro> libros = new ArrayList<>();
        libros = libroRepositorio.findAll();
        return libros;

    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)
            throws MiException {

        validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Libro> respuesta = libroRepositorio.findById(isbn);

        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);

        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);

        Autor autor = new Autor();

        Editorial editorial = new Editorial();

        if (respuestaAutor.isPresent()) {
            autor = respuestaAutor.get();
        }

        if (respuestaEditorial.isPresent()) {
            editorial = respuestaEditorial.get();
        }

        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();

            libro.setTitulo(titulo);

            libro.setAutor(autor);

            libro.setEditorial(editorial);

            libro.setEjemplares(ejemplares);

            libroRepositorio.save(libro);
        }
    }

    @Deprecated
    @Transactional(readOnly = true)
    public Libro getLibroPorisbn(Long isbn) {
        
        return libroRepositorio.getOne(isbn);
    }

    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial)
            throws MiException {

        if (isbn == null) {
            throw new MiException("el isbn no puede ser nulo");
        }

        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("el titulo no puede ser nulo o estar vacio");
        }
        if (ejemplares == null) {
            throw new MiException("el ejemplares no puede ser nulo");
        }

        if (idAutor.isEmpty() || idAutor == null) {
            throw new MiException("el titulo no puede ser nulo o estar vacio");
        }
        if (idEditorial.isEmpty() || idEditorial == null) {
            throw new MiException("el titulo no puede ser nulo o estar vacio");
        }
    }

}
