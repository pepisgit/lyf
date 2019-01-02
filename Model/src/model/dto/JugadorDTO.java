package model.dto;

import java.io.Serializable;

import javax.persistence.Transient;

import model.entities.Jugador;

public class JugadorDTO implements Comparable, Serializable
{
    private Long jugId;
    private Long perId;
    private Long swpId; // Id de Swss perfect
    private String titulo;
    private String federacion;
    
    private Integer posicion;
    private Double puntaje;
    
    private Long orden;
    private String jugApellido;
    private String jugNombre;
    private Long grauActual;

    private Integer categoria;
    private String backgroundColor;
    
    // Para ChessResults
    private String jugSistemaNombre;
    private Long jugSistemaId;
    private Long jugCRId;
    private JugadorDTO jugadorSistema;
    
    private boolean clasifica;
    
    public JugadorDTO()
    {
        
    }
    
    public int compareTo(Object obj)
    {
        JugadorDTO j = (JugadorDTO)obj;
        int lastCmp = this.getApellidoNombre().compareTo(j.getApellidoNombre());
        return lastCmp;
    }
    
    public JugadorDTO(Jugador obj)
    {
        this.jugId = obj.getJug_id() != null? new Long(obj.getJug_id()): null;
        this.perId = obj.getPer_id() != null?new Long(obj.getPer_id()): null;
        this.titulo = obj.getJug_titulo() != null? new String(obj.getJug_titulo()): null;
        this.grauActual = obj.getJug_elo_actual() != null? new Long(obj.getJug_elo_actual()): null;
        this.categoria = obj.getCategoria() != null? new Integer(obj.getCategoria()): null;
        this.backgroundColor = obj.getBackgroundColor() != null? new String(obj.getBackgroundColor()): null;
    }
    
    public JugadorDTO(JugadorDTO obj)
    {
        this.jugId = obj.getJugId();
        this.perId = obj.getPerId();
        this.swpId = obj.getSwpId();
        this.jugCRId = obj.getJugCRId();
        this.posicion = obj.getPosicion();
        this.puntaje = obj.getPuntaje();
        this.jugApellido = obj.getJugApellido();
        this.jugNombre = obj.getJugNombre();
        this.grauActual = obj.getGrauActual();
        this.clasifica = obj.isClasifica();
        this.categoria = obj.getCategoria();
        this.backgroundColor = obj.getBackgroundColor();
    }

    public String getApellidoNombre()
    {
        return jugApellido + (jugNombre != null? ", " + jugNombre: "");
    }
    
    public String getApellidoNombresNorm()
    {
        return normalizarCaracteres(getApellidoNombre());
    }

    private String normalizarCaracteres(String input)
    {
        if (input==null) return null;
        
        // Cadena de caracteres original a sustituir.
        String original = "·‡‰ÈËÎÌÏÔÛÚˆ˙˘uÒ¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹—Á«";
        // Cadena de caracteres ASCII que reemplazar·n los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public void setJugId(Long jugId)
    {
        this.jugId = jugId;
    }

    public Long getJugId()
    {
        return jugId;
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

    public void setPerId(Long perId)
    {
        this.perId = perId;
    }

    public Long getPerId()
    {
        return perId;
    }

    public void setGrauActual(Long grauActual)
    {
        this.grauActual = grauActual;
    }

    public Long getGrauActual()
    {
        return grauActual;
    }

    public void setOrden(Long orden)
    {
        this.orden = orden;
    }

    public Long getOrden()
    {
        return orden;
    }

    public void setPuntaje(Double puntaje)
    {
        this.puntaje = puntaje;
    }

    public Double getPuntaje()
    {
        return puntaje;
    }

    public void setClasifica(boolean clasifica)
    {
        this.clasifica = clasifica;
    }

    public boolean isClasifica()
    {
        return clasifica;
    }

    public void setPosicion(Integer posicion)
    {
        this.posicion = posicion;
    }

    public Integer getPosicion()
    {
        return posicion;
    }

    public void setSwpId(Long swpId)
    {
        this.swpId = swpId;
    }

    public Long getSwpId()
    {
        return swpId;
    }

    public void setCategoria(Integer categoria)
    {
        this.categoria = categoria;
    }

    public Integer getCategoria()
    {
        return categoria;
    }

    public void setBackgroundColor(String backgroundColor)
    {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundColor()
    {
        return backgroundColor;
    }

    public void setTitulo(String titulo)
    {
        this.titulo = titulo;
    }

    public String getTitulo()
    {
        return titulo;
    }

    public void setFederacion(String federacion) {
        this.federacion = federacion;
    }

    public String getFederacion() {
        return federacion;
    }

    public void setJugSistemaNombre(String jugSistemaNombre) {
        this.jugSistemaNombre = jugSistemaNombre;
    }

    public String getJugSistemaNombre() {
        return jugSistemaNombre;
    }

    public void setJugSistemaId(Long jugSistemaId) {
        this.jugSistemaId = jugSistemaId;
    }

    public Long getJugSistemaId() {
        return jugSistemaId;
    }

    public void setJugadorSistema(JugadorDTO jugadorSistema) {
        this.jugadorSistema = jugadorSistema;
    }

    public JugadorDTO getJugadorSistema() {
        return jugadorSistema;
    }

    public void setJugCRId(Long jugCRId) {
        this.jugCRId = jugCRId;
    }

    public Long getJugCRId() {
        return jugCRId;
    }
}
