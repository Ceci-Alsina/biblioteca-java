package com.cecilia.biblioteca.controladores;

import com.cecilia.biblioteca.excepciones.MiException;
import com.cecilia.biblioteca.servicios.EditorialServicio;
import com.cecilia.biblioteca.entidades.Editorial;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "¡La editorial se creó exitosamente!");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_form.html";
        }
        return "index.html";
    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.put("editoriales", editoriales);
        return "editorial_list.html";
    }

    @Deprecated
    @GetMapping("/modificar/{idEditorial}")
    public String modificar(@PathVariable String idEditorial, ModelMap modelo){
        modelo.put("editorial", editorialServicio.getEditorialPorId(idEditorial));
        
        return "editorial_modificar.html";
    }
    
    @PostMapping("/modificar/{idEditorial}")
    public String modificar(@PathVariable String idEditorial, String nombre, ModelMap modelo){
        try {
            editorialServicio.modificarEditorial(nombre, idEditorial);
            
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
            
    
}

}
