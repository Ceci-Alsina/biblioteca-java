package com.cecilia.biblioteca.controladores;

import com.cecilia.biblioteca.entidades.Autor;
import com.cecilia.biblioteca.excepciones.MiException;
import com.cecilia.biblioteca.servicios.AutorServicio;
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
@RequestMapping("/autor") //localhost:8080/autor
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/registrar") //localhost:8080/autor/registrar
    public String registrar(){
        return "autor_form.html";
    }
    
          
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "El autor ha sido cargado");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_form.html";
        }
        return "index.html";
    }
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List <Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        
        return "autor_list.html";
               
    }
    @Deprecated
    @GetMapping("/modificar/{idAutor}")
    public String modificar(@PathVariable String idAutor, ModelMap modelo){
        modelo.put("autor", autorServicio.getAutorPorId(idAutor));
        
        return "autor_modificar.html";
    }
    
    @PostMapping("/modificar/{idAutor}")
    public String modificar(@PathVariable String idAutor, String nombre, ModelMap modelo){
        try {
            autorServicio.modificarAutor(nombre, idAutor);
            
            return "redirect:../lista";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
            
    
}
}