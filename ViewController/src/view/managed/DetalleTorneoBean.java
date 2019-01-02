package view.managed;

import java.util.HashMap;
import java.util.List;

import java.util.Map;

import model.dto.JugadorDTO;

import model.entities.Noticias;
import model.entities.Torneo;

import model.util.Constantes;

public class DetalleTorneoBean
{
    private Long idTorneo; // Id pasado por parametro
    private Torneo torneo; // Torneo, rondas y partidas
    
    private String jugadorClasificado;
    private String styleFilaClasificado;
    private List<Noticias> noticiasRelacionadas;
        
    private SessionBean session;
    
    public void clean()
    {
        this.setIdTorneo(null);
        this.setTorneo(null);
        this.setNoticiasRelacionadas(null);
    }
    
    public void inicializar()
    {

        if (torneo.getTor_tipo().equals(Long.valueOf(Constantes.TIPO_TORNEO_CLASIFICATORIO)) ||
            torneo.getTor_tipo().equals(Long.valueOf(Constantes.TIPO_TORNEO_CLASIF_AFICIONADOS)))
        {
            Map filtros = new HashMap();
            filtros.put("idTorneo", this.torneo.getTor_id());
            List <JugadorDTO> listaJugadoresTorneo = session.getServicio().getJugadores (filtros);
            String clasificados = "";
            
            if (listaJugadoresTorneo != null && listaJugadoresTorneo.size() > 0)
            {
                for (JugadorDTO jug: listaJugadoresTorneo)
                {
                    if (jug.isClasifica())
                    {
                        clasificados += " / " + jug.getApellidoNombre();
                    }
                }
                if (!clasificados.equals(""))
                    clasificados = (clasificados.substring(2)).toUpperCase();
            }
            
            this.jugadorClasificado = clasificados;
            this.styleFilaClasificado = "visibility:visible;";
        }
        else
        {
            this.styleFilaClasificado = "visibility:hidden;";
        }
        
    }

    public void setIdTorneo(Long idTorneo)
    {
        this.idTorneo = idTorneo;
    }

    public Long getIdTorneo()
    {
        return idTorneo;
    }

    public void setTorneo(Torneo torneo)
    {
        this.torneo = torneo;
    }

    public Torneo getTorneo()
    {
        return torneo;
    }

    public void setNoticiasRelacionadas(List<Noticias> noticiasRelacionadas)
    {
        this.noticiasRelacionadas = noticiasRelacionadas;
    }

    public List<Noticias> getNoticiasRelacionadas()
    {
        return noticiasRelacionadas;
    }

    public void setJugadorClasificado(String jugadorClasificado)
    {
        this.jugadorClasificado = jugadorClasificado;
    }

    public String getJugadorClasificado()
    {
        return jugadorClasificado;
    }

    public void setStyleFilaClasificado(String styleFilaClasificado)
    {
        this.styleFilaClasificado = styleFilaClasificado;
    }

    public String getStyleFilaClasificado()
    {
        return styleFilaClasificado;
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
