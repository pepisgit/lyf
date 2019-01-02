package model.entities;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries( { @NamedQuery(name = "Socios.findAll", query = "select o from Socios o") })
@Table(name = "\"socios\"")
public class Socios implements Serializable {
    @Temporal(TemporalType.DATE)
    @Column(name = "soc_fecha_ingreso")
    private Date soc_fecha_ingreso;
    @Id
    @Column(name = "soc_id", nullable = false)
    private Long soc_id;
    @Column(name = "soc_per_id", nullable = false)
    private Long soc_per_id;

    public Socios() {
    }

    public Socios(Date soc_fecha_ingreso, Long soc_id, Long soc_per_id) {
        this.soc_fecha_ingreso = soc_fecha_ingreso;
        this.soc_id = soc_id;
        this.soc_per_id = soc_per_id;
    }

    public Date getSoc_fecha_ingreso() {
        return soc_fecha_ingreso;
    }

    public void setSoc_fecha_ingreso(Date soc_fecha_ingreso) {
        this.soc_fecha_ingreso = soc_fecha_ingreso;
    }


    public void setSoc_id(Long soc_id) {
        this.soc_id = soc_id;
    }

    public Long getSoc_id() {
        return soc_id;
    }

    public void setSoc_per_id(Long soc_per_id) {
        this.soc_per_id = soc_per_id;
    }

    public Long getSoc_per_id() {
        return soc_per_id;
    }
}
