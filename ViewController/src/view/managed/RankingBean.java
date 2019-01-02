package view.managed;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import model.dto.JugadorDTO;

import model.entities.GrauHistorial;
import model.entities.GrauRanking;

public class RankingBean 
{
    private JugadorDTO jugador;
    private GrauRanking grauRanking;
    private String pfdMostrar;
    private List<GrauHistorial> historial;
    
    public Long getMinimo()
    {
        Long menor = 9999L;
        if (historial != null && historial.size() > 0)
        {
            for (GrauHistorial gh: historial)
            {
                if (gh.getHis_puntaje() < menor){
                    menor = gh.getHis_puntaje();
                }
            }
        }
        else
            menor = 1200L;
        
        menor = menor - 200L;
        
        if (menor < 1200L)
            menor = 1200L;
        else
        {
            menor = Long.valueOf(100 * Math.round(menor / 100));
        }
        
        return menor;
    }
    
    public String getFechaMinima()
    {
        String ret = null;

        if (historial != null && historial.size() > 0)
        {
            if (historial.size() >= 12)
                ret = historial.get(historial.size()-12).getFechaFormateada();
            else
                ret = historial.get(0).getFechaFormateada();
        }
        System.out.println("Fecha minima: " + ret);
        return ret;
    }
    
    public String getFechaMaxima()
    {
        String ret = null;
        
        if (historial != null && historial.size() > 0)
        {
            Date fechaMaximaHistorial = historial.get(historial.size()-1).getRkg_fecha();
            Calendar cal = new GregorianCalendar ();
            cal.setTime(fechaMaximaHistorial);
            cal.add(Calendar.MONTH, 1);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            ret = sdf.format(cal.getTime());            
            
            /*
            if (historial.size() >= 12)
                ret = historial.get(historial.size()-1).getFechaFormateada();
            else
            {
                Date fechaMinimaHistorial = getFechaMinimaHistorial();
                Calendar cal = new GregorianCalendar ();
                cal.setTime(fechaMinimaHistorial);
                cal.add(Calendar.MONTH, 11);
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
                ret = sdf.format(cal.getTime());
            }
*/
        }
        System.out.println("Fecha máxima: " + ret);
        return ret;
    }
    
    private Date getFechaMinimaHistorial()
    {
        if (historial != null && historial.size() > 0)
        {
            return historial.get(0).getRkg_fecha();
        }
        else return null;
    }
    
    public String getJsonHistorial(){
        return new Gson().toJson(historial);
    }
    
    public void setHistorial(List<GrauHistorial> historial) {
        this.historial = historial;
    }

    public List<GrauHistorial> getHistorial() {
        return historial;
    }

    public void setJugador(JugadorDTO jugador) {
        this.jugador = jugador;
    }

    public JugadorDTO getJugador() {
        return jugador;
    }

    public void setGrauRanking(GrauRanking grauRanking) {
        this.grauRanking = grauRanking;
    }

    public GrauRanking getGrauRanking() {
        return grauRanking;
    }

    public void setPfdMostrar(String pfdMostrar) {
        this.pfdMostrar = pfdMostrar;
    }

    public String getPfdMostrar() {
        return pfdMostrar;
    }
}
