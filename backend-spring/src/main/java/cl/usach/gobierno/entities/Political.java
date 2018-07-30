package cl.usach.gobierno.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "political")

public class Political implements Serializable {

    private static final long serialVersionUID = 1L;

    public Political(){}

    public Political(Political political){
        this.id = political.id;
        this.nombre = political.nombre;
        this.descripcion = political.descripcion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true,nullable = false)
    private int id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="comentariosNegativos")
    private Double comentariosNegativos;

    @Column(name="comentariosPositivos")
    private Double comentariosPositivos;

    @Column(name="comentariosNeutros")
    private Double comentariosNeutros;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre(){return nombre;}

    public void setNombre(String nombre){this.nombre = nombre;}

    public String getDescripcion(){return descripcion;}

    public void setDescripcion(String descripcion){this.descripcion = descripcion;}

    public Double getComentariosNegativos(){return comentariosNegativos;}

    public void setComentariosNegativos(Double comentariosNegativos){this.comentariosNegativos = comentariosNegativos;}

    public Double getComentariosPositivos(){return comentariosPositivos;}

    public void setComentariosPositivos(Double comentariosPositivos){this.comentariosPositivos = comentariosPositivos;}

    public Double getComentariosNeutros(){return comentariosNeutros;}

    public void setComentariosNeutros(Double comentariosNeutros){this.comentariosNeutros = comentariosNeutros;}
}
