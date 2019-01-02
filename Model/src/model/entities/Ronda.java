package model.entities;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import javax.persistence.SqlResultSetMapping;

import model.dto.RondaDTO;

@SqlResultSetMapping(name="rondaMap", 
  entities={ 
    @EntityResult(entityClass=Ronda.class)}
)

@Entity
@Table(name = "rondas")
@IdClass(RondaPK.class)
public class Ronda implements Serializable
{
    @Id
    @Column(name = "ron_id", nullable = false)
    private Long ron_id;
    @Id
    @Column(name = "ron_tor_id", nullable = false)
    private Long ron_tor_id;
    @Column(name = "ron_numero", nullable = false)
    private Long ron_numero;
    @Temporal(TemporalType.DATE)
    @Column(name = "ron_fecha")
    private Date ron_fecha;
    @Column(name = "ron_reloj")
    private String ron_reloj;
    @Column(name = "ron_tipo", nullable = false)
    private Long ron_tipo;

    //@ManyToOne 
    //@JoinColumn (name="ron_tor_id", insertable = false, updatable = false)    
    @Transient
    private Torneo torneo;
    
    //@OneToMany (fetch = FetchType.EAGER, mappedBy = "ronda", cascade = CascadeType.ALL)
    @Transient
    private List<Partida> partidas;
    
    @Transient 
    private Long numeroRonda;
    @Transient 
    private String descReloj;
    @Transient
    private boolean nueva;
    @Transient
    private Long maxParId;

    public Ronda()
    {
    }

    public Ronda(Date ron_fecha, Long ron_id, String ron_reloj, Long ron_tipo, Long ron_tor_id)
    {
        this.ron_fecha = ron_fecha;
        this.ron_id = ron_id;
        this.ron_reloj = ron_reloj;
        this.ron_tipo = ron_tipo;
        this.ron_tor_id = ron_tor_id;
    }
    
    public Ronda (RondaDTO rDTO)
    {
        this.ron_tor_id = rDTO.getRonda().getRon_tor_id();
        this.ron_numero = rDTO.getRonda().getRon_numero();
        this.ron_fecha = rDTO.getRonda().getRon_fecha();
        this.ron_reloj = rDTO.getRonda().getRon_reloj();
        this.ron_tipo = rDTO.getRonda().getRon_tipo();
    }

    public void agregarPartida(Partida partida)
    {
        if (this.getPartidas() == null)
            this.setPartidas(new ArrayList<Partida>());
        
        this.getPartidas().add(partida);
    }

    public Date getRon_fecha()
    {
        return ron_fecha;
    }

    public void setRon_fecha(Date ron_fecha)
    {
        this.ron_fecha = ron_fecha;
    }

    public Long getRon_id()
    {
        return ron_id;
    }

    public String getRon_reloj()
    {
        return ron_reloj;
    }

    public void setRon_reloj(String ron_reloj)
    {
        this.ron_reloj = ron_reloj;
    }

    public Long getRon_tipo()
    {
        return ron_tipo;
    }

    public Long getRon_tor_id()
    {
        return ron_tor_id;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void setRon_id(Long ron_id) {
        this.ron_id = ron_id;
    }

    public void setRon_tipo(Long ron_tipo) {
        this.ron_tipo = ron_tipo;
    }

    public void setRon_tor_id(Long ron_tor_id) {
        this.ron_tor_id = ron_tor_id;
    }
    
    public void setTorneo(Torneo torneo)
    {
        this.torneo = torneo;
    }

    public Torneo getTorneo()
    {
        return torneo;
    }

    public void setNumeroRonda(Long numeroRonda)
    {
        this.numeroRonda = numeroRonda;
    }

    public Long getNumeroRonda()
    {
        return numeroRonda;
    }

    public void setDescReloj(String descReloj)
    {
        this.descReloj = descReloj;
    }

    public String getDescReloj()
    {
        if (this.ron_reloj == null)
        {
            return "Sin Datos.";
        }
        else
        {
            //String etapas[] = this.ron_reloj.split("[;]");
            return descReloj;
        }
        
    }

    public void setRon_numero(Long ron_numero)
    {
        this.ron_numero = ron_numero;
    }

    public Long getRon_numero()
    {
        return ron_numero;
    }

    public void setNueva(boolean nueva)
    {
        this.nueva = nueva;
    }

    public boolean isNueva()
    {
        return nueva;
    }

    public void setMaxParId(Long maxParId)
    {
        this.maxParId = maxParId;
    }

    public Long getMaxParId()
    {
        return maxParId;
    }
}
