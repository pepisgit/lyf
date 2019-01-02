package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Transient;

@SqlResultSetMappings({
@SqlResultSetMapping(name="jugadoresMapping", 
  entities={ 
    @EntityResult(entityClass=Jugador.class)
  },
  columns = {
    @ColumnResult (name = "APELLIDO"),
    @ColumnResult (name = "NOMBRE"),
    @ColumnResult (name = "PUNTAJE"),
    @ColumnResult (name = "CLASIFICA"),
    @ColumnResult (name = "POSICION")
  }
),

@SqlResultSetMapping(name="jugadoresMapping2", 
  entities={ 
    @EntityResult(entityClass=Jugador.class)
  },
  columns = {
    @ColumnResult (name = "APELLIDO"),
    @ColumnResult (name = "NOMBRE")
  }
)

})

@Entity
@Table(name = "\"jugadores\"")
public class Jugador
    implements Serializable
{
    @Column(name = "jug_elo_actual")
    private Long jug_elo_actual;
    @Id
    @Column(name = "jug_id", nullable = false)
    private Long jug_id;
    @Column(name = "jug_titulo")
    private String jug_titulo;
    @Column(name = "per_id", nullable = false)
    private Long per_id;
    @Column(name = "soc_id")
    private Long soc_id;

    @Column(name = "jug_estado_elo")
    private Long jug_estado_elo;
    @Column(name = "jug_partidas_grau")
    private Long jug_partidas_grau;
    @Column(name = "jug_coeficiente_k")
    private Long jug_coeficiente_k;
    @Column(name = "jug_activo")
    private Long jug_activo;
    
    @Transient
    private Integer categoria;
    
    @Transient
    private String backgroundColor;

    @Transient
    private String jugApellido;
    
    @Transient    
    private String jugNombre;

    public Jugador()
    {
        this.jug_estado_elo = 0L;
        this.jug_coeficiente_k = 0L;
        this.jug_partidas_grau = 0L;
        this.jug_elo_actual = 0L;
    }

    public Jugador(Long jug_elo_actual, Long jug_id, String jug_titulo, Long per_id, Long soc_id)
    {
        this.jug_elo_actual = jug_elo_actual;
        this.jug_id = jug_id;
        this.jug_titulo = jug_titulo;
        this.per_id = per_id;
        this.soc_id = soc_id;
    }
    
    
    public String getApellidoNombre()
    {
        return jugApellido + (jugNombre != null? ", " + jugNombre: "");
    }

    public void setJug_elo_actual(Long jug_elo_actual) {
        this.jug_elo_actual = jug_elo_actual;
    }

    public Long getJug_elo_actual() {
        return jug_elo_actual;
    }

    public void setJug_id(Long jug_id) {
        this.jug_id = jug_id;
    }

    public Long getJug_id() {
        return jug_id;
    }

    public void setJug_titulo(String jug_titulo) {
        this.jug_titulo = jug_titulo;
    }

    public String getJug_titulo() {
        return jug_titulo;
    }

    public void setPer_id(Long per_id) {
        this.per_id = per_id;
    }

    public Long getPer_id() {
        return per_id;
    }

    public void setSoc_id(Long soc_id) {
        this.soc_id = soc_id;
    }

    public Long getSoc_id() {
        return soc_id;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setJugApellido(String jugApellido)
    {
        this.jugApellido = jugApellido;
    }

    public String getJugApellido()
    {
        return jugApellido;
    }

    public void setJugNombre(String jugNombre)
    {
        this.jugNombre = jugNombre;
    }

    public String getJugNombre()
    {
        return jugNombre;
    }

    public void setJug_estado_elo(Long jug_estado_elo) {
        this.jug_estado_elo = jug_estado_elo;
    }

    public Long getJug_estado_elo() {
        return jug_estado_elo;
    }

    public void setJug_partidas_grau(Long jug_partidas_grau) {
        this.jug_partidas_grau = jug_partidas_grau;
    }

    public Long getJug_partidas_grau() {
        return jug_partidas_grau;
    }

    public void setJug_coeficiente_k(Long jug_coeficiente_k) {
        this.jug_coeficiente_k = jug_coeficiente_k;
    }

    public Long getJug_coeficiente_k() {
        return jug_coeficiente_k;
    }

    public void setJug_activo(Long jug_activo) {
        this.jug_activo = jug_activo;
    }

    public Long getJug_activo() {
        return jug_activo;
    }
}
