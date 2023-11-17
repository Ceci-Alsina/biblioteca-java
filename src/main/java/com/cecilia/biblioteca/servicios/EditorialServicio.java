package com.cecilia.biblioteca.servicios;

import com.cecilia.biblioteca.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cecilia.biblioteca.entidades.Editorial;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import com.cecilia.biblioteca.excepciones.MiException;

@Service
public class EditorialServicio {
    @Autowired // inyección de dependencia-->se inicializa sola
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void crearEditorial(String nombre) throws MiException {
        validar(nombre);

        Editorial editorial = new Editorial();

        editorial.setNombre(nombre);

        editorialRepositorio.save(editorial);
    }

    public List<Editorial> listarEditoriales() {
        List<Editorial> editoriales = new ArrayList<>();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }

    public void modificarEditorial(String id, String nombre) throws MiException {
        validar(nombre);

        Optional<Editorial> respuesta = editorialRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();

            editorial.setNombre(nombre);

            editorialRepositorio.save(editorial);
        }

    }

    @Deprecated
    @Transactional(readOnly = true)
    public Editorial getEditorialPorId(String idEditorial) {
        
        return editorialRepositorio.getOne(idEditorial);
    }

    public void validar(String nombre) throws MiException {
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacio");
        }

    }

}
