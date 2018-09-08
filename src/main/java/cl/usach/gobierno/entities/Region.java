package cl.usach.gobierno.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "region")

public class Region implements Serializable {

	private static final long serialVersionUID = 1L;

	public Region(){};

	public Region(Region region){
		this.idregion = region.idregion;
		this.nombre = region.nombre;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idregion",nullable = false)
	private int idregion;

	@Column(name="nombre")
	private String nombre;

	@Column(name="compositivos")
	private int compositivos;

	@Column(name="comnegativos")
	private int comnegativos;

	@Column(name="comneutros")
	private int comneutros;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "idpoliticos")
	private Political political;


	public int getId() {
		return idregion;
	}

	public void setId(int id) {
		this.idregion = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCompositivos(){
		return compositivos;
	}

	public void setCompositivos(int compositivos){
		this.compositivos = compositivos;
	}
	public int getComnegativos(){
		return comnegativos;
	}

	public void setComnegativos(int comnegativos){
		this.comnegativos = comnegativos;
	}

	public int getComneutros(){
		return comneutros;
	}

	public void setComneutros(int comneutros){
		this.comnegativos = comneutros;
	}

	public Political getPolitical(){
		return political;
	}

	public void setPolitical(Political political){
		this.political = political;
	}

}