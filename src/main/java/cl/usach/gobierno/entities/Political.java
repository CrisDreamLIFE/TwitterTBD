package cl.usach.gobierno.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ManyToAny;

import java.io.Serializable;
import java.util.*;

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


    //bi-directional many-to-many association to Localidad
    @OneToMany(mappedBy="political", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PoliticoLocalidad> localidades = new ArrayList<>();


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

    public List<PoliticoLocalidad> getLocalidades(){
        return localidades;
    }

    public void setLocalidades(List<PoliticoLocalidad> localidades){
        this.localidades = localidades;
    }

    public void addLocalidad(Localidad localidad) {
        PoliticoLocalidad politicalLocalidad = new PoliticoLocalidad(this, localidad);
        localidades.add(politicalLocalidad);
        localidad.getPoliticals().add(politicalLocalidad);
    }

    public void removeLocalidad(Localidad localidad) {
        for (Iterator<PoliticoLocalidad> iterator = localidades.iterator();
             iterator.hasNext(); ) {
            PoliticoLocalidad politicalLocalidad = iterator.next();

            if (politicalLocalidad.getPolitical().equals(this) &&
                    politicalLocalidad.getLocalidad().equals(localidad)) {
                iterator.remove();
                politicalLocalidad.getLocalidad().getPoliticals().remove(politicalLocalidad);
                politicalLocalidad.setPolitical(null);
                politicalLocalidad.setLocalidad(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Political political = (Political) o;
        return Objects.equals(nombre, political.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}
