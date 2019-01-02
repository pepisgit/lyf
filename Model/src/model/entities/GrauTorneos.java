package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "\"grau_torneos\"")
@IdClass(GrauTorneosPK.class)
public class GrauTorneos
    implements Serializable
{
    @Id
    @Column(name = "gtr_rkg_id", nullable = false)
    private int gtr_rkg_id;
    @Column(name = "gtr_tipo_calculo", nullable = false)
    private int gtr_tipo_calculo;
    @Id
    @Column(name = "gtr_tor_id", nullable = false)
    private int gtr_tor_id;

    public GrauTorneos()
    {
    }

    public GrauTorneos(int gtr_rkg_id, int gtr_tipo_calculo, int gtr_tor_id)
    {
        this.gtr_rkg_id = gtr_rkg_id;
        this.gtr_tipo_calculo = gtr_tipo_calculo;
        this.gtr_tor_id = gtr_tor_id;
    }

    public int getGtr_rkg_id()
    {
        return gtr_rkg_id;
    }

    public void setGtr_rkg_id(int gtr_rkg_id)
    {
        this.gtr_rkg_id = gtr_rkg_id;
    }

    public int getGtr_tipo_calculo()
    {
        return gtr_tipo_calculo;
    }

    public void setGtr_tipo_calculo(int gtr_tipo_calculo)
    {
        this.gtr_tipo_calculo = gtr_tipo_calculo;
    }

    public int getGtr_tor_id()
    {
        return gtr_tor_id;
    }

    public void setGtr_tor_id(int gtr_tor_id)
    {
        this.gtr_tor_id = gtr_tor_id;
    }
}
