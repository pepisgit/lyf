package model.entities;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import model.dto.PartidaDTO;

@SqlResultSetMapping(name="partidaMap", 
  entities={ 
    @EntityResult(entityClass=Partida.class)}
)
@Entity
@Table(name = "\"partidas\"")
public class Partida
    implements Serializable
{
    @Column(name = "par_desc_torneo")
    private String par_desc_torneo;
    @Column(name = "par_elo_blancas")
    private Long par_elo_blancas;
    @Column(name = "par_elo_negras")
    private Long par_elo_negras;
    @Column(name = "par_equ_id")
    private Long par_equ_id;
    @Temporal(TemporalType.DATE)
    @Column(name = "par_fecha")
    private Date par_fecha;
    @Id
    @Column(name = "par_id", nullable = false)
    private Long par_id;
    @Column(name = "par_jug_blancas")
    private Long par_jug_blancas;
    @Column(name = "par_jug_negras")
    private Long par_jug_negras;
    @Column(name = "par_lugar")
    private String par_lugar;
    @Column(name = "par_observaciones")
    private String par_observaciones;
    @Column(name = "par_pgn")
    private String par_pgn;

    @Column(name = "par_resultado_blancas", nullable = false)
    private Long par_resultado_blancas;

    @Column(name = "par_resultado_negras", nullable = false)
    private Long par_resultado_negras;

    @Column(name = "par_tipo_res_blancas", nullable = false)
    private Long par_tipo_res_blancas;

    @Column(name = "par_tipo_res_negras", nullable = false)
    private Long par_tipo_res_negras;


    @Column(name = "par_ron_id")
    private Long par_ron_id;
    @Column(name = "par_tor_id")
    private Long par_tor_id;
    
    /*
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumns ({
        @JoinColumn (name="par_ron_id", referencedColumnName = "ron_id", insertable = false, updatable = false),
        @JoinColumn (name="par_tor_id", referencedColumnName = "ron_tor_id",insertable = false, updatable = false)
        }
    )
    */
    @Transient
    private Ronda ronda;
    
    //@ManyToOne (fetch = FetchType.LAZY)
    //@JoinColumn (name="par_jug_blancas", insertable = false, updatable = false)
    @Transient
    private Jugador jugadorBlancas;
    
    //@ManyToOne (fetch = FetchType.LAZY)
    //@JoinColumn (name="par_jug_negras", insertable = false, updatable = false)
    @Transient
    private Jugador jugadorNegras;
    
    @Transient
    private String nombreJugadorBlancas;
    @Transient
    private String nombreJugadorNegras;   
    @Transient
    private String strResultBlancas;
    @Transient
    private String strResultNegras;

    public Partida()
    {
    }

    public Partida(Long par_id,
                   Long par_tor_id,
                   Long par_ron_id, 
                   Long par_equ_id, 
                   Date par_fecha,
                   Long par_jug_blancas, 
                   Long par_jug_negras, 
                   Long par_resultado_blancas, 
                   Long par_resultado_negras, 
                   Long par_tipo_res_blancas, 
                   Long par_tipo_res_negras,                    
                   Long par_elo_blancas, 
                   Long par_elo_negras, 
                   String par_lugar, 
                   String par_observaciones,
                   String par_pgn, 
                   String par_desc_torneo)
    {
        this.par_desc_torneo = par_desc_torneo;
        this.par_elo_blancas = par_elo_blancas;
        this.par_elo_negras = par_elo_negras;
        this.par_equ_id = par_equ_id;
        this.par_fecha = par_fecha;
        this.par_id = par_id;
        this.par_jug_blancas = par_jug_blancas;
        this.par_jug_negras = par_jug_negras;
        this.par_lugar = par_lugar;
        this.par_observaciones = par_observaciones;
        this.par_pgn = par_pgn;
        this.par_ron_id = par_ron_id;
        this.par_tor_id = par_tor_id;
        this.par_resultado_blancas = par_resultado_blancas;
        this.par_resultado_negras = par_resultado_negras;
        this.par_tipo_res_blancas = par_tipo_res_blancas;
        this.par_tipo_res_negras = par_tipo_res_negras;
    }
    
    public Partida(Partida par)
    {
        this.par_desc_torneo = par.getPar_desc_torneo();
        this.par_elo_blancas = par.getPar_elo_blancas();
        this.par_elo_negras = par.getPar_elo_negras();
        this.par_equ_id = par.getPar_equ_id();
        this.par_fecha = par.getPar_fecha();
        this.par_id = par.getPar_id();
        this.par_jug_blancas = par.getPar_jug_blancas();
        this.par_jug_negras = par.getPar_jug_negras();
        this.par_lugar = par.getPar_lugar();
        this.par_observaciones = par.getPar_observaciones();
        this.par_pgn = par.getPar_pgn();
        this.par_ron_id = par.getPar_ron_id();
        this.par_tor_id = par.getPar_tor_id();
        this.par_resultado_blancas = par.getPar_resultado_blancas();
        this.par_resultado_negras = par.getPar_resultado_negras();
        this.par_tipo_res_blancas = par.getPar_tipo_res_blancas();
        this.par_tipo_res_negras = par.getPar_tipo_res_negras();
    }    
    
    public Partida (Ronda r)
    {
        this.par_id = null;
        this.par_ron_id = r.getRon_id();
        this.par_tor_id = r.getRon_tor_id();
        this.par_equ_id = null;
        this.par_fecha = r.getRon_fecha();
        this.par_jug_blancas = null;
        this.par_jug_negras = null;
        this.par_resultado_blancas = null;
        this.par_resultado_negras = null;
        this.par_tipo_res_blancas = null;
        this.par_tipo_res_negras = null;
    }

    public void setPar_desc_torneo(String par_desc_torneo) {
        this.par_desc_torneo = par_desc_torneo;
    }

    public String getPar_desc_torneo() {
        return par_desc_torneo;
    }

    public void setPar_elo_blancas(Long par_elo_blancas) {
        this.par_elo_blancas = par_elo_blancas;
    }

    public Long getPar_elo_blancas() {
        return par_elo_blancas;
    }

    public void setPar_elo_negras(Long par_elo_negras) {
        this.par_elo_negras = par_elo_negras;
    }

    public Long getPar_elo_negras() {
        return par_elo_negras;
    }

    public void setPar_equ_id(Long par_equ_id) {
        this.par_equ_id = par_equ_id;
    }

    public Long getPar_equ_id() {
        return par_equ_id;
    }

    public void setPar_fecha(Date par_fecha) {
        this.par_fecha = par_fecha;
    }

    public Date getPar_fecha() {
        return par_fecha;
    }

    public void setPar_id(Long par_id) {
        this.par_id = par_id;
    }

    public Long getPar_id() {
        return par_id;
    }

    public void setPar_jug_blancas(Long par_jug_blancas) {
        this.par_jug_blancas = par_jug_blancas;
    }

    public Long getPar_jug_blancas() {
        return par_jug_blancas;
    }

    public void setPar_jug_negras(Long par_jug_negras) {
        this.par_jug_negras = par_jug_negras;
    }

    public Long getPar_jug_negras() {
        return par_jug_negras;
    }

    public void setPar_lugar(String par_lugar) {
        this.par_lugar = par_lugar;
    }

    public String getPar_lugar() {
        return par_lugar;
    }

    public void setPar_observaciones(String par_observaciones) {
        this.par_observaciones = par_observaciones;
    }

    public String getPar_observaciones() {
        return par_observaciones;
    }

    public void setPar_pgn(String par_pgn) {
        this.par_pgn = par_pgn;
    }

    public String getPar_pgn() {
        return par_pgn;
    }

    public void setPar_ron_id(Long par_ron_id) {
        this.par_ron_id = par_ron_id;
    }

    public Long getPar_ron_id() {
        return par_ron_id;
    }

    public void setPar_tor_id(Long par_tor_id) {
        this.par_tor_id = par_tor_id;
    }

    public Long getPar_tor_id() {
        return par_tor_id;
    }

    public void setJugadorBlancas(Jugador jugadorBlancas) {
        this.jugadorBlancas = jugadorBlancas;
    }

    public Jugador getJugadorBlancas() {
        return jugadorBlancas;
    }

    public void setJugadorNegras(Jugador jugadorNegras) {
        this.jugadorNegras = jugadorNegras;
    }

    public Jugador getJugadorNegras() {
        return jugadorNegras;
    }

    public void setPar_resultado_blancas(Long par_resultado_blancas)
    {
        this.par_resultado_blancas = par_resultado_blancas;
    }

    public Long getPar_resultado_blancas()
    {
        return par_resultado_blancas;
    }

    public void setPar_resultado_negras(Long par_resultado_negras)
    {
        this.par_resultado_negras = par_resultado_negras;
    }

    public Long getPar_resultado_negras()
    {
        return par_resultado_negras;
    }

    public void setPar_tipo_res_blancas(Long par_tipo_res_blancas)
    {
        this.par_tipo_res_blancas = par_tipo_res_blancas;
    }

    public Long getPar_tipo_res_blancas()
    {
        return par_tipo_res_blancas;
    }

    public void setPar_tipo_res_negras(Long par_tipo_res_negras)
    {
        this.par_tipo_res_negras = par_tipo_res_negras;
    }

    public Long getPar_tipo_res_negras()
    {
        return par_tipo_res_negras;
    }

    public void setRonda(Ronda ronda)
    {
        this.ronda = ronda;
    }

    public Ronda getRonda()
    {
        return ronda;
    }

    public void setNombreJugadorBlancas(String nombreJugadorBlancas)
    {
        this.nombreJugadorBlancas = nombreJugadorBlancas;
    }
    
    public String getNombreJugadorBlancas()
    {
        String ret = "?";
        
        if (this.nombreJugadorBlancas != null)
            ret = this.nombreJugadorBlancas;
        else if (getJugadorBlancas() != null)
            ret = getJugadorBlancas().getJugApellido() + ", " + getJugadorBlancas().getJugNombre();
        else if (this.getPar_jug_blancas() != null && this.getPar_jug_blancas().equals(Long.valueOf(0L)))
            ret = "BYE";
        
        return ret;
    }

    public void setNombreJugadorNegras(String nombreJugadorNegras)
    {
        this.nombreJugadorNegras = nombreJugadorNegras;
    }

    public String getNombreJugadorNegras()
    {
        String ret = "?";
        
        if (this.nombreJugadorNegras != null)
            ret = this.nombreJugadorNegras;
        else if (getJugadorNegras() != null)
            ret = getJugadorNegras().getJugApellido() + ", " + getJugadorNegras().getJugNombre();
        else if (this.getPar_jug_negras() != null && this.getPar_jug_negras().equals(Long.valueOf(0L)))
            ret = "BYE";
        
        return ret;
    }

    public void setStrResultBlancas(String strResultBlancas) {
        this.strResultBlancas = strResultBlancas;
    }

    public String getStrResultBlancas()
    {
        String ret = "";
        
        if (this.getPar_tipo_res_blancas()== null && this.getPar_resultado_blancas() == null)
            return ret;
        
        if (this.strResultBlancas != null)
            ret = strResultBlancas;
        else        
            if (this.getPar_tipo_res_blancas().equals(Long.valueOf(1L)) || this.getPar_tipo_res_blancas().equals(Long.valueOf(3L)))
            {
                switch (this.getPar_resultado_blancas().intValue())
                {
                case 1: ret = "1"; break;
                case 0: ret = "0"; break;
                case 2: ret = "1/2";
                }
                strResultBlancas = ret;
            }
            else if (this.getPar_tipo_res_blancas().equals(Long.valueOf(2L)))
            {
                switch (this.getPar_resultado_blancas().intValue())
                {
                case 1: ret = "+"; break;
                case 0: ret = "-"; break;
                }
                strResultBlancas = ret;
            }
        return ret;
    }

    public void setStrResultNegras(String strResultNegras) 
    {
        this.strResultNegras = strResultNegras;
    }

    public String getStrResultNegras()
    {
        String ret = "";
        
        if (this.getPar_tipo_res_negras()== null && this.getPar_resultado_negras() == null)
            return ret;
        
        if (this.strResultNegras != null)
            ret = strResultNegras;
        else
            if (this.getPar_tipo_res_negras().equals(Long.valueOf(1L)) || this.getPar_tipo_res_negras().equals(Long.valueOf(3L)))
            {
                switch (this.getPar_resultado_negras().intValue())
                {
                case 1: ret = "1"; break;
                case 0: ret = "0"; break;
                case 2: ret = "1/2";
                }
                strResultNegras = ret;
            }
            else if (this.getPar_tipo_res_negras().equals(Long.valueOf(2L)))
            {
                switch (this.getPar_resultado_negras().intValue())
                {
                case 1: ret = "+"; break;
                case 0: ret = "-"; break;
                }
                strResultNegras = ret;
            }
        return ret;
    }
}
