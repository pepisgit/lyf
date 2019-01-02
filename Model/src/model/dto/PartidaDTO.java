package model.dto;

import java.util.List;

import javax.faces.model.SelectItem;

import model.entities.Jugador;
import model.entities.Partida;

public class PartidaDTO 
{
    private Partida partida;
    private JugadorDTO jugadorBlancas;
    private JugadorDTO jugadorNegras;
    
    private List<SelectItem> jugadores;

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setJugadores(List<SelectItem> jugadores) {
        this.jugadores = jugadores;
    }

    public List<SelectItem> getJugadores() {
        return jugadores;
    }

    public void setJugadorBlancas(JugadorDTO jugadorBlancas)
    {
        this.jugadorBlancas = jugadorBlancas;
    }

    public JugadorDTO getJugadorBlancas()
    {
        return jugadorBlancas;
    }

    public void setJugadorNegras(JugadorDTO jugadorNegras)
    {
        this.jugadorNegras = jugadorNegras;
    }

    public JugadorDTO getJugadorNegras()
    {
        return jugadorNegras;
    }
}
