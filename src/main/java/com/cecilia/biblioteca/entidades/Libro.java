package com.cecilia.biblioteca.entidades;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.TemporalType;
import lombok.Data;




@Entity
@Data
public class Libro {
    @Id
    private Long isbn;
    private String titulo;
    private Integer ejemplares;

    @Temporal(TemporalType.DATE)
    private Date alta;
    @ManyToOne
    private Editorial editorial;
    @ManyToOne
    private Autor autor;

}
