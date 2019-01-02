package model.dto;

import java.util.ArrayList;
import java.util.List;

public class TorneoDTO
{
    private String nombreTorneo;
    
    private List <JugadorDTO> listaJugadores;
    private List <RondaDTO> listaRondas;

    
    public void agregarRonda(RondaDTO r)
    {
        if (listaRondas == null) 
            listaRondas = new ArrayList<RondaDTO>();
        listaRondas.add(r);
    }
    
    public void setListaJugadores(List<JugadorDTO> listaJugadores) {
        this.listaJugadores = listaJugadores;
    }

    public List<JugadorDTO> getListaJugadores() {
        return listaJugadores;
    }

    public void setListaRondas(List<RondaDTO> listaRondas) {
        this.listaRondas = listaRondas;
    }

    public List<RondaDTO> getListaRondas() {
        return listaRondas;
    }

    public void setNombreTorneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }
}
