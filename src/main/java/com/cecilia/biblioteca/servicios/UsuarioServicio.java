package com.cecilia.biblioteca.servicios;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.cecilia.biblioteca.entidades.Imagen;
import com.cecilia.biblioteca.entidades.Usuario;
import com.cecilia.biblioteca.enumeraciones.Rol;
import com.cecilia.biblioteca.excepciones.MiException;
import com.cecilia.biblioteca.repositorios.UsuarioRepositorio;


@Service
public class UsuarioServicio implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiException {

        validar(nombre, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        Imagen imagen =imagenServicio.guardar(archivo);
        usuario.setImagen(imagen);        

        usuarioRepositorio.save(usuario);
    }
    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario,  String nombre, String email, String password, String password2) throws MiException{

        validar(nombre, email, password, password2);
        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario); 

        if(respuesta.isPresent()){

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
            usuario.setRol(Rol.USER);

            String idImagen = null;  // para que no dé error de que no está inicializada

            if(usuario.getImagen() != null){
                idImagen = usuario.getImagen().getIdImagen();                
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            usuario.setImagen(imagen);

            usuarioRepositorio.save(usuario); 

        }
        
    }

    private void validar(String nombre, String email, String password, String password2)
            throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o vacío");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("la password no puede estar vacía o tener menos de 5 dígitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("las contraseñas deben ser iguales");
        }

    }

    // Toma un correo electrónico como entrada y devuelve un objeto
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {

            // Aquí se crea un nuevo objeto GrantedAuthority que representa un permiso del
            // usuario.
            List<GrantedAuthority> permisos = new ArrayList<>();

            // El rol del usuario se añade al prefijo “ROLE_” para crear el nombre del
            // permiso.
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

            permisos.add(p);

            // User user = new User(usuario.getEmail(), usuario.getPassword(), permisos); va
            // directamente en el return!!

            // Aquí se obtienen los atributos de la solicitud actual -->en attr viaja la información (se castean los tipos de datos)
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            // Se obtiene la sesión HTTP actual de la solicitud y el objeto que trae se guarda en session
             HttpSession session = attr.getRequest().getSession(true);

            // Se setean los atributos de session-->añade el usuario a la sesión con el nombre “usuariosession” (que es un nombre de llave).
            session.setAttribute("usuariosession", usuario); 

            // Se crea y devuelve un nuevo objeto User (que implementa la interfaz UserDetails)
            // con el correo electrónico, la contraseña y los permisos del usuario.
            return new User(usuario.getEmail(), usuario.getPassword(), permisos);

        } else {

            return null;

        }

    }

    @Deprecated
    @Transactional
    public Usuario getUsuarioPorId(String idUsuario) {
        
        return usuarioRepositorio.getOne(idUsuario);
    }
}
