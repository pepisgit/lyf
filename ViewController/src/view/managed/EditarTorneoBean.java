package view.managed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import model.dto.JugadorDTO;

import model.dto.PartidaDTO;

import model.entities.Jugador;
import model.entities.JugadorTorneo;
import model.entities.Partida;
import model.entities.Ronda;
import model.entities.Torneo;

import model.dto.RondaDTO;
import model.dto.TorneoDTO;

public class EditarTorneoBean
{
    private Long idTorneo; // Id pasado por parametro
    private Torneo torneo; // Torneo, rondas y partidas
    
    private List <JugadorDTO> listaAllJugadores; // Todos los jugadores disponibles
    private List <JugadorDTO> listaJugadores;    // Jugadores pertenecientes al torneo
    private List <RondaDTO> rondasdto;
    
    private List<SelectItem> listaTiposTorneo;
    private List<SelectItem> listaSistemas;
    private List<SelectItem> listaRitmos;
    private List<SelectItem> listaCatAscenso;
    
    private JugadorDTO jugadorSeleccionado;
    private JugadorDTO nuevoJugador;
    private PartidaDTO partidaSeleccionada;
    private RondaDTO rondaSeleccionada;
    
    private Integer modo;
    private String filtroJugadores;
    private Long seqRondas; // para ids provisorios de rondas nuevas
    
    private boolean procesoCalcularPuntos;
    private boolean procesoOrdenarPosiciones;
    
    // Torneo de ChessResults
    private Long idChessResults;
    private TorneoDTO torneoChessResults;
    
    public void inicializar()
    {
        this.torneo = new Torneo();
        this.torneo.setTor_fecha_inicio(new Date());
        this.torneo.setTor_organizador("Club de Ajedrez Luz y Fuerza");
        this.torneo.setTor_sistema(1L);
        this.torneo.setMaxRonId(0L);
        this.torneo.setTor_bn_ok("S");
        this.torneo.setTorbnok(true);
        
        this.nuevoJugador = new JugadorDTO();
        
        JugadorDTO bye = new JugadorDTO();
        bye.setJugApellido("BYE");
        bye.setJugNombre("");
        bye.setJugId(0L);
        bye.setPosicion(0);
        bye.setPuntaje(0D);
        
        this.listaJugadores = new ArrayList<JugadorDTO>();
        this.getListaJugadores().add(bye);
        
        this.setModo(0); // Altas
    }
    
    public void clear()
    {
        if (this.listaJugadores != null) {
            this.listaJugadores.clear();
            this.listaJugadores = null;
        }
        if (this.listaAllJugadores != null) {
            this.listaAllJugadores.clear();
            this.listaAllJugadores = null;
        }
        if (this.listaTiposTorneo != null) {
            this.listaTiposTorneo.clear();
            this.listaTiposTorneo = null;
        }
        if (this.listaSistemas != null) {
            this.listaSistemas.clear();
            this.listaSistemas = null;
        }
        if (this.listaRitmos != null) {
            this.listaRitmos.clear();
            this.listaRitmos = null;
        }
        if (this.rondasdto != null) {
            this.rondasdto.clear();
            this.rondasdto = null;
        }
        
        this.torneoChessResults = null;
        
        this.jugadorSeleccionado = null;
        this.partidaSeleccionada = null;
        this.rondaSeleccionada = null;
        this.filtroJugadores = null;
        this.nuevoJugador = new JugadorDTO();
        this.idTorneo = null;
        this.seqRondas = null;
    }
    
    // Agrega un jugador a la lista de jugadores del torneo
    public void agregarJugador()
    {
        if (listaJugadores == null)
        {
            listaJugadores = new ArrayList<JugadorDTO>();
        }
        if (jugadorSeleccionado != null)
        {
            if (jugadorSeleccionado.getPuntaje() == null)
                jugadorSeleccionado.setPuntaje(0D);
            
            listaJugadores.add(new JugadorDTO(jugadorSeleccionado));
            int indice = 0;
            for (JugadorDTO j: listaAllJugadores)
            {
                if (j.getJugId().equals(jugadorSeleccionado.getJugId()))
                {
                    listaAllJugadores.remove(j);
                    //itemsAllJugadores.remove(indice);
                    break;
                }
                indice++;
            }
        }
    }

    // Quita un jugador de la lista de jugadores del torneo
    public void quitarJugador()
    {
        if (jugadorSeleccionado != null)
        {
            listaAllJugadores.add(jugadorSeleccionado);
            
            for (JugadorDTO j: listaJugadores)
            {
                if (j.getJugId().equals(jugadorSeleccionado.getJugId()))
                {
                    listaJugadores.remove(j);
                    break;
                }
            }
            reasignarPosiciones();
        }
    }
    
    public void reasignarPosiciones()
    {
        // Reasigna posiciones
        int pos = 0;
        for (JugadorDTO jugador: this.getListaJugadores())
        {
            if (!jugador.getJugId().equals(Long.valueOf(0L)))
            {
                pos++;
                jugador.setPosicion(Integer.valueOf(pos));
            }
        }        
    }
    
    public RondaDTO agregarRonda(int partidas)
    {
        RondaDTO nuevaRonda = new RondaDTO();
        nuevaRonda.setRonda(new Ronda());
        nuevaRonda.getRonda().setRon_tipo(1L);
        
        if (rondasdto == null)
            rondasdto = new ArrayList<RondaDTO>();
        
        this.seqRondas = (this.seqRondas == null? new Long(1L): seqRondas + 1);
        
        nuevaRonda.getRonda().setRon_id(this.seqRondas * -1);
        nuevaRonda.getRonda().setRon_numero(Long.valueOf( rondasdto.size() + 1));
        nuevaRonda.getRonda().setRon_tor_id(this.getTorneo().getTor_id());
            
        if (partidas > 0)
        {
            for (int i=0; i<partidas; i++)
            {
                nuevaRonda.agregarPartida();
            }
        }
        
        rondasdto.add(nuevaRonda);
        
        return nuevaRonda;
    }
    
    public void quitarRonda(RondaDTO ronda)
    {
        for (RondaDTO r: this.getRondasdto())
        {
            if (r.getRonda().getRon_id().equals(ronda.getRonda().getRon_id()))
            {
                this.getRondasdto().remove(r);
                break;
            }
        }
        
        // Renumero rondas
        long numeroRonda = 1;
        for (RondaDTO r: this.getRondasdto())
        {
            r.getRonda().setRon_numero(numeroRonda);
            numeroRonda++;
        }
    }

    // Actualiza los datos en las entidades (Torneo -> Rondas -> Partidas) antes de guardar.
    
    public void mergeData()
    {
        // Lista de jugadores
        
        // 1 - Borra jugadores si se han eliminado
        if (this.getTorneo().getJugadoresTorneo() != null && this.getTorneo().getJugadoresTorneo().size() > 0)
        for (JugadorTorneo j: this.getTorneo().getJugadoresTorneo())
        {
            if (!existeJugador(j.getJpt_jug_id()))
            {
                this.getTorneo().getJugadoresTorneo().remove(j);
            }
        }
        
        // 2 - Inserta jugadores
        if (this.getListaJugadores() != null && this.getListaJugadores().size() > 0)
        for (JugadorDTO n: this.getListaJugadores())
        {
            if (!this.getTorneo().existeJugador(n.getJugId()))
            {
                JugadorTorneo nuevo = new JugadorTorneo(n, this.getTorneo().getTor_id());
                this.getTorneo().agregarJugador(nuevo);
            }
        }
        
        // 3 - Rondas y Partidas
        
        // Recorrer entidades de rondas y quitar las que se hayan eliminado
        if (this.getTorneo().getRondas() != null && this.getTorneo().getRondas().size() > 0)
        {
            for (Ronda r: this.getTorneo().getRondas())
            {
                if (!existeRonda(r.getRon_id()))
                {
                    this.getTorneo().getRondas().remove(r);
                }
            }
        }
        
        //   Agregar las nuevas (id < 0) (incluye todas las partidas)
        if (this.getRondasdto() != null && this.getRondasdto().size() > 0)
        {
            for (RondaDTO rDTO: this.getRondasdto())
            {
                if (rDTO.getRonda().getRon_id() < 0)
                {
                    Ronda nueva = new Ronda(rDTO);
                    nueva.setNueva(true);
                    nueva.setMaxParId(0L);
                    this.getTorneo().setMaxRonId(this.getTorneo().getMaxRonId() + 1L);
                    nueva.setRon_id(this.getTorneo().getMaxRonId());
                    this.getTorneo().agregarRonda(nueva);
                    
                    // Agregar todas las partidas
                    if (rDTO.getListaPartidas() != null && rDTO.getListaPartidas().size() > 0)
                        for (PartidaDTO partida: rDTO.getListaPartidas())
                        {
                            if (nueva.getPartidas() == null)
                                nueva.setPartidas(new ArrayList<Partida>(rDTO.getListaPartidas().size()));
                            
                            // Asigna ID
                            rDTO.getRonda().setMaxParId(rDTO.getRonda().getMaxParId() + 1L);
                            partida.getPartida().setPar_id(rDTO.getRonda().getMaxParId());
                            
                            nueva.getPartidas().add(partida.getPartida());
                        }
                }
            }
        }
        
        // Numerar rondas
        if (this.getTorneo().getRondas() != null && this.getTorneo().getRondas().size() > 0)
        {
            long ron_num = 0;
            
            for (Ronda r: this.getTorneo().getRondas())
            {
                ron_num++;
                r.setRon_numero(ron_num);
            }
        }
        
        // Recorrer entidades rondas previamente guardadas (nueva = false)
        if (this.getTorneo().getRondas() != null && this.getTorneo().getRondas().size() > 0)
        {
            for (Ronda ron: this.getTorneo().getRondas())
            {
                if (!ron.isNueva())
                {
                    // Quitar las partidas que se hayan quitado
                    if (ron.getPartidas() != null && ron.getPartidas().size() > 0)
                    for (Partida partida: ron.getPartidas())
                    {
                        if (!existePartida(ron.getRon_id(), partida.getPar_id()))
                        {
                            ron.getPartidas().remove(partida);
                        }
                    }
                    // Agregar las nuevas partidas (id < 0)
                    if (this.getRondasdto() != null && this.getRondasdto().size() > 0)
                    for (RondaDTO rdto: this.getRondasdto())
                    {
                        if (rdto.getRonda().getRon_id().equals(ron.getRon_id())) // Me ubico en la ronda
                        {
                            if (rdto.getListaPartidas() != null && rdto.getListaPartidas().size() > 0)
                            for (PartidaDTO p: rdto.getListaPartidas())
                            {
                                if (p.getPartida().getPar_id() < 0)
                                {
                                    // Asignar ID
                                    rdto.getRonda().setMaxParId(rdto.getRonda().getMaxParId() + 1L);
                                    p.getPartida().setPar_id(rdto.getRonda().getMaxParId());
                                    
                                    ron.agregarPartida(p.getPartida());
                                }
                            }
                            
                            break;
                        }
                    }
                }
            }
        }
        
    } // Fin mergeData()
    
    public boolean existePartida(Long ronId, Long parId)
    {
        boolean ret = false;
        
        if (this.getRondasdto() != null && this.getRondasdto().size() > 0)
        for (RondaDTO r: this.getRondasdto())
        {
            if (r.getRonda().getRon_id().equals(ronId))
            {
                if (r.getListaPartidas() != null && r.getListaPartidas().size() > 0)
                for (PartidaDTO p: r.getListaPartidas())
                {
                    if (p.getPartida().getPar_id().equals(parId))
                    {
                        ret = true;
                        break;
                    }
                }
            }
            if (ret) break;
        }
        
        return ret;
    }
    
    public boolean existeRonda(Long ronId)
    {
        boolean ret = false;
        
        if (this.getRondasdto() != null)
        for (RondaDTO r: this.getRondasdto())
        {
            if (r.getRonda().getRon_id().equals(ronId))
            {
                ret = true;
                break;
            }
        }
        
        return ret;
    }
    
    public boolean existeJugador(Long jugId)
    {
        boolean ret = false;
        
        if (this.getListaJugadores() != null)
        for (JugadorDTO j: this.getListaJugadores())
        {
            if (j.getJugId().equals(jugId))
            {
                ret = true;
                break;
            }
        }
        
        return ret;
    }

    private String normalizarCaracteres(String input) 
    {
        if (input==null) return null;
        
        // Cadena de caracteres original a sustituir.
        String original = "·‡‰ÈËÎÌÏÔÛÚˆ˙˘uÒ¡¿ƒ…»ÀÕÃœ”“÷⁄Ÿ‹—Á«";
        // Cadena de caracteres ASCII que reemplazar·n los originales.
        String ascii =    "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }
    
    public void moverPosicionJugador(Long idJugador, int sentido)
    {
        if (this.getListaJugadores() != null && this.getListaJugadores().size() > 1)
        {
            int indice = 0;
            for (JugadorDTO j: this.getListaJugadores())
            {
                if (j.getJugId().equals(idJugador))
                {
                    if (sentido == 1)
                    {
                        if (indice > 0)
                        {
                            Collections.rotate(getListaJugadores().subList(indice-1, indice+1), 1);
                        }
                    }
                    else
                    {
                        if (indice < this.getListaJugadores().size()-1)
                        {
                            Collections.rotate(getListaJugadores().subList(indice, indice+2), -1);
                        }
                    }
                    break;
                }
                indice++;
            }
            
            int pos = 0;
            for (JugadorDTO jugador: getListaJugadores())
            {
                pos++;
                jugador.setPosicion(Integer.valueOf(pos));
            }
        }
    }
    
    public void cargarPartidasDTO()
    {
        if (torneo != null && torneo.getRondas() != null && torneo.getRondas().size() > 0)
        {
            rondasdto = new ArrayList<RondaDTO>();
            Long maxRonId = 0L;
            Long maxParId = 0L;
            
            for (Ronda r: torneo.getRondas())
            {
                RondaDTO ron = new RondaDTO();
                r.setMaxParId(0L);
                ron.setRonda(r);
                
                if (r.getRon_id() > maxRonId)
                    maxRonId = r.getRon_id();
                
                if (r.getPartidas() != null && r.getPartidas().size() > 0)
                {
                    List listPartidas = new ArrayList<PartidaDTO>();
                    maxParId = 0L;
                    for (Partida p: r.getPartidas())
                    {
                        PartidaDTO par = new PartidaDTO();
                        par.setPartida(p);
                        if (p.getPar_id() > maxParId)
                            maxParId = p.getPar_id();
                            
                        listPartidas.add(par);
                    }
                    ron.setListaPartidas(listPartidas);
                    ron.getRonda().setMaxParId(maxParId);
                }
                
                rondasdto.add(ron);
            }
            torneo.setMaxRonId(maxRonId);
        }
    }
    
    public String getFiltroJugadoresNorm()
    {
        return normalizarCaracteres(getFiltroJugadores());
    }

    public void setIdTorneo(Long idTorneo) {
        this.idTorneo = idTorneo;
    }

    public Long getIdTorneo() {
        return idTorneo;
    }

    public void setTorneo(Torneo torneo) {
        this.torneo = torneo;
    }

    public Torneo getTorneo() {
        return torneo;
    }

    public void setListaTiposTorneo(List<SelectItem> listaTiposTorneo)
    {
        this.listaTiposTorneo = listaTiposTorneo;
    }

    public List<SelectItem> getListaTiposTorneo()
    {
        return listaTiposTorneo;
    }

    public void setListaSistemas(List<SelectItem> listaSistemas)
    {
        this.listaSistemas = listaSistemas;
    }

    public List<SelectItem> getListaSistemas()
    {
        return listaSistemas;
    }

    public void setModo(Integer modo)
    {
        this.modo = modo;
    }

    public Integer getModo()
    {
        return modo;
    }

    public void setListaJugadores(List<JugadorDTO> listaJugadores)
    {
        this.listaJugadores = listaJugadores;
    }

    public List<JugadorDTO> getListaJugadores()
    {
        return listaJugadores;
    }

    public void setJugadorSeleccionado(JugadorDTO jugadorSeleccionado)
    {
        this.jugadorSeleccionado = jugadorSeleccionado;
    }

    public JugadorDTO getJugadorSeleccionado()
    {
        return jugadorSeleccionado;
    }

    public void setListaAllJugadores(List<JugadorDTO> listaAllJugadores)
    {
        this.listaAllJugadores = listaAllJugadores;
        //cargarItemsJugadores();
    }

    public List<JugadorDTO> getListaAllJugadores()
    {
        return listaAllJugadores;
    }

    public void setFiltroJugadores(String filtroJugadores)
    {
        this.filtroJugadores = filtroJugadores;
    }

    public String getFiltroJugadores()
    {
        return filtroJugadores;
    }

    public void setNuevoJugador(JugadorDTO nuevoJugador)
    {
        this.nuevoJugador = nuevoJugador;
    }

    public JugadorDTO getNuevoJugador()
    {
        return nuevoJugador;
    }
/*
    public void setItemsAllJugadores(List<SelectItem> itemsAllJugadores) {
        this.itemsAllJugadores = itemsAllJugadores;
    }

    public List<SelectItem> getItemsAllJugadores() {
        return itemsAllJugadores;
    }
*/
    public void setRondasdto(List<RondaDTO> rondasdto) {
        this.rondasdto = rondasdto;
    }

    public List<RondaDTO> getRondasdto() {
        return rondasdto;
    }

    public void setRondaSeleccionada(RondaDTO rondaSeleccionada)
    {
        this.rondaSeleccionada = rondaSeleccionada;
    }

    public RondaDTO getRondaSeleccionada()
    {
        return rondaSeleccionada;
    }

    public void setPartidaSeleccionada(PartidaDTO partidaSeleccionada)
    {
        this.partidaSeleccionada = partidaSeleccionada;
    }

    public PartidaDTO getPartidaSeleccionada()
    {
        return partidaSeleccionada;
    }


    public void setSeqRondas(Long seqRondas)
    {
        this.seqRondas = seqRondas;
    }

    public Long getSeqRondas()
    {
        return seqRondas;
    }

    public void setProcesoCalcularPuntos(boolean procesoCalcularPuntos)
    {
        this.procesoCalcularPuntos = procesoCalcularPuntos;
    }

    public boolean isProcesoCalcularPuntos()
    {
        return procesoCalcularPuntos;
    }

    public void setProcesoOrdenarPosiciones(boolean procesoOrdenarPosiciones)
    {
        this.procesoOrdenarPosiciones = procesoOrdenarPosiciones;
    }

    public boolean isProcesoOrdenarPosiciones()
    {
        return procesoOrdenarPosiciones;
    }

    public void setListaRitmos(List<SelectItem> listaRitmos) {
        this.listaRitmos = listaRitmos;
    }

    public List<SelectItem> getListaRitmos() {
        return listaRitmos;
    }

    public void setListaCatAscenso(List<SelectItem> listaCatAscenso) {
        this.listaCatAscenso = listaCatAscenso;
    }

    public List<SelectItem> getListaCatAscenso() {
        return listaCatAscenso;
    }

    public void setIdChessResults(Long idChessResults) {
        this.idChessResults = idChessResults;
    }

    public Long getIdChessResults() {
        return idChessResults;
    }

    public void setTorneoChessResults(TorneoDTO torneoChessResults) {
        this.torneoChessResults = torneoChessResults;
    }

    public TorneoDTO getTorneoChessResults() {
        return torneoChessResults;
    }
}
