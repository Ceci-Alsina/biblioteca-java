package com.cecilia.biblioteca.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cecilia.biblioteca.entidades.Imagen;

@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
    
}
