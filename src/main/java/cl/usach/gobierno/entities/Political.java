package cl.usach.gobierno.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

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
    private Double compositivos;

    @Column(name="comnegativos")
    private Double comnegativos;

    @Column(name="comneutros")
    private Double comneutros;

    @Column(name="fechainicio")
    private Date fechainicio;

    @Column(name="fechatermino")
    private Date fechatermino;

    /*
    //bi-directional many-to-one association to Usuario
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="idusuario")
    private Usuario usuario;
    */
    
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

    public Double getComnegativos(){return comnegativos;}

    public void setComnegativos(Double comnegativos){this.comnegativos = comnegativos;}

    public Double getCompositivos(){return compositivos;}

    public void setCompositivos(Double compositivos){this.compositivos = compositivos;}

    public Double getComneutros(){return comneutros;}

    public void setComneutros(Double comneutros){this.comneutros = comneutros;}

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
}
