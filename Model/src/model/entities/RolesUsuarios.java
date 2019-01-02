package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "RolesUsuarios.findAll", query = "select o from RolesUsuarios o") })
@Table(name = "\"roles_usuarios\"")
public class RolesUsuarios implements Serializable {
    @Id
    @Column(name = "rolusr_id", nullable = false)
    private Long rolusr_id;
    @Column(name = "rolusr_rol", nullable = false)
    private Long rolusr_rol;
    @Column(name = "rolusr_usr", nullable = false)
    private Long rolusr_usr;

    public RolesUsuarios() {
    }

    public RolesUsuarios(Long rolusr_id, Long rolusr_rol, Long rolusr_usr) {
        this.rolusr_id = rolusr_id;
        this.rolusr_rol = rolusr_rol;
        this.rolusr_usr = rolusr_usr;
    }

    public Long getRolusr_id() {
        return rolusr_id;
    }


    public void setRolusr_id(Long rolusr_id) {
        this.rolusr_id = rolusr_id;
    }

    public void setRolusr_rol(Long rolusr_rol) {
        this.rolusr_rol = rolusr_rol;
    }

    public Long getRolusr_rol() {
        return rolusr_rol;
    }

    public void setRolusr_usr(Long rolusr_usr) {
        this.rolusr_usr = rolusr_usr;
    }

    public Long getRolusr_usr() {
        return rolusr_usr;
    }
}
