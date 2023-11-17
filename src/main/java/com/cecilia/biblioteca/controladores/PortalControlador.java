package com.cecilia.biblioteca.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.cecilia.biblioteca.entidades.Usuario;
import com.cecilia.biblioteca.excepciones.MiException;
import com.cecilia.biblioteca.servicios.UsuarioServicio;

@Controller
@RequestMapping("/") // localhost: 8080/
public class PortalControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @GetMapping("/")
    public String index() {
        return "Index.html";
    }

    @GetMapping("/registrar")
    public String registrar() {

        return "registro.html"; // es la vista dónde vamos a renderizar con el formulario p/registro
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo, MultipartFile archivo) {
        try {
            usuarioServicio.registrar(archivo, nombre, email, password, password2);
            modelo.put("exito", "Usuario registrado correctamente");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", ex.getMessage());
            modelo.put("email", ex.getMessage());
            return "registro.html";
        }
        return "index.html";
    }

    @GetMapping("/login") // es un parámetro que puede venir o no -->required=falso
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o contraseña incorrecta");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        // logueado tiene todos los datos de la session
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        if (logueado.getRol().toString().equals("ADMIN")) {

            return "redirect:/admin/dashboard";
        }

        return "inicio.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Usuario usuario = (Usuario) session.getAttribute("usuariosession");

        modelo.put("usuario", usuario);

        return "usuario_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping("/perfil/{idUsuario}")
    public String actualizar(MultipartFile archivo, @PathVariable String idUsuario, @RequestParam String nombre,
            @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo) {
                
        try {
            usuarioServicio.actualizar(archivo, idUsuario, nombre, email, password, password2);
            modelo.put("exito", "Usuario actualizado correctamente!");
            return "inicio.html";

        } catch (Exception ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);

            return "usuario_modificar.html";

        }

    }

}
