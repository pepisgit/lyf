package view.backing;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import model.dto.JugadorDTO;

import model.entities.Jugador;

import model.entities.Persona;

import view.managed.EditarTorneoBean;
import view.managed.SessionBean;

import view.util.Util;


public class EditarTorneoLateral
{
    private EditarTorneoBean editarTorneoBean;
    private SessionBean session;

    public List<JugadorDTO> getListaJugadores()
    {
        List<JugadorDTO> lista = null;
        
        try
        {
            lista = editarTorneoBean.getListaAllJugadores();
            if (lista == null)
            {
                lista = session.getServicio().getJugadores(new HashMap());
                editarTorneoBean.setListaAllJugadores(lista);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Util.addMessageError("Ha ocurrido un error al intentar leer los jugadores: " + e.getMessage());
        }
        
        Collections.sort(lista);
        return lista;
    }
    
    public String agregar_action()
    {
        if (this.editarTorneoBean.getJugadorSeleccionado().getPosicion() == null)
        {
            this.editarTorneoBean.getJugadorSeleccionado().setPosicion
                (this.editarTorneoBean.getListaJugadores() != null? this.editarTorneoBean.getListaJugadores().size(): 1);
        }
        this.editarTorneoBean.agregarJugador();
        return null;
    }

    public String guardar_jugador_action()
    {
        JugadorDTO jdto = editarTorneoBean.getNuevoJugador();
        try
        {
            Persona p = new Persona();
            p.setPer_apellido(jdto.getJugApellido());
            p.setPer_nombres(jdto.getJugNombre());
            
            Long idPersona = session.getServicio().persistPersona(p, true);
            
            Jugador j = new Jugador();
            j.setPer_id(idPersona);
            j.setJug_elo_actual(jdto.getGrauActual());
            j.setJug_activo(1L);
            
            Long idJugador = session.getServicio().persistJugador(j, true);
            jdto.setJugId(idJugador);
            jdto.setPuntaje(0D);
            
            
            this.editarTorneoBean.setJugadorSeleccionado(jdto);
            this.editarTorneoBean.agregarJugador();
            this.editarTorneoBean.reasignarPosiciones();
            
            // Reasigna posiciones
            /*
            int pos = 0;
            for (JugadorDTO jugador: editarTorneoBean.getListaJugadores())
            {
                pos++;
                jugador.setPosicion(Integer.valueOf(pos));
            }
            */
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Util.addMessageError("Ha ocurrido un error al intentar guardar: " + e.getMessage());
        }
        return null;
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
}
