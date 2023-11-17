package com.cecilia.biblioteca.entidades;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Entity
@Data
public class Imagen {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idImagen;

    private String mime; //es el atributo que asigna el formato del archivo de la imagen 
    private String nombre;

    @Lob  @Basic(fetch = FetchType.LAZY)     // con esto le decimos a spring que el dato puede ser grande @basic carga perezosa(lazy)
    private byte[] contenido;  //un arreglo de bytes y solo se carga por si usamos un get()

    
}
