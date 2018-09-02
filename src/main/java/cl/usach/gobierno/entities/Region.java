package cl.usach.gobierno.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class Region {

	@Entity
	@Table(name = "region")

	public class Political implements Serializable {

	    private static final long serialVersionUID = 1L;


	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id_region",unique = true,nullable = false)
	    private int id_region;

	    @Column(name="poblacion")
	    private int poblacion;

	    @Column(name="porcentajeAprobacion")
	    private int porcentajeAprobacion;

	    
	    public Integer getId() {
	        return id_region;
	    }

	    public void setId(Integer id) {
	        this.id_region = id;
	    }
	    
	    public int getPoblacion(){return poblacion;}

	    public void setPoblacion(int poblacion){this.poblacion = poblacion;}

	    public int getPorcentajeAprovacion(){return porcentajeAprobacion;}

	    public void setPorcentajeAprobacion(int porcentaje){this.porcentajeAprobacion = porcentaje;}
	}
}
