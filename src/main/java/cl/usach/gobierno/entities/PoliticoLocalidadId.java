package cl.usach.gobierno.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PoliticoLocalidadId implements Serializable {

    @Column(name = "idpoliticos")
    private int idpoliticos;

    @Column(name = "idlocalidad")
    private int idlocalidad;

    private PoliticoLocalidadId() {}

    public PoliticoLocalidadId(int idpoliticos, int idlocalidad) {
        this.idpoliticos = idpoliticos;
        this.idlocalidad = idlocalidad;
    }

    public int getIdpoliticos() {
        return idpoliticos;
    }

    public void setIdpoliticos(int idpoliticos) {
        this.idpoliticos = idpoliticos;
    }

    public int getIdlocalidad() {
        return idlocalidad;
    }

    public void setIdlocalidad(int idlocalidad) {
        this.idlocalidad = idlocalidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        PoliticoLocalidadId that = (PoliticoLocalidadId) o;
        return Objects.equals(idpoliticos, that.idpoliticos) && Objects.equals(idlocalidad, that.idlocalidad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idpoliticos, idlocalidad);
    }
}
