package cl.usach.gobierno.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity
@Table(name = "localidad")

public class Localidad implements Serializable {

	private static final long serialVersionUID = 1L;

	public Localidad(){};

	public Localidad(Localidad localidad){
		this.idlocalidad = localidad.idlocalidad;
		this.nombre = localidad.nombre;
		this.isRegion = localidad.isRegion;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idlocalidad",nullable = false)
	private int idlocalidad;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "isRegion")
	private boolean isRegion;

	@OneToMany(mappedBy = "localidad", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	@JoinColumn(name = "idpoliticos")
	private List<PoliticoLocalidad> politicals = new ArrayList<>();


	public int getId() {
		return idlocalidad;
	}

	public void setId(int id) {
		this.idlocalidad = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean getCompositivos(){
		return isRegion;
	}

	public void setCompositivos(boolean isRegion){
		this.isRegion = isRegion;
	}

	public List<PoliticoLocalidad> getPoliticals(){
		return politicals;
	}

	public void setPoliticals(List<PoliticoLocalidad> politicals){
		this.politicals = politicals;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Localidad localidad = (Localidad) o;
		return Objects.equals(nombre, localidad.nombre);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

}