package cl.citiaps.spring.backend.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the politico database table.
 * 
 */
@Entity
@Table(name="political")
@NamedQuery(name="politicos.findAll", query="SELECT p FROM Political p")
public class Political implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="political_id", unique=true, nullable=false)
	private int idPolitical;
	
	@Column(name="comentariosNegativos", nullable=false)
	private double comentariosNegativos;
	
	@Column(name="comentariosPositivos", nullable=false)
	private double comentariosPositivos;

	@Column(name="comentariosNeutros", nullable=true)
	private int comentariosNeutros;

	@Column(name="descripcion", nullable=true)
	private String descripcion;

	@Column(name="nombre", nullable=false)
	private String nombre;

	//bi-directional many-to-one association to Usuario
	//@ManyToOne
	//@JsonIgnore
	//@JoinColumn(name="idusuario")
	//private Usuario usuario;

	//bi-directional many-to-one association to Genero
	//@ManyToOne
	//@JsonIgnore
	//@JoinColumn(name="idgenero")
	//private Genero genero;

	//bi-directional many-to-many association to Keyword
	//@ManyToMany(mappedBy="artistas")
	//@JsonIgnore
	//private List<Keyword> keywords;

	//bi-directional many-to-one association to Artista
	//@OneToMany(mappedBy="artista")
	//@JsonIgnore
	//private List<Pais> paises;

	public Political() {
	}

	public int getIdPolitical() {
		return this.idPolitical;
	}

	public void setIdPolitical(int idPolitical) {
		this.idPolitical = idPolitical;
	}

	public double getComentariosNegativos() {
		return this.comentariosNegativos;
	}

	public void setComentariosNegativos(double comentariosNegativos) {
		this.comentariosNegativos = comentariosNegativos;
	}

	public double getComentariosPositivos() {
		return this.comentariosPositivos;
	}

	public void setComentariosPositivos(double comentariosPositivos) {
		this.comentariosPositivos = comentariosPositivos;
	}

	public int getComentariosNeutros() {
		return comentariosNeutros;
	}

	public void setComentariosNeutros(int comentariosNeutros) {
		this.comentariosNeutros = comentariosNeutros;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/*public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Genero getGenero() {
		return this.genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public List<Keyword> getKeywords() {
		return this.keywords;
	}

	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}

	public List<Pais> getPaises() {
		return paises;
	}

	public void setPaises(List<Pais> paises) {
		this.paises = paises;
	}*/
}