 package view.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import javax.faces.component.html.HtmlForm;

import model.dto.JugadorDTO;

import model.entities.JugadorTorneo;
import model.entities.Torneo;

import model.util.Constantes;

import view.managed.DetalleTorneoBean;
import view.managed.EditarTorneoBean;
import view.managed.SessionBean;
import view.managed.UserInfo;

public class Torneos 
{
    private SessionBean session;
    private UserInfo userInfo;
    private DetalleTorneoBean detalleTorneoBean;
    private EditarTorneoBean editarTorneoBean;
    
    public String getHabilitaCargaTorneos()
    {
        if (userInfo != null && userInfo.getUsuario() != null && 
            userInfo.getUsuario().getRol() != null)
        {
            if (userInfo.getUsuario().getRol().getRol_id() == Constantes.ROL_ADMIN ||
                userInfo.getUsuario().getRol().getRol_id() == Constantes.ROL_CLUB_MANAGER)
            {
                return "S";
            }
        }
        return "N";
    }
    
    public String verTorneo_action()
    {
        try
        {
            detalleTorneoBean.inicializar();
            System.out.println("Abriendo torneo: " + detalleTorneoBean.getTorneo().getTor_nombre());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "detalleTorneo";
    }
    
    public String nuevoTorneo_action()
    {
        editarTorneoBean.clear();
        editarTorneoBean.inicializar();
        return "editar";
    }
    
    public String modificarTorneo_action()
    {
        editarTorneoBean.clear();
        editarTorneoBean.setModo(1); // Modificaciones
        
        // Cargar Torneo Completo
        Torneo torneoCompleto = session.getServicio().qTorneoCompleto(editarTorneoBean.getTorneo().getTor_id()); 
        editarTorneoBean.setTorneo(torneoCompleto);
        
        // Cargo Todos los jugadores disponibles
        List<JugadorDTO> listaTodos = session.getServicio().getJugadores(new HashMap());
        editarTorneoBean.setListaAllJugadores(listaTodos);
        
        // Cargo los jugadores del torneo
        Map filtros = new HashMap();
        filtros.put("idTorneo", editarTorneoBean.getTorneo().getTor_id());
        
        List<JugadorDTO> jugadoresTorneo = session.getServicio().getJugadores(filtros);
        
        if (jugadoresTorneo != null && jugadoresTorneo.size() > 0)
        {
            for (JugadorDTO j: jugadoresTorneo)
            {
                editarTorneoBean.setJugadorSeleccionado(j);
                editarTorneoBean.agregarJugador();
            }
        }
        
        JugadorDTO bye = new JugadorDTO();
        bye.setJugApellido("BYE");
        bye.setJugNombre("");
        bye.setJugId(0L);
        bye.setPosicion(0);
        bye.setPuntaje(0D);
        
        if (editarTorneoBean.getListaJugadores() == null)
        {
            editarTorneoBean.setListaJugadores(new ArrayList<JugadorDTO>());
        }        
        
        editarTorneoBean.getListaJugadores().add(bye);
        editarTorneoBean.cargarPartidasDTO();
        editarTorneoBean.getTorneo().setTorbnok(editarTorneoBean.getTorneo().getTor_bn_ok().equals("S"));
        
        return "editar";
    }
    
    public List<Torneo> getListaTorneos()
    {
        List<Torneo> lista = null;
        try
        {
            lista = session.getServicio().qTorneos(null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lista;
    }
    
    public void setSession(SessionBean session)
    {
        this.session = session;
    }

    public SessionBean getSession()
    {
        return session;
    }

    public void setDetalleTorneoBean(DetalleTorneoBean detalleTorneoBean)
    {
        this.detalleTorneoBean = detalleTorneoBean;
    }

    public DetalleTorneoBean getDetalleTorneoBean()
    {
        return detalleTorneoBean;
    }

    public void setUserInfo(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo()
    {
        return userInfo;
    }

    public void setEditarTorneoBean(EditarTorneoBean editarTorneoBean) {
        this.editarTorneoBean = editarTorneoBean;
    }

    public EditarTorneoBean getEditarTorneoBean() {
        return editarTorneoBean;
    }
}
