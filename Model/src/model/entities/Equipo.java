package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "\"equipos\"")
@IdClass(EquipoPK.class)
public class Equipo
    implements Serializable
{
    @Id
    @Column(name = "equ_id", nullable = false)
    private Long equ_id;
    @Column(name = "equ_nombre", nullable = false)
    private String equ_nombre;
    @Id
    @Column(name = "equ_tor_id", nullable = false)
    private Long equ_tor_id;
    @Column(name = "equ_posicion_final", nullable = true)
    private Long equ_posicion_final;

    public Equipo()
    {
    }

    public Equipo(Long equ_id, String equ_nombre, Long equ_tor_id, Long equ_posicion_final)
    {
        this.equ_id = equ_id;
        this.equ_nombre = equ_nombre;
        this.equ_tor_id = equ_tor_id;
        this.equ_posicion_final = equ_posicion_final;
    }

    public String getEqu_nombre()
    {
        return equ_nombre;
    }

    public void setEqu_nombre(String equ_nombre)
    {
        this.equ_nombre = equ_nombre;
    }


    public void setEqu_id(Long equ_id) {
        this.equ_id = equ_id;
    }

    public Long getEqu_id() {
        return equ_id;
    }

    public void setEqu_tor_id(Long equ_tor_id) {
        this.equ_tor_id = equ_tor_id;
    }

    public Long getEqu_tor_id() {
        return equ_tor_id;
    }

    public void setEqu_posicion_final(Long equ_posicion_final) {
        this.equ_posicion_final = equ_posicion_final;
    }

    public Long getEqu_posicion_final() {
        return equ_posicion_final;
    }
}
