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
@Table(name = "\"resultados_tiebreaks\"")
@IdClass(ResultadoTiebreakPK.class)
public class ResultadoTiebreak
    implements Serializable
{
    @Id
    @Column(name = "rtb_jug_id", nullable = false)
    private Long rtb_jug_id;
    @Id
    @Column(name = "rtb_tor_id", nullable = false)
    private Long rtb_tor_id;
    @Id
    @Column(name = "rtb_ttb_id", nullable = false)
    private Long rtb_ttb_id;
    @Column(name = "rtb_valor")
    private double rtb_valor;

    public ResultadoTiebreak()
    {
    }

    public ResultadoTiebreak(Long rtb_jug_id, Long rtb_tor_id, Long rtb_ttb_id, double rtb_valor)
    {
        this.rtb_jug_id = rtb_jug_id;
        this.rtb_tor_id = rtb_tor_id;
        this.rtb_ttb_id = rtb_ttb_id;
        this.rtb_valor = rtb_valor;
    }


    public void setRtb_jug_id(Long rtb_jug_id) {
        this.rtb_jug_id = rtb_jug_id;
    }

    public Long getRtb_jug_id() {
        return rtb_jug_id;
    }

    public void setRtb_tor_id(Long rtb_tor_id) {
        this.rtb_tor_id = rtb_tor_id;
    }

    public Long getRtb_tor_id() {
        return rtb_tor_id;
    }

    public void setRtb_ttb_id(Long rtb_ttb_id) {
        this.rtb_ttb_id = rtb_ttb_id;
    }

    public Long getRtb_ttb_id() {
        return rtb_ttb_id;
    }

    public void setRtb_valor(double rtb_valor) {
        this.rtb_valor = rtb_valor;
    }

    public double getRtb_valor() {
        return rtb_valor;
    }
}
