package view.backing;

import java.util.List;

import model.dto.JugadorTorneoDTO;

import model.entities.JugadorTorneo;

import model.entities.PremioTorneo;

import model.entities.Torneo;

import view.managed.DetalleTorneoBean;
import view.managed.SessionBean;

import view.services.HtmlRenderServce;

public class DetalleTorneo
{
    private SessionBean session;
    private DetalleTorneoBean detalleTorneoBean;

    
    // TODO: Manejar Torneos Por Equipos
    
    public String getHtmlResultados()
    {
        if (detalleTorneoBean.getTorneo() != null)
        {
            HtmlRenderServce s = new HtmlRenderServce();
            Torneo torneoCompleto = session.getServicio().qTorneoCompleto(detalleTorneoBean.getTorneo().getTor_id());

            return s.getHtmlResultsTournament(torneoCompleto);
        }
        else 
            return "Sin Datos.";
    }
    
    public String getHtmlPosiciones()
    {
        if (detalleTorneoBean.getTorneo() != null)
        {
            HtmlRenderServce s = new HtmlRenderServce();
            List<JugadorTorneoDTO> lista = session.getServicio().getJugadoresTorneo(detalleTorneoBean.getTorneo().getTor_id());
            
            String htmlPosiciones = s.getHtmlTorunamentStandings(lista);
            //System.out.println(htmlPosiciones);
            
            return htmlPosiciones;
        }
        else
            return "Sin Datos";
    }

    public String getHtmlPremios()
    {
        if (detalleTorneoBean.getTorneo() != null)
        {
            HtmlRenderServce s = new HtmlRenderServce();
            List<PremioTorneo> lista = session.getServicio().getPremiosTorneo(detalleTorneoBean.getTorneo().getTor_id());
            
            String htmlPremios = s.getHtmlTournamentPrizes(lista, detalleTorneoBean.getTorneo().getTor_id());
            System.out.println(htmlPremios);
            
            return htmlPremios;
        }
        else
            return "Sin Datos";
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
}
