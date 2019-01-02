package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "Roles.findAll", query = "select o from Roles o") })
@Table(name = "\"roles\"")
public class Roles implements Serializable {
    @Column(name = "rol_descripcion", nullable = false)
    private String rol_descripcion;
    @Id
    @Column(name = "rol_id", nullable = false)
    private Long rol_id;
    @Column(name = "rol_observaciones")
    private String rol_observaciones;

    public Roles() {
    }

    public Roles(String rol_descripcion, Long rol_id, String rol_observaciones) {
        this.rol_descripcion = rol_descripcion;
        this.rol_id = rol_id;
        this.rol_observaciones = rol_observaciones;
    }

    public String getRol_descripcion() {
        return rol_descripcion;
    }

    public void setRol_descripcion(String rol_descripcion) {
        this.rol_descripcion = rol_descripcion;
    }

    public String getRol_observaciones() {
        return rol_observaciones;
    }

    public void setRol_observaciones(String rol_observaciones) {
        this.rol_observaciones = rol_observaciones;
    }

    public void setRol_id(Long rol_id) {
        this.rol_id = rol_id;
    }

    public Long getRol_id() {
        return rol_id;
    }
}
