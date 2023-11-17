package com.cecilia.biblioteca.repositorios;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cecilia.biblioteca.entidades.Autor;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    
}
