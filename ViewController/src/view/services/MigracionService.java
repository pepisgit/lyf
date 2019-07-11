package view.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

import java.text.SimpleDateFormat;

import java.util.Date;

import model.entities.Partida;

import view.managed.SessionBean;

public class MigracionService
{
    private SessionBean session;
    
    public static void main(String[] args)
    {
        MigracionService migracionService = new MigracionService();
        migracionService.procesarXlsResultados();
    }
    
    public void procesarXlsResultados()
    {
        String csvFile = "D:\\tmp\\res_5toClas2013_.csv";
        
        try
        {
            File file = new File (csvFile);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String linea = null;
            
            Long idPartida = 0L;
            Long torneo = 1L;
            
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
                        fecha = sdf.parse("19/10/2012");
                    else if (ronda.equals(3L) || ronda.equals(4L))
                        fecha = sdf.parse("26/10/2012");
                    else if (ronda.equals(5L) || ronda.equals(6L))
                        fecha = sdf.parse("02/11/2012");
                    
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
                
                    session.getServicio().persist(p);
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

    public void setSession(SessionBean session)
    {
        this.session = session;
    }

    public SessionBean getSession()
    {
        return session;
    }
}
