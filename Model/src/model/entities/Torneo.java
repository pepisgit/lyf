package model.entities;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import model.dto.JugadorDTO;

@SqlResultSetMapping(name="torneosMap", 
  entities={ 
    @EntityResult(entityClass=Torneo.class)},
  columns = {
    @ColumnResult(name = "sistema"),
    @ColumnResult(name = "tipotorneo")
      }
)

@Entity
@Table(name = "\"torneos\"")
public class Torneo
    implements Serializable
{
    @Id
    @Column(name = "tor_id", nullable = false)
    private Long tor_id;

    @Column(name = "tor_nombre", nullable = false)
    private String tor_nombre;
    @Column(name = "tor_arbitro")
    private String tor_arbitro;
    @Temporal(TemporalType.DATE)
    @Column(name = "tor_fecha_inicio")
    private Date tor_fecha_inicio;
    @Column(name = "tor_organizador")
    private String tor_organizador;
    @Column(name = "tor_lugar")
    private String tor_lugar;
    @Column(name = "tor_tipo")
    private Long tor_tipo;
    @Column(name = "tor_rondas")
    private Long tor_rondas;
    @Column(name = "tor_sistema")
    private Long tor_sistema;
    @Column(name = "tor_ritmo_juego")
    private Long tor_ritmo_juego;
    @Column(name = "tor_categoria_ascenso")
    private Long tor_categoria_ascenso;
    @Column(name = "tor_equipos")
    private String tor_equipos;
    @Column(name = "tor_partidas_rondas")
    private Long tor_partidas_rondas;
    @Column(name = "tor_premios")
    private String tor_premios;
    @Column(name = "tor_rankeado")
    private String tor_rankeado;
    @Column(name = "tor_software_usado")
    private Long tor_software_usado;
    @Column(name = "tor_jug_clasificado")
    private Long tor_jug_clasificado;
    @Column(name = "tor_comentarios")
    private String tor_comentarios;
    @Column(name = "tor_bn_ok")
    private String tor_bn_ok;
    @Column(name = "tor_chess_results_id")
    private String tor_chess_results_id;
    @Column(name = "tor_estado")
    private Long tor_estado;
    
    @Transient
    private boolean torbnok;
    
    //@OneToMany (fetch = FetchType.LAZY, mappedBy = "torneo", cascade = CascadeType.ALL)
    @Transient
    private List<Ronda> rondas;
    
    @Transient
    private List<JugadorTorneo> jugadoresTorneo;
    
    @Transient
    private String sistema;
    @Transient    
    private String tipoTorneo;
    @Transient    
    private Long maxRonId;
    
    public Torneo()
    {
    }

    public Torneo(String tor_arbitro, String tor_comentarios, String tor_equipos, Date tor_fecha_inicio, Long tor_id,
                   String tor_lugar, String tor_nombre, String tor_organizador, Long tor_partidas_rondas,
                   String tor_premios, String tor_rankeado, Long tor_rondas, Long tor_sistema, Long tor_software_usado)
    {
        this.tor_arbitro = tor_arbitro;
        this.tor_comentarios = tor_comentarios;
        this.tor_equipos = tor_equipos;
        this.tor_fecha_inicio = tor_fecha_inicio;
        this.tor_id = tor_id;
        this.tor_lugar = tor_lugar;
        this.tor_nombre = tor_nombre;
        this.tor_organizador = tor_organizador;
        this.tor_partidas_rondas = tor_partidas_rondas;
        this.tor_premios = tor_premios;
        this.tor_rankeado = tor_rankeado;
        this.tor_rondas = tor_rondas;
        this.tor_sistema = tor_sistema;
        this.tor_software_usado = tor_software_usado;
    }

    public boolean existeJugador(Long jugId)
    {
        boolean ret = false;
        
        if (this.getJugadoresTorneo() != null)
        for (JugadorTorneo j: this.getJugadoresTorneo())
        {
            if (j.getJpt_jug_id().equals(jugId))
            {
                ret = true;
                break;
            }
        }
        
        return ret;
    }
    
    public void agregarJugador(JugadorTorneo jugador)
    {
        if (this.getJugadoresTorneo() == null)
            this.setJugadoresTorneo(new ArrayList<JugadorTorneo >());

        this.getJugadoresTorneo().add(jugador);
    }
    
    public void agregarRonda(Ronda ronda)
    {
        if (this.getRondas() == null)
            this.setRondas(new ArrayList<Ronda>());

        this.getRondas().add(ronda);
    }

    public void setTor_arbitro(String tor_arbitro) {
        this.tor_arbitro = tor_arbitro;
    }

    public String getTor_arbitro() {
        return tor_arbitro;
    }

    public void setTor_comentarios(String tor_comentarios) {
        this.tor_comentarios = tor_comentarios;
    }

    public String getTor_comentarios() {
        return tor_comentarios;
    }

    public void setTor_equipos(String tor_equipos) {
        this.tor_equipos = tor_equipos;
    }

    public String getTor_equipos() {
        return tor_equipos;
    }

    public void setTor_fecha_inicio(Date tor_fecha_inicio) {
        this.tor_fecha_inicio = tor_fecha_inicio;
    }

    public Date getTor_fecha_inicio() {
        return tor_fecha_inicio;
    }

    public void setTor_id(Long tor_id) {
        this.tor_id = tor_id;
    }

    public Long getTor_id() {
        return tor_id;
    }

    public void setTor_lugar(String tor_lugar) {
        this.tor_lugar = tor_lugar;
    }

    public String getTor_lugar() {
        return tor_lugar;
    }

    public void setTor_nombre(String tor_nombre) {
        this.tor_nombre = tor_nombre;
    }

    public String getTor_nombre() {
        return tor_nombre;
    }

    public void setTor_organizador(String tor_organizador) {
        this.tor_organizador = tor_organizador;
    }

    public String getTor_organizador() {
        return tor_organizador;
    }

    public void setTor_partidas_rondas(Long tor_partidas_rondas) {
        this.tor_partidas_rondas = tor_partidas_rondas;
    }

    public Long getTor_partidas_rondas() {
        return tor_partidas_rondas;
    }

    public void setTor_premios(String tor_premios) {
        this.tor_premios = tor_premios;
    }

    public String getTor_premios() {
        return tor_premios;
    }

    public void setTor_rankeado(String tor_rankeado) {
        this.tor_rankeado = tor_rankeado;
    }

    public String getTor_rankeado() {
        return tor_rankeado;
    }

    public void setTor_rondas(Long tor_rondas) {
        this.tor_rondas = tor_rondas;
    }

    public Long getTor_rondas() {
        return tor_rondas;
    }

    public void setTor_sistema(Long tor_sistema) {
        this.tor_sistema = tor_sistema;
    }

    public Long getTor_sistema() {
        return tor_sistema;
    }

    public void setTor_software_usado(Long tor_software_usado) {
        this.tor_software_usado = tor_software_usado;
    }

    public Long getTor_software_usado() {
        return tor_software_usado;
    }

    
    public void setRondas(List<Ronda> rondas)
    {
        this.rondas = rondas;
    }

    public List<Ronda> getRondas()
    {
        return rondas;
    }

    public void setSistema(String sistema)
    {
        this.sistema = sistema;
    }

    public String getSistema()
    {
        return sistema;
    }

    public void setTor_tipo(Long tor_tipo)
    {
        this.tor_tipo = tor_tipo;
    }

    public Long getTor_tipo()
    {
        return tor_tipo;
    }

    public void setTipoTorneo(String tipoTorneo)
    {
        this.tipoTorneo = tipoTorneo;
    }

    public String getTipoTorneo()
    {
        return tipoTorneo;
    }

    public void setTor_jug_clasificado(Long tor_jug_clasificado) {
        this.tor_jug_clasificado = tor_jug_clasificado;
    }

    public Long getTor_jug_clasificado() {
        return tor_jug_clasificado;
    }

    public void setJugadoresTorneo(List<JugadorTorneo> jugadoresTorneo)
    {
        this.jugadoresTorneo = jugadoresTorneo;
    }

    public List<JugadorTorneo> getJugadoresTorneo()
    {
        return jugadoresTorneo;
    }

    public void setTor_bn_ok(String tor_bn_ok)
    {
        this.tor_bn_ok = tor_bn_ok;
    }

    public String getTor_bn_ok()
    {
        return tor_bn_ok;
    }

    public void setTorbnok(boolean torbnok)
    {
        this.torbnok = torbnok;
    }

    public boolean isTorbnok()
    {
        return torbnok;
    }

    public void setMaxRonId(Long maxRonId)
    {
        this.maxRonId = maxRonId;
    }

    public Long getMaxRonId()
    {
        return maxRonId;
    }

    public void setTor_ritmo_juego(Long tor_ritmo_juego) {
        this.tor_ritmo_juego = tor_ritmo_juego;
    }

    public Long getTor_ritmo_juego() {
        return tor_ritmo_juego;
    }

    public void setTor_categoria_ascenso(Long tor_categoria_ascenso) {
        this.tor_categoria_ascenso = tor_categoria_ascenso;
    }

    public Long getTor_categoria_ascenso() {
        return tor_categoria_ascenso;
    }


    public void setTor_chess_results_id(String tor_chess_results_id) {
        this.tor_chess_results_id = tor_chess_results_id;
    }

    public String getTor_chess_results_id() {
        return tor_chess_results_id;
    }

    public void setTor_estado(Long tor_estado) {
        this.tor_estado = tor_estado;
    }

    public Long getTor_estado() {
        return tor_estado;
    }
}
