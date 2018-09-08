package cl.usach.gobierno.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ManyToAny;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "politicos")

public class Political implements Serializable {

    private static final long serialVersionUID = 1L;

    public Political(){}

    public Political(Political political){
        this.idpoliticos = political.idpoliticos;
        this.nombre = political.nombre;
        this.cargo = political.cargo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpoliticos",unique = true,nullable = false)
    private int idpoliticos;

    @Column(name="nombre")
    private String nombre;

    @Column(name="cargo")
    private String cargo;

    @Column(name="compositivos")
    private int compositivos;

    @Column(name="comnegativos")
    private int comnegativos;

    @Column(name="comneutros")
    private int comneutros;

    @Column(name="fechainicio")
    private Date fechainicio;

    @Column(name="fechatermino")
    private Date fechatermino;


    //bi-directional many-to-one association to Usuario
    @OneToMany(mappedBy="political")
    @JsonIgnore
    private List<Region> region;


    public Integer getId() {
        return idpoliticos;
    }

    public void setId(Integer id) {
        this.idpoliticos = id;
    }
  /*
    public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
*/
    public String getNombre(){return nombre;}

    public void setNombre(String nombre){this.nombre = nombre;}

    public String getCargo(){return cargo;}

    public void setCargo(String cargo){this.cargo = cargo;}

    public int getComnegativos(){return comnegativos;}

    public void setComnegativos(int comnegativos){this.comnegativos = comnegativos;}

    public int getCompositivos(){return compositivos;}

    public void setCompositivos(int compositivos){this.compositivos = compositivos;}

    public int getComneutros(){return comneutros;}

    public void setComneutros(int comneutros){this.comneutros = comneutros;}

    public Date getFechainicio(){
        return this.fechainicio;
    }

    public void setFechainicio(Date fechainicio){
        this.fechainicio = fechainicio;
    }

    public Date getFechatermino(){
        return this.fechatermino;
    }

    public void setFechatermino(Date fechatermino){
        this.fechatermino = fechatermino;
    }

    public List<Region> getRegion(){
        return region;
    }

    public void setRegion(List<Region> region){
        this.region = region;
    }
}
