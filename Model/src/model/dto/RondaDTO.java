package model.dto;

import java.util.ArrayList;
import java.util.List;

import model.entities.Partida;
import model.entities.Ronda;

public class RondaDTO 
{
    private Ronda ronda;
    private List<PartidaDTO> listaPartidas;
    
    private Long seqPartidas; // para ids provisorios de partidas nuevas
    private boolean expanded;

    public void agregarPartida()
    {
        if (listaPartidas == null)
        {
            listaPartidas = new ArrayList<PartidaDTO>();
        }
        PartidaDTO partida = new PartidaDTO();
        
        Partida p = new Partida(ronda);
        this.seqPartidas = (this.seqPartidas == null? new Long(1L): seqPartidas + 1);
        p.setPar_id(seqPartidas * -1);
        
        partida.setPartida(p);
        listaPartidas.add(partida);
    }
    
    public void quitarPartida(Long idPartida)
    {
        for (PartidaDTO p: this.listaPartidas)
        {
            if (p.getPartida().getPar_id().equals(idPartida))
            {
                this.listaPartidas.remove(p);
                break;
            }
        }
    }
    
    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }

    public Ronda getRonda() {
        return ronda;
    }

    public void setListaPartidas(List<PartidaDTO> listaPartidas) {
        this.listaPartidas = listaPartidas;
    }

    public List<PartidaDTO> getListaPartidas() {
        return listaPartidas;
    }

    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }

    public boolean isExpanded()
    {
        return expanded;
    }

    public void setSeqPartidas(Long seqPartidas)
    {
        this.seqPartidas = seqPartidas;
    }

    public Long getSeqPartidas()
    {
        return seqPartidas;
    }
}
