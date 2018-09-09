package cl.usach.gobierno.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "politico_localidad")
public class PoliticoLocalidad {

    @EmbeddedId
    private PoliticoLocalidadId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idpoliticos")
    private Political political;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idlocalidad")
    private Localidad localidad;

    @Column(name="compositivos")
    private int compositivos;

    @Column(name="comnegativos")
    private int comnegativos;

    @Column(name="comneutros")
    private int comneutros;

    private PoliticoLocalidad() {}

    public PoliticoLocalidad(Political political, Localidad localidad) {
        this.political = political;
        this.political = political;
        this.id = new PoliticoLocalidadId(political.getId(), localidad.getId());
    }

    // GETTERS Y SETTERS

    public PoliticoLocalidadId getId() {
        return id;
    }

    public void setId(PoliticoLocalidadId id) {
        this.id = id;
    }

    public Political getPolitical() {
        return political;
    }

    public void setPolitical(Political political) {
        this.political = political;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public int getCompositivos() {
        return compositivos;
    }

    public void setCompositivos(int compositivos) {
        this.compositivos = compositivos;
    }

    public int getComnegativos() {
        return comnegativos;
    }

    public void setComnegativos(int comnegativos) {
        this.comnegativos = comnegativos;
    }

    public int getComneutros() {
        return comneutros;
    }

    public void setComneutros(int comneutros) {
        this.comneutros = comneutros;
    }

    // FIN GETS/SETS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PoliticoLocalidad that = (PoliticoLocalidad) o;
        return Objects.equals(political, that.political) && Objects.equals(localidad, that.localidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(political, localidad);
    }
}
