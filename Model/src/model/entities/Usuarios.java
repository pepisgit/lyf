package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@SqlResultSetMapping (name = "UsuariosMapping",
                      
                      entities = {@EntityResult(entityClass = Usuarios.class),
                                  @EntityResult(entityClass = Socios.class),
                                  @EntityResult(entityClass = Roles.class),
                                  @EntityResult(entityClass = Persona.class)}
                      )

@NamedQueries( { @NamedQuery(name = "Usuarios.findAll", query = "select o from Usuarios o") })
@Table(name = "\"usuarios\"")
public class Usuarios implements Serializable {
    @Id
    @Column(name = "usr_id", nullable = false)
    private Long usr_id;
    @Column(name = "usr_nombre", nullable = false)
    private String usr_nombre;
    @Column(name = "usr_pass", nullable = false)
    private String usr_pass;
    @Column(name = "usr_soc_id")
    private Long usr_soc_id;
    
    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "usr_soc_id")
    @Transient
    private Socios socio;
    
    @Transient
    private Roles rol;
    
    @Transient
    private Persona persona;
    
    
    

    public Usuarios() {
    }

    public Usuarios(Long usr_id, String usr_nombre, String usr_pass, Long usr_soc_id) {
        this.usr_id = usr_id;
        this.usr_nombre = usr_nombre;
        this.usr_pass = usr_pass;
        this.usr_soc_id = usr_soc_id;
    }

    public String getUsr_nombre() {
        return usr_nombre;
    }

    public void setUsr_nombre(String usr_nombre) {
        this.usr_nombre = usr_nombre;
    }

    public String getUsr_pass() {
        return usr_pass;
    }

    public void setUsr_pass(String usr_pass) {
        this.usr_pass = usr_pass;
    }

    public void setSocio(Socios socio) {
        this.socio = socio;
    }

    public Socios getSocio() {
        return socio;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Roles getRol() {
        return rol;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setUsr_id(Long usr_id) {
        this.usr_id = usr_id;
    }

    public Long getUsr_id() {
        return usr_id;
    }

    public void setUsr_soc_id(Long usr_soc_id) {
        this.usr_soc_id = usr_soc_id;
    }

    public Long getUsr_soc_id() {
        return usr_soc_id;
    }
}
