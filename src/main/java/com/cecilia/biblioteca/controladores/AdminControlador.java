package com.cecilia.biblioteca.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @GetMapping("/dashboard") 
    public String panelAdministrativo(){ // dentro de esta clase se pueden tener muchos métodos que necesiten una pre autorización
        return "panel.html";            // esto se puede hacer desde acá o en general desde la clase seguridad web
    }
    
}
