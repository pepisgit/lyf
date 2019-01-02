package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;

@SqlResultSetMapping
(
  name = "premiosTorneoMapping", 
  entities = { 
    @EntityResult(entityClass=PremioTorneo.class)},
  columns = {
    @ColumnResult(name = "APELLIDO"),
    @ColumnResult(name = "NOMBRE")}
)

@Entity
@Table(name = "\"premios_torneos\"")
@IdClass(PremioTorneoPK.class)
public class PremioTorneo implements Serializable {
    @Column(name = "pre_descripcion", nullable = false)
    private String pre_descripcion;
    @Id
    @Column(name = "pre_id", nullable = false)
    private Integer pre_id;
    @Column(name = "pre_jug_id", nullable = false)
    private Integer pre_jug_id;
    @Id
    @Column(name = "pre_tor_id", nullable = false)
    private Integer pre_tor_id;
    @Column(name = "pre_premio")
    private String pre_premio;
    
    @Transient
    private String apellido;
    
    @Transient   
    private String nombre;
    
    public PremioTorneo() {
    }

    public PremioTorneo(String pre_descripcion, Integer pre_id, Integer pre_jug_id, Integer pre_tor_id) {
        this.pre_descripcion = pre_descripcion;
        this.pre_id = pre_id;
        this.pre_jug_id = pre_jug_id;
        this.pre_tor_id = pre_tor_id;
    }

    public String getPre_descripcion() {
        return pre_descripcion;
    }

    public void setPre_descripcion(String pre_descripcion) {
        this.pre_descripcion = pre_descripcion;
    }

    public void setPre_id(Integer pre_id) {
        this.pre_id = pre_id;
    }

    public Integer getPre_id() {
        return pre_id;
    }

    public void setPre_jug_id(Integer pre_jug_id) {
        this.pre_jug_id = pre_jug_id;
    }

    public Integer getPre_jug_id() {
        return pre_jug_id;
    }

    public void setPre_tor_id(Integer pre_tor_id) {
        this.pre_tor_id = pre_tor_id;
    }

    public Integer getPre_tor_id() {
        return pre_tor_id;
    }

    public void setPre_premio(String pre_premio)
    {
        this.pre_premio = pre_premio;
    }

    public String getPre_premio()
    {
        return pre_premio;
    }

    public void setApellido(String apellido)
    {
        this.apellido = apellido;
    }

    public String getApellido()
    {
        return apellido;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getNombre()
    {
        return nombre;
    }
}
