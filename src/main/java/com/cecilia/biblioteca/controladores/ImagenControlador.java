package com.cecilia.biblioteca.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cecilia.biblioteca.entidades.Usuario;
import com.cecilia.biblioteca.servicios.UsuarioServicio;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    @Autowired
    UsuarioServicio usuarioServicio;

    @Deprecated
    @GetMapping("/perfil/{idUsuario}")
    public ResponseEntity<byte[]> imagenUsuario(@PathVariable String idUsuario) {

        Usuario usuario = usuarioServicio.getUsuarioPorId(idUsuario);

        byte[] imagen = usuario.getImagen().getContenido(); // las imágenes en html se consumen como una url 

        HttpHeaders headers = new HttpHeaders(); // esta cabecera le dice al navegador que lo que devolvemos es una imagen

        headers.setContentType(MediaType.IMAGE_JPEG);


        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);

    }

}
