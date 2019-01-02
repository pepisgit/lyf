package view.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.component.html.HtmlInputText;
import javax.faces.model.SelectItem;

import model.dto.JugadorDTO;

import model.dto.PartidaDTO;

import model.entities.Parametros;

import org.apache.commons.beanutils.BeanComparator;

import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ReverseComparator;

import model.dto.RondaDTO;

import model.dto.TorneoDTO;

import model.entities.Torneo;

import view.managed.EditarTorneoBean;
import view.managed.SessionBean;

import view.services.ChessResultsService;

import view.util.Util;

public class EditarTorneo
{
    private EditarTorneoBean editarTorneoBean;
    private HtmlInputText txtCantidadPartidasAgregar;
    private SessionBean session;
    
    public String btnGuardar_action()
    {
        try
        {
            // Validar datos
            
            // Guardar
            // this.getEditarTorneoBean().mergeData(); // Actualiza entidades
            
            Torneo result = session.getServicio().persistTorneo(editarTorneoBean.getTorneo(),
                                                                editarTorneoBean.getRondasdto(),
                                                                editarTorneoBean.getListaJugadores());
            
            if (result != null)
            {
                editarTorneoBean.setTorneo(result);
                editarTorneoBean.cargarPartidasDTO();
                
                Util.addMessageInfo("Los datos han sido guardados.");
                return "volver";
            }
            else
            {
                Util.addMessageError("Ha ocurrido un problema al intentar guardar el torneo.");
            }
        }
        catch (Exception e)
        {
            String msj = e.getMessage();
            e.printStackTrace();
            Util.addMessageError("Ha ocurrido un error al intentar guardar los datos. " + (msj!=null?msj:"") );
        }
        return null;
    }

    // Carga Torneo de ChessResults luego de confirmar jugadores en popup
    public String cargarTorneoCR()
    {
        TorneoDTO torneoCR = this.editarTorneoBean.getTorneoChessResults();
        
        // Cargar Datos Generales
        this.editarTorneoBean.getTorneo().setTor_nombre(torneoCR.getNombreTorneo());
        
        // Cargar Jugadores modificados o cargados manualmente
        for (JugadorDTO j: torneoCR.getListaJugadores())
        {
            if (j.getJugadorSistema() == null || (!j.getJugSistemaId().equals(j.getJugadorSistema().getJugId())) )
            {
                JugadorDTO jugadorSistema = this.session.getServicio().getJugadorDTO(j.getJugSistemaId());
                j.setJugadorSistema(jugadorSistema);
            }
            j.getJugadorSistema().setJugCRId(j.getOrden()); // Copia el id de CR para luego saber cómo recrear partidas
        }
        
        // Cargar Lista Jugadores
        for (JugadorDTO j: torneoCR.getListaJugadores())
        {
            this.editarTorneoBean.setJugadorSeleccionado(j.getJugadorSistema());
            this.editarTorneoBean.agregarJugador();
        }
        this.editarTorneoBean.reasignarPosiciones();
        
        // Cargar Rondas / Partidas
        for (RondaDTO r: torneoCR.getListaRondas())
        {
            Long nroRonda = r.getRonda().getNumeroRonda();
            Date fecRonda = r.getRonda().getRon_fecha();
            
            System.out.println("Ronda " + nroRonda + " - Fecha: " + fecRonda + " - Partidas: " + r.getListaPartidas().size());
            
            // Crear Ronda con cantidad de partidas
            RondaDTO nuevaRonda = this.getEditarTorneoBean().agregarRonda(r.getListaPartidas().size());
            nuevaRonda.getRonda().setRon_fecha(fecRonda);
            
            // Cargar datos de partidas
            
            int index = 0;
            for (PartidaDTO np: nuevaRonda.getListaPartidas())
            {
                PartidaDTO p = r.getListaPartidas().get(index); // Partida de CR
                
                // Copiar valores
                np.getPartida().setPar_jug_blancas( getIdJugadorSistema(p.getPartida().getPar_jug_blancas()) );
                np.getPartida().setPar_jug_negras( getIdJugadorSistema(p.getPartida().getPar_jug_negras()) );
                
                np.getPartida().setPar_resultado_blancas(p.getPartida().getPar_resultado_blancas());
                np.getPartida().setPar_resultado_negras(p.getPartida().getPar_resultado_negras());
                
                np.getPartida().setPar_tipo_res_blancas(p.getPartida().getPar_tipo_res_blancas());
                np.getPartida().setPar_tipo_res_negras(p.getPartida().getPar_tipo_res_negras());
                
                np.getPartida().setNombreJugadorBlancas(getNombreJugador(np.getPartida().getPar_jug_blancas()));
                np.getPartida().setNombreJugadorNegras(getNombreJugador(np.getPartida().getPar_jug_negras()));
                
                index++;
            }
            
        }
        
        return null;
    }
    
    private String getNombreJugador(Long id)
    {
        String ret = "";
        if (id.equals(0L)) return "BYE";
        
        for (JugadorDTO j: editarTorneoBean.getListaJugadores())
        {
            if (j.getJugId() != null && j.getJugId().equals(id))
            {
                ret = j.getApellidoNombre();
                break;
            }
        }
        return ret;
    }
    
    
    // Recorre los jugadores para ver cuál tenía un determinado Id de CR
    private Long getIdJugadorSistema(Long idCR)
    {
        Long ret = null;
        if (idCR.equals(0L)) return Long.valueOf(0L);
        
        for (JugadorDTO j: editarTorneoBean.getListaJugadores())
        {
            if (j.getJugCRId() != null && j.getJugCRId().equals(idCR))
            {
                ret = j.getJugId();
                break;
            }
        }
        return ret;
    }

    public String nuevaRonda_action()
    {
        int partidas = 0;
        
        if (this.getTxtCantidadPartidasAgregar().getValue() != null)
        {
            partidas = Integer.valueOf((String)this.getTxtCantidadPartidasAgregar().getValue()).intValue();
        }
        this.getEditarTorneoBean().agregarRonda(partidas);
        return null;
    }
    
    public String quitarRonda_action()
    {
        RondaDTO ronda = editarTorneoBean.getRondaSeleccionada();
        editarTorneoBean.quitarRonda(ronda);
        return null;
    }

    public String nuevaPartida_action()
    {
        RondaDTO ronda = editarTorneoBean.getRondaSeleccionada();
        if (ronda != null)
        {
            ronda.agregarPartida();
        }
        return null;
    }
    
    public String quitarPartida_action()
    {
        PartidaDTO partida = editarTorneoBean.getPartidaSeleccionada();
        RondaDTO ronda = editarTorneoBean.getRondaSeleccionada();
        if (partida != null)
        {
            ronda.quitarPartida(partida.getPartida().getPar_id());
        }
        return null;
    }

    public String quitarJugador_action()
    {
        this.editarTorneoBean.quitarJugador();
        return null;
    }
    
    public String subirJugador_action()
    {
        Long id = editarTorneoBean.getJugadorSeleccionado().getJugId();
        
        editarTorneoBean.moverPosicionJugador(id, 1);
        return null;
    }

    public String bajarJugador_action()
    {
        Long id = editarTorneoBean.getJugadorSeleccionado().getJugId();
        
        editarTorneoBean.moverPosicionJugador(id, -1);
        return null;
    }
    
    public String btnProcesarPuntaje_action()
    {
        // Calcula puntaje de los jugadores en base a los resultados, y los asigna
        
        Double puntaje = 0D;
        if (editarTorneoBean.getListaJugadores() != null && editarTorneoBean.isProcesoCalcularPuntos())
        for (JugadorDTO jugador: editarTorneoBean.getListaJugadores())
        {
            puntaje = 0D;
            
            if (editarTorneoBean.getRondasdto() != null)
            for (RondaDTO ronda: editarTorneoBean.getRondasdto())
            {
                if (ronda.getListaPartidas() != null)
                for (PartidaDTO partida: ronda.getListaPartidas())
                {
                    if (partida.getPartida().getPar_jug_blancas().equals(jugador.getJugId()))
                    {
                        // Suma puntos obtenidos
                        switch (partida.getPartida().getPar_resultado_blancas().intValue())
                        {
                            case 1: puntaje += 1D; break;
                            case 2: puntaje += 0.5D;
                        }
                    }
                    else if (partida.getPartida().getPar_jug_negras().equals(jugador.getJugId()))
                    {
                        // Suma puntos obtenidos
                        switch (partida.getPartida().getPar_resultado_negras().intValue())
                        {
                            case 1: puntaje += 1D; break;
                            case 2: puntaje += 0.5D;
                        }
                    }
                }
            }
            jugador.setPuntaje(puntaje);
        }
        
        if (editarTorneoBean.getListaJugadores() != null && editarTorneoBean.isProcesoOrdenarPosiciones())
        {
            // Reordena listado de jugadores según su puntaje
            BeanComparator bc = new BeanComparator("puntaje", new ReverseComparator(new ComparableComparator()));

            Collections.sort(editarTorneoBean.getListaJugadores(), bc);
            
            
            editarTorneoBean.reasignarPosiciones();
            
            /*
            // Reasigna posiciones
            int pos = 0;
            for (JugadorDTO jugador: editarTorneoBean.getListaJugadores())
            {
                pos++;
                jugador.setPosicion(Integer.valueOf(pos));
            }
            */
        }
        return null;
    }
    
    public String btnProcesarCR_action()
    {
        Long idCR = editarTorneoBean.getIdChessResults();
        
        try
        {
            TorneoDTO t = ChessResultsService.readCRTournamentById(idCR);
            
            // Trae jugadores de la base que coincidan con el nombre de CR
            for (JugadorDTO j: t.getListaJugadores())
            {
                JugadorDTO matchJug =  session.getServicio().getJugadorBySoundex(j.getJugNombre());
                
                if (matchJug != null)
                {
                    j.setJugSistemaId(new Long(matchJug.getJugId()));
                    j.setJugSistemaNombre(matchJug.getApellidoNombre());
                    j.setJugadorSistema(matchJug);
                }
            }
            
            editarTorneoBean.setTorneoChessResults(t);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // TODO Mensaje error
        }
        return null;
    }    
    
    public String btnSalir_action()
    {
        return "volver";
    }
    
    public List<SelectItem> getListaTiposTorneo()
    {
        if (editarTorneoBean.getListaTiposTorneo() == null)
        {
            List<SelectItem> ret = new ArrayList<SelectItem>();
            try
            {
                List<Parametros> listaTipos = session.getServicio().qParametrosPorTipo("TIPOS_TORNEO"); 

                if (listaTipos != null && listaTipos.size() > 0){
                    for (Parametros par: listaTipos)
                    {
                        SelectItem item = new SelectItem();
                        item.setValue(Long.valueOf(par.getPar_codigo()));
                        item.setLabel(par.getPar_valor());
                        
                        ret.add(item);
                    }
                    editarTorneoBean.setListaTiposTorneo(ret);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return editarTorneoBean.getListaTiposTorneo();
    }

    public List<SelectItem> getListaCatAscenso()
    {
        if (editarTorneoBean.getListaCatAscenso() == null)
        {
            List<SelectItem> ret = new ArrayList<SelectItem>();
            
            SelectItem item = new SelectItem();
            item.setValue(Long.valueOf(0));
            item.setLabel("--Seleccionar--");
            ret.add(item);
            
            SelectItem item2 = new SelectItem();
            item2.setValue(Long.valueOf(3));
            item2.setLabel("3ra");
            ret.add(item2);

            SelectItem item3 = new SelectItem();
            item3.setValue(Long.valueOf(2));
            item3.setLabel("2da");
            ret.add(item3);
            
            editarTorneoBean.setListaCatAscenso(ret);
        }

        return editarTorneoBean.getListaCatAscenso();
    }

    public List<SelectItem> getListaSistemas()
    {
        if (editarTorneoBean.getListaSistemas() == null)
        {
            List<SelectItem> ret = new ArrayList<SelectItem>();
            try
            {
                List<Parametros> listaSistemas = session.getServicio().qParametrosPorTipo("SISTEMAS"); 

                if (listaSistemas != null && listaSistemas.size() > 0){
                    for (Parametros par: listaSistemas)
                    {
                        SelectItem item = new SelectItem();
                        item.setValue(Long.valueOf(par.getPar_codigo()));
                        item.setLabel(par.getPar_valor());
                        
                        ret.add(item);
                    }
                    editarTorneoBean.setListaSistemas(ret);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return editarTorneoBean.getListaSistemas();
    }

    public List<SelectItem> getListaRitmos()
    {
        if (editarTorneoBean.getListaRitmos() == null)
        {
            List<SelectItem> ret = new ArrayList<SelectItem>();
            try
            {
                List<Parametros> listaRitmos = session.getServicio().qParametrosPorTipo("RITMO_DE_JUEGO"); 

                if (listaRitmos != null && listaRitmos.size() > 0){
                    for (Parametros par: listaRitmos)
                    {
                        SelectItem item = new SelectItem();
                        item.setValue(Long.valueOf(par.getPar_codigo()));
                        item.setLabel(par.getPar_valor());
                        
                        ret.add(item);
                    }
                    editarTorneoBean.setListaRitmos(ret);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return editarTorneoBean.getListaRitmos();
    }

    public void setEditarTorneoBean(EditarTorneoBean editarTorneoBean)
    {
        this.editarTorneoBean = editarTorneoBean;
    }

    public EditarTorneoBean getEditarTorneoBean()
    {
        return editarTorneoBean;
    }

    public void setSession(SessionBean session)
    {
        this.session = session;
    }

    public SessionBean getSession()
    {
        return session;
    }

    public void setTxtCantidadPartidasAgregar(HtmlInputText txtCantidadPartidasAgregar)
    {
        this.txtCantidadPartidasAgregar = txtCantidadPartidasAgregar;
    }

    public HtmlInputText getTxtCantidadPartidasAgregar()
    {
        return txtCantidadPartidasAgregar;
    }
}
