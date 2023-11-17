package com.cecilia.biblioteca.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cecilia.biblioteca.entidades.Editorial;

@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{


    
}
