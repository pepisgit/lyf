package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "\"tiebreak_torneos\"")
@IdClass(TiebreakTorneoPK.class)
public class TiebreakTorneo
    implements Serializable
{
    @Column(name = "tpt_orden", nullable = false)
    private Long tpt_orden;
    @Id
    @Column(name = "tpt_tor_id", nullable = false)
    private Long tpt_tor_id;
    @Id
    @Column(name = "tpt_ttb_id", nullable = false)
    private Long tpt_ttb_id;

    public TiebreakTorneo()
    {
    }

    public TiebreakTorneo(Long tpt_orden, Long tpt_tor_id, Long tpt_ttb_id)
    {
        this.tpt_orden = tpt_orden;
        this.tpt_tor_id = tpt_tor_id;
        this.tpt_ttb_id = tpt_ttb_id;
    }

    public void setTpt_orden(Long tpt_orden) {
        this.tpt_orden = tpt_orden;
    }

    public Long getTpt_orden() {
        return tpt_orden;
    }

    public void setTpt_tor_id(Long tpt_tor_id) {
        this.tpt_tor_id = tpt_tor_id;
    }

    public Long getTpt_tor_id() {
        return tpt_tor_id;
    }

    public void setTpt_ttb_id(Long tpt_ttb_id) {
        this.tpt_ttb_id = tpt_ttb_id;
    }

    public Long getTpt_ttb_id() {
        return tpt_ttb_id;
    }
}
