package com.cecilia.biblioteca.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;


@Entity
@Data
public class Editorial {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idEditorial;
    private String nombre; 
    
}
