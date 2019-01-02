package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "\"jugadores_equipos\"")
@IdClass(JugadorEquipoPK.class)
public class JugadorEquipo
    implements Serializable
{
    @Column(name = "jxe_capitan")
    private String jxe_capitan;
    @Column(name = "jxe_condicion")
    private Long jxe_condicion;
    @Id
    @Column(name = "jxe_equ_id", nullable = false)
    private Long jxe_equ_id;
    @Id
    @Column(name = "jxe_jug_id", nullable = false)
    private Long jxe_jug_id;
    @Column(name = "jxe_orden_tablero")
    private Long jxe_orden_tablero;
    @Id
    @Column(name = "jxe_tor_id", nullable = false)
    private Long jxe_tor_id;

    public JugadorEquipo()
    {
    }

    public JugadorEquipo(String jxe_capitan, Long jxe_condicion, Long jxe_equ_id, Long jxe_jug_id,
                            Long jxe_orden_tablero, Long jxe_tor_id)
    {
        this.jxe_capitan = jxe_capitan;
        this.jxe_condicion = jxe_condicion;
        this.jxe_equ_id = jxe_equ_id;
        this.jxe_jug_id = jxe_jug_id;
        this.jxe_orden_tablero = jxe_orden_tablero;
        this.jxe_tor_id = jxe_tor_id;
    }

    public String getJxe_capitan()
    {
        return jxe_capitan;
    }

    public void setJxe_capitan(String jxe_capitan)
    {
        this.jxe_capitan = jxe_capitan;
    }

    public void setJxe_condicion(Long jxe_condicion) {
        this.jxe_condicion = jxe_condicion;
    }

    public Long getJxe_condicion() {
        return jxe_condicion;
    }

    public void setJxe_equ_id(Long jxe_equ_id) {
        this.jxe_equ_id = jxe_equ_id;
    }

    public Long getJxe_equ_id() {
        return jxe_equ_id;
    }

    public void setJxe_jug_id(Long jxe_jug_id) {
        this.jxe_jug_id = jxe_jug_id;
    }

    public Long getJxe_jug_id() {
        return jxe_jug_id;
    }

    public void setJxe_orden_tablero(Long jxe_orden_tablero) {
        this.jxe_orden_tablero = jxe_orden_tablero;
    }

    public Long getJxe_orden_tablero() {
        return jxe_orden_tablero;
    }

    public void setJxe_tor_id(Long jxe_tor_id) {
        this.jxe_tor_id = jxe_tor_id;
    }

    public Long getJxe_tor_id() {
        return jxe_tor_id;
    }
}
