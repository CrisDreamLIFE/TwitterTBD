package cl.usach.gobierno.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import java.io.Serializable;

@Entity
@Table(name="user")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int idusuario;

    private String apellido;

    private String email;

    private String nombre;

    private String password;

    private String username;

    //bi-directional many-to-one association to Political
    @OneToMany(mappedBy="usuario")
    @JsonIgnore
    private List<Political> politicals;

    public Usuario() {
    }

    public int getIdusuario() {
        return this.idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Political> getPoliticals() {
        return this.politicals;
    }

    public void setPoliticals(List<Political> politicals) {
        this.politicals = politicals;
    }

    public Political addPolitical(Political political) {
        getPoliticals().add(political);
        political.setUsuario(this);

        return political;
    }

    public Political removePolitical(Political political) {
        getPoliticals().remove(political);
        political.setUsuario(null);

        return political;
    }

}