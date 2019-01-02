package model.dto;

import model.entities.JugadorTorneo;

public class JugadorTorneoDTO
{
    private Long torId;
    private Long jugId;
    private Integer posicionFinal;
    private Double puntajeFinal;
    private Long swpId;             // Id utilizado en SwissPerfect
    private String clasifica;
    
    private String jugApellido;
    private String jugNombre;

    public JugadorTorneoDTO (JugadorTorneo jug)
    {
        this.torId = jug.getJpt_tor_id();
        this.jugId = jug.getJpt_jug_id();
        this.posicionFinal = jug.getJpt_posicion_final();
        this.puntajeFinal = jug.getJpt_puntaje_final();
        this.swpId = jug.getJpt_swp_id();
        this.clasifica = jug.getJpt_clasifica();
    }

    public void setTorId(Long torId)
    {
        this.torId = torId;
    }

    public Long getTorId()
    {
        return torId;
    }

    public void setJugId(Long jugId)
    {
        this.jugId = jugId;
    }

    public Long getJugId()
    {
        return jugId;
    }

    public void setPosicionFinal(Integer posicionFinal)
    {
        this.posicionFinal = posicionFinal;
    }

    public Integer getPosicionFinal()
    {
        return posicionFinal;
    }

    public void setPuntajeFinal(Double puntajeFinal)
    {
        this.puntajeFinal = puntajeFinal;
    }

    public Double getPuntajeFinal()
    {
        return puntajeFinal;
    }

    public void setSwpId(Long swpId)
    {
        this.swpId = swpId;
    }

    public Long getSwpId()
    {
        return swpId;
    }

    public void setClasifica(String clasifica)
    {
        this.clasifica = clasifica;
    }

    public String getClasifica()
    {
        return clasifica;
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
}
