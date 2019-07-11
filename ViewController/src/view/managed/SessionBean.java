package view.managed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import model.entities.JugadorTorneo;
import model.entities.Noticias;

import model.entities.Partida;

import model.servicio.ServicioClub;

public class SessionBean implements Serializable
{
    private ServicioClub servicio = null;
    private String usuario;
    
    private String rutaImagenes;


    public SessionBean()
    {
        try 
        {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("SESSION_BEAN", this);
            servicio = new ServicioClub();
            
            File path = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/"));
            String parent = path.getParent();
            
            rutaImagenes = parent + File.separator + "imagenesLyF" + File.separator;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<Noticias> getUltimasNoticias()
    {
        List<Noticias> lista = this.getServicio().getUltimasNoticias();
        
        //Noticias not = session.getServicio().getNoticiaById(1L);
        //System.out.println(not.getNot_contenido());
        
        return lista;
    }

    public void procesarXlsResultados(Long torneo, String csvFile)
    {
        try
        {
            File file = new File (csvFile);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String linea = null;
            
            Long idPartida = 94L;
            
            Long ronda = null;
            Long blancas = null;
            Long negras = null;
            Long resBlancas = null;
            Long resNegras = null;
            Long tipoResBlancas = null;
            Long tipoResNegras = null;
            Date fecha = null;
            String lugar = "Club Ajedrez Pensado";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            
            while (br.ready()) 
            {
                linea = br.readLine();
                String []s = linea.split("[;]");
                
                if (s[1] != null && s[1].contains("Ronda"))
                {
                    ronda = Long.valueOf(s[1].substring(6));
                    System.out.println("Ronda: " + ronda + "\n");
                }
                else
                if (s.length > 5 && !s[0].trim().equals("") &&  !s[1].trim().equals("") && !s[2].trim().equals("") && !s[3].trim().equals("") && !s[4].trim().equals("") && !s[5].trim().equals("")) 
                {
                    // Seteo Fecha
                    if (ronda.equals(1L) || ronda.equals(2L))
                        fecha = sdf.parse("29/03/2014");
                    else if (ronda.equals(3L) || ronda.equals(4L))
                        fecha = sdf.parse("05/04/2014");
                    else if (ronda.equals(5L) || ronda.equals(6L))
                        fecha = sdf.parse("12/04/2014");
                    
                    // Seteo jugadores
                    blancas = Long.valueOf(s[0].trim());
                    negras = Long.valueOf(s[5].trim());
                    
                    // Seteo resultados y tipo de resultados
                    Long[] resultadoBlancas = parseResultado(s[2].trim(), s[4]);
                    Long[] resultadoNegras =  parseResultado(s[3].trim(), null);
                    
                    resBlancas = resultadoBlancas[0];
                    resNegras = resultadoNegras[0];
                    
                    tipoResBlancas = resultadoBlancas[1];
                    tipoResNegras = resultadoNegras[1];
                    
                    System.out.println(sdf.format(fecha) + " - " + blancas + " vs " + negras + " --> " + resBlancas + "-" + resNegras + "-" + tipoResBlancas + "-" + tipoResNegras);
                            
                    //System.out.println(s[0] + " " + s[1] + " " + s[2] + "-" + s[3] + " " + s[4] + " " + s[5]);
                
                    // Creo la entidad Partida
                    idPartida += 1L;
                    Partida p = new Partida (idPartida,
                                             torneo,
                                             ronda,
                                             null,
                                             fecha,
                                             blancas,
                                             negras,
                                             resBlancas,
                                             resNegras,
                                             tipoResBlancas,
                                             tipoResNegras,
                                             null,
                                             null,
                                             lugar,
                                             null,
                                             null,
                                             null);
                
                    getServicio().persist(p);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }

    public Long[] parseResultado (String res, String oponente)
    {
        Long[] ret = new Long[2];
        
        if (res.contains("1"))
        {
            ret[0] = 1L;
        }
        else if (res.contains("0"))
        {
            ret[0] = 0L;
        }
        else if (res.equals("½"))
        {
            ret[0] = 2L;
        }

        if (res.contains("(a)"))
            ret[1] = 2L;
        else if (oponente != null && oponente.trim().equals("BYE"))
            ret[1] = 3L;
        else
            ret[1] = 1L;
        
        return ret;
    }

    public void procesarXlsPosiciones (Long idTorneo, String csvFile)
    {
        try
        {
            File file = new File (csvFile);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String linea = null;
            
            DecimalFormat df = new DecimalFormat ("###,#");
            
            Long jugId = null;
            Integer posicion = 0;
            Double puntaje = 0D;
            
            while (br.ready()) 
            {
                linea = br.readLine();
                String []s = linea.split("[;]");
                
                if (s.length > 3)
                {
                    posicion++;
                    puntaje = df.parse(s[2]).doubleValue();
                    jugId = Long.parseLong(s[3]);
                    
                    JugadorTorneo jt = new JugadorTorneo(idTorneo, jugId, posicion, puntaje, null);
                    getServicio().persist(jt);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setServicio(ServicioClub servicio)
    {
        this.servicio = servicio;
    }

    public ServicioClub getServicio()
    {
        return servicio;
    }

    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }

    public String getUsuario()
    {
        return usuario;
    }
    
    public String getRandom()
    {
        double rnd = Math.random();
        
        String ret = String.valueOf(rnd);
        ret = ret.substring(2);
        return ret;
    }

    public void setRutaImagenes(String rutaImagenes) {
        this.rutaImagenes = rutaImagenes;
    }

    public String getRutaImagenes() {
        return rutaImagenes;
    }
}
