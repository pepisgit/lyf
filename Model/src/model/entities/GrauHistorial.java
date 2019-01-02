package model.entities;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "grau_historial")
@IdClass(GrauHistorialPK.class)
public class GrauHistorial
    implements Serializable
{
    @Id
    @Column(name = "his_rkg_id", nullable = false)
    private Long his_rkg_id;
    @Id
    @Column(name = "his_jug_id", nullable = false)
    private Long his_jug_id;

    @Column(name = "his_puntaje", nullable = false)
    private Long his_puntaje;
    @Column(name = "his_variacion", nullable = false)
    private Long his_variacion;
    @Column(name = "his_categoria", nullable = false)
    private Long his_categoria;
    @Column(name = "his_categoria_anterior", nullable = false)
    private Long his_categoria_anterior;
    
    @Transient
    private Date rkg_fecha;
    @Transient
    private Boolean participo; // Participo en algún torneo para el ranking dado

    public GrauHistorial()
    {
    }
    
    public String getFechaFormateada()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        return sdf.format(this.rkg_fecha);
    }

    public void setHis_jug_id(Long his_jug_id) {
        this.his_jug_id = his_jug_id;
    }

    public Long getHis_jug_id() {
        return his_jug_id;
    }

    public void setHis_puntaje(Long his_puntaje) {
        this.his_puntaje = his_puntaje;
    }

    public Long getHis_puntaje() {
        return his_puntaje;
    }

    public void setHis_rkg_id(Long his_rkg_id) {
        this.his_rkg_id = his_rkg_id;
    }

    public Long getHis_rkg_id() {
        return his_rkg_id;
    }

    public void setHis_variacion(Long his_variacion) {
        this.his_variacion = his_variacion;
    }

    public Long getHis_variacion() {
        return his_variacion;
    }

    public void setHis_categoria(Long his_categoria) {
        this.his_categoria = his_categoria;
    }

    public Long getHis_categoria() {
        return his_categoria;
    }

    public void setHis_categoria_anterior(Long his_categoria_anterior) {
        this.his_categoria_anterior = his_categoria_anterior;
    }

    public Long getHis_categoria_anterior() {
        return his_categoria_anterior;
    }

    public void setRkg_fecha(Date rkg_fecha) {
        this.rkg_fecha = rkg_fecha;
    }

    public Date getRkg_fecha() {
        return rkg_fecha;
    }

    public void setParticipo(Boolean participo) {
        this.participo = participo;
    }

    public Boolean getParticipo() {
        return participo;
    }
}
