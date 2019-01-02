package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import javax.persistence.Transient;

import model.dto.JugadorDTO;

@SqlResultSetMapping(name="jugadoresTorneoMapping", 
  entities={ 
    @EntityResult(entityClass=JugadorTorneo.class)},
  columns = {
    @ColumnResult (name = "APELLIDO"),
    @ColumnResult (name = "NOMBRE")
  }
)

@Entity
@Table(name = "\"jugadores_torneos\"")
@IdClass(JugadorTorneoPK.class)
public class JugadorTorneo
    implements Serializable
{
    @Id
    @Column(name = "jpt_tor_id", nullable = false)
    private Long jpt_tor_id;
    @Id
    @Column(name = "jpt_jug_id", nullable = false)
    private Long jpt_jug_id;
    @Column(name = "jpt_posicion_final")
    private Integer jpt_posicion_final;
    @Column(name = "jpt_puntaje_final")
    private Double jpt_puntaje_final;
    
    @Column(name = "jpt_swp_id")
    private Long jpt_swp_id;     // Id utilizado en SwissPerfect o SwissManager
    
    @Column(name = "jpt_clasifica")
    private String jpt_clasifica;
    
    //@ManyToOne (fetch = FetchType.LAZY)
    //@JoinColumn (name="jpt_jug_id", insertable = false, updatable = false)
    @Transient
    private Jugador jugador;
    
    public JugadorTorneo()
    {
    }
    
    public JugadorTorneo(JugadorDTO jug, Long idTorneo)
    {
        this.jpt_jug_id = jug.getJugId();
        this.jpt_posicion_final = jug.getPosicion();
        this.jpt_puntaje_final = jug.getPuntaje(); 
        this.jpt_tor_id = idTorneo;
        this.jpt_clasifica = (jug.isClasifica()? "S": "N");
        this.jpt_swp_id = jug.getSwpId();
    }

    public JugadorTorneo(Long jpt_tor_id, Long jpt_jug_id, Integer jpt_posicion_final, Double jpt_puntaje_final, Long jpt_swp_id)
    {
        this.jpt_tor_id = jpt_tor_id;
        this.jpt_jug_id = jpt_jug_id;
        this.jpt_posicion_final = jpt_posicion_final;
        this.jpt_puntaje_final = jpt_puntaje_final;
        this.jpt_swp_id = jpt_swp_id;
    }

    public void setJpt_jug_id(Long jpt_jug_id) {
        this.jpt_jug_id = jpt_jug_id;
    }

    public Long getJpt_jug_id() {
        return jpt_jug_id;
    }

    public void setJpt_tor_id(Long jpt_tor_id) {
        this.jpt_tor_id = jpt_tor_id;
    }

    public Long getJpt_tor_id() {
        return jpt_tor_id;
    }

    public void setJpt_puntaje_final(Double jpt_puntaje_final)
    {
        this.jpt_puntaje_final = jpt_puntaje_final;
    }

    public Double getJpt_puntaje_final()
    {
        return jpt_puntaje_final;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJpt_swp_id(Long jpt_swp_id) {
        this.jpt_swp_id = jpt_swp_id;
    }

    public Long getJpt_swp_id() {
        return jpt_swp_id;
    }

    public void setJpt_clasifica(String jpt_clasifica)
    {
        this.jpt_clasifica = jpt_clasifica;
    }

    public String getJpt_clasifica()
    {
        return jpt_clasifica;
    }

    public void setJpt_posicion_final(Integer jpt_posicion_final)
    {
        this.jpt_posicion_final = jpt_posicion_final;
    }

    public Integer getJpt_posicion_final()
    {
        return jpt_posicion_final;
    }
}
