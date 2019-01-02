package model.entities;

import java.io.Serializable;

import java.util.Date;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@SqlResultSetMapping(name="grauMapping", 
  entities={ 
    @EntityResult(entityClass=GrauRanking.class)
  },
  columns = {
    @ColumnResult (name = "cant_torneos")
  }
)

@Entity
@Table(name = "\"grau_ranking\"")
public class GrauRanking
    implements Serializable
{
    @Id
    @Column(name = "rkg_id", nullable = false)
    private int rkg_id;

    @Temporal(TemporalType.DATE)
    @Column(name = "rkg_fecha", nullable = false)
    private Date rkg_fecha;
    
    @Transient
    private List<Torneo> listaTorneos;
    @Transient
    private List<Jugador> listaJugadores;
    
    @Transient
    private String strFecha;
    @Transient
    private Long cantidadTorneos;
    @Transient
    private String archivoActivos;
    @Transient
    private String archivoGral;
    @Transient
    private String archivoNuevos;
    @Transient
    private String archivoVariacionesSumadas;
    

    public GrauRanking()
    {
    }

    public GrauRanking(Date rkg_fecha, int rkg_id)
    {
        this.rkg_fecha = rkg_fecha;
        this.rkg_id = rkg_id;
    }

    public Date getRkg_fecha()
    {
        return rkg_fecha;
    }

    public void setRkg_fecha(Date rkg_fecha)
    {
        this.rkg_fecha = rkg_fecha;
    }

    public int getRkg_id()
    {
        return rkg_id;
    }

    public void setRkg_id(int rkg_id)
    {
        this.rkg_id = rkg_id;
    }

    public void setListaTorneos(List<Torneo> listaTorneos)
    {
        this.listaTorneos = listaTorneos;
    }

    public List<Torneo> getListaTorneos()
    {
        return listaTorneos;
    }

    public void setListaJugadores(List<Jugador> listaJugadores)
    {
        this.listaJugadores = listaJugadores;
    }

    public List<Jugador> getListaJugadores()
    {
        return listaJugadores;
    }

    public void setArchivoActivos(String archivoActivos) {
        this.archivoActivos = archivoActivos;
    }

    public String getArchivoActivos() {
        return archivoActivos;
    }

    public void setArchivoGral(String archivoGral) {
        this.archivoGral = archivoGral;
    }

    public String getArchivoGral() {
        return archivoGral;
    }

    public void setArchivoNuevos(String archivoNuevos) {
        this.archivoNuevos = archivoNuevos;
    }

    public String getArchivoNuevos() {
        return archivoNuevos;
    }

    public void setArchivoVariacionesSumadas(String archivoVariacionesSumadas) {
        this.archivoVariacionesSumadas = archivoVariacionesSumadas;
    }

    public String getArchivoVariacionesSumadas() {
        return archivoVariacionesSumadas;
    }

    public void setCantidadTorneos(Long cantidadTorneos) {
        this.cantidadTorneos = cantidadTorneos;
    }

    public Long getCantidadTorneos() {
        return cantidadTorneos;
    }

    public void setStrFecha(String strFecha) {
        this.strFecha = strFecha;
    }

    public String getStrFecha() {
        return strFecha;
    }
}
