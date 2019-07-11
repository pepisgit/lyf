package view.services;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.dto.JugadorDTO;
import model.dto.PartidaDTO;
import model.dto.RondaDTO;
import model.dto.TorneoDTO;

import model.entities.Partida;
import model.entities.Ronda;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Sintaxis para selector jsoup : https://jsoup.org/cookbook/extracting-data/selector-syntax
 */

public class ChessResultsService
{
    public static TorneoDTO readCRTournamentById(Long crId) throws Exception
    {
        TorneoDTO torneo = new TorneoDTO();
        
        String urlGeneral = "http://chess-results.com/tnr" + crId + ".aspx?lan=2&turdet=YES";
        Document doc = Jsoup.connect(urlGeneral).get();
        
        // Carga datos generales
        
        Elements cuadros = doc.getElementsByClass("defaultDialog");
        
        Element elNombre = cuadros.get(0).select("h2").first();
        String datNombre = elNombre.text();
        torneo.setNombreTorneo(datNombre);
        
        // Carga lista de Jugadores

        // Elements tablaJugadores = doc.select("div.defaultDialog > table > tbody > tr:gt(0)");
        
        
        Elements tablaJugadores = doc.select("div.defaultDialog > table.CRs1 > tbody > tr"); 
        // Cambio: La fila cero me sirve para saber donde estan los datos porque no siempre hay la misma cantidad de columnas
        
        int idx_nombre = -1, idx_jid = -1, idx_fed = -1, idx_rkg = -1;
        int idx = 0;
        // Recorre fila 0 para determinar indices (columnas) correctos de los datos
        Elements header = tablaJugadores.get(0).getElementsByTag("td");
        
        
        for (Element celda: header)
        {
            String texto = celda.text() != null? celda.text().trim(): "";
            if (texto.equalsIgnoreCase("Nombre") || texto.equalsIgnoreCase("Name")) idx_nombre = idx;
            if (texto.equalsIgnoreCase("ID")) idx_jid = idx;
            if (texto.equalsIgnoreCase("FED")) idx_fed = idx;
            if (texto.equalsIgnoreCase("EloN")) idx_rkg = idx;
            idx++;
        }
        
        if (idx_nombre == -1)
        {
            throw new Exception ("No se pudieron identificar columnas de jugadores correctamente.");
        }
        
        if (tablaJugadores.size() > 1)
        {
            torneo.setListaJugadores(new ArrayList<JugadorDTO>());
            
            tablaJugadores.remove(0); // Quito la cabecera.
            
            for (Element e: tablaJugadores)
            {
                Elements columnas = e.getElementsByTag("td");
                
                String jOrden  = columnas.get(0).text();
                String jNombre = columnas.get(idx_nombre).text();
                String jId     = idx_jid >= 0? columnas.get(idx_jid).text(): null;
                String jFed    = idx_fed >= 0? columnas.get(idx_fed).text(): null;
                String jRkg    = idx_rkg >= 0? columnas.get(idx_rkg).text(): null;
                
                System.out.println(jNombre + " " + jId + " " + jFed + " " + jRkg);
                
                JugadorDTO j = new JugadorDTO();
                
                j.setOrden(Long.valueOf(jOrden));
                j.setJugNombre(jNombre);
                j.setJugId(jId != null && !jId.equals("")? Long.valueOf(jId): 0L);
                j.setFederacion(jFed);
                j.setGrauActual(jRkg != null && !jRkg.equals("")? Long.valueOf(jRkg): 0L);
                
                torneo.getListaJugadores().add(j);
            }
        }
        
        // Carga rondas
        
        Long nroRonda = 1L;
        int tipoTorneo = 0; // 1=Round Robin, 2=Suizo
        
        int idx_blancas, idx_negras, idx_resultado;
        int indice;
        
        String urlRondas = "http://chess-results.com/tnr" + crId + ".aspx?lan=2&art=2&zeilen=99999";
        Document docRondas = Jsoup.connect(urlRondas).get();
        
        
        Elements chkRondasRR = docRondas.select("div.defaultDialog > table > tbody > tr.CRg1b > td:matches(.*Ronda.*)");
        Elements chkRondasSZ = docRondas.select("div.defaultDialog > h3:matches(.*Ronda.*)");
        
        if (chkRondasRR.size() > 0)
            tipoTorneo = 1;
        else if (chkRondasSZ.size() > 0)
            tipoTorneo = 2;
        else
            throw new Exception ("No se ha podido determinar el tipo de Torneo a partir de la estructura del html.");
        
        Elements pHeader = null, tablaHeaderPartidasCRNG1B, tablaHeaderPartidasCRG1B;
        
        if (tipoTorneo == 1)
        {
            tablaHeaderPartidasCRG1B = docRondas.select("div.defaultDialog > table.CRs1 > tbody > tr.CRg1b");
            tablaHeaderPartidasCRNG1B = docRondas.select("div.defaultDialog > table.CRs1 > tbody > tr.CRng1b");
            
            pHeader = tablaHeaderPartidasCRG1B.size() > 0 ?
                tablaHeaderPartidasCRG1B.get(1).getElementsByTag("td"):
                tablaHeaderPartidasCRNG1B.get(1).getElementsByTag("td");
        }
        else if (tipoTorneo == 2)
        {
            tablaHeaderPartidasCRG1B = docRondas.select("div.defaultDialog > table.CRs1 > tbody > tr.CRg1b");
            tablaHeaderPartidasCRNG1B = docRondas.select("div.defaultDialog > table.CRs1 > tbody > tr.CRng1b");
            
            pHeader = tablaHeaderPartidasCRG1B.size() > 0 ?
                tablaHeaderPartidasCRG1B.get(0).getElementsByTag("td"):
                tablaHeaderPartidasCRNG1B.get(0).getElementsByTag("td");
        }
        
        idx_blancas = -1; idx_negras = -1; idx_resultado = -1;
        indice = 0;
        
        for (Element celda: pHeader)
        {
            String texto = celda.text() != null? celda.text().trim(): "";
            if (texto.equalsIgnoreCase("No."))
            {
                if (idx_blancas == -1)
                    idx_blancas = indice;
                else
                    idx_negras = indice;
            }
            if (texto.equalsIgnoreCase("Resultado"))
            {
                idx_resultado = indice;
            }
            indice++;
        }
        
        if (idx_blancas == -1 || idx_negras == -1  || idx_resultado == -1)
        {
            throw new Exception ("No se pudieron identificar columnas de partidas correctamente.");
        }
        
        Elements tablasRondas;
        
        if (tipoTorneo == 1) // Round Robin
        {
            tablasRondas = docRondas.select("div.defaultDialog > table.CRs1 > tbody > tr");
            
            for (int nRonda = 1; nRonda <= chkRondasRR.size(); nRonda++) // itera n rondas
            {
                RondaDTO r = new RondaDTO();
                r.setRonda(new Ronda());
                r.getRonda().setNumeroRonda(Long.valueOf(nRonda));
                
                // Fecha de la ronda
                Elements elemFecha = docRondas.select("div.defaultDialog > table > tbody > tr.CRg1b > td:matches(" + nRonda + "\\..*)");
                if (elemFecha.size() > 0)
                {
                    r.getRonda().setRon_fecha(getFecha(elemFecha));
                }
                System.out.println("Ronda " + nRonda + " - " + r.getRonda().getRon_fecha());
                
                int count = 0;
                
                for (Element par: tablasRondas)
                {
                    if (count == nRonda)
                    {
                        if (par.select("td:matches(\\.*Ronda.*)").size() > 0)
                        {
                            break;
                        }
                        else
                        {
                            if (r.getListaPartidas() == null) r.setListaPartidas(new ArrayList<PartidaDTO>());
                            r.getListaPartidas().add(getPartida(par,
                                                                idx_blancas,
                                                                idx_negras,
                                                                idx_resultado));                            
                        }
                    }
                    else
                    {
                        if (par.select("td:contains(M.)").size() > 0)
                        {
                            count++;
                        }
                    }
                }
                torneo.agregarRonda(r);
            }
        }
        else if (tipoTorneo == 2) // Suizo
        {
            tablasRondas = docRondas.select("div.defaultDialog > table.CRs1");
            Collections.reverse(tablasRondas);
            
            nroRonda = 1L;
                
            for (Element fila: tablasRondas)
            {
                RondaDTO r = new RondaDTO();
                r.setRonda(new Ronda());
                r.getRonda().setNumeroRonda(nroRonda);

                // Fecha de la ronda
                Elements elemFecha = docRondas.select("div.defaultDialog > h3.CR:matches(" + nroRonda + "\\..*)");
                if (elemFecha.size() > 0)
                {
                    r.getRonda().setRon_fecha(getFecha(elemFecha));
                }
                
                System.out.println("Ronda " + nroRonda + " - " + r.getRonda().getRon_fecha());
                
                Elements partidas = fila.select("tr");
                partidas.remove(0); // Quito la cabecera.
                
                for (Element par: partidas)
                {
                    if (r.getListaPartidas() == null) r.setListaPartidas(new ArrayList<PartidaDTO>());
                    
                    r.getListaPartidas().add(getPartida(par,
                                                        idx_blancas,
                                                        idx_negras,
                                                        idx_resultado));                    
                } // fin for (partidas)
                
                torneo.agregarRonda(r);
                nroRonda++;

            } // fin for (rondas)

        } // fin torneo Suizo

        return torneo;
    }
    
    private static PartidaDTO getPartida (Element par,
                                          int idx_blancas,
                                          int idx_negras,
                                          int idx_resultado)
    {
        Elements columnas = par.getElementsByTag("td");   
        
        String nroJugBlancas = columnas.get(idx_blancas).text();
        String nroJugNegras  = columnas.get(idx_negras).text();
        String resultado     = columnas.get(idx_resultado).text().trim();
        
        System.out.println(nroJugBlancas + " " + resultado + " " + nroJugNegras);
        
        Long resBlancas = null;
        Long resNegras = null;
        Long tipoResBlancas = null;
        Long tipoResNegras = null;
        
        if (resultado.equals("1") || resultado.equals("0") || resultado.equals("") || resultado.equals("&frac12;"))
        {
            tipoResBlancas = 3L; // bye
            tipoResNegras = 3L;  // bye
            
            resBlancas = resultado.equals("1")? 1L: (resultado.equals("0") || resultado.equals("")? 0L: 2L);
            resNegras = resultado.equals("&frac12;")? 2L: 0L;
        }
        else if (resultado.matches("[01+-]\\s-\\s[01+-]") || resultado.equals("&frac12; - &frac12;")) // 0 - 1, 1 - 0, 0 - 0, + - -, - - +, - - -
        {
            // Tipo de resultado
            if (resultado.startsWith("1") || resultado.startsWith("0") || resultado.startsWith("&frac12;"))
            {
                tipoResBlancas = 1L; // normal
                tipoResNegras = 1L;
            }
            else if (resultado.startsWith("+") || resultado.startsWith("-"))
            {
                tipoResBlancas = 2L; // ausencia
                tipoResNegras = 2L;
            }
            
            //Resultado
            resBlancas = (resultado.startsWith("1") || resultado.startsWith("+"))? 1L:
                         (resultado.startsWith("0") || resultado.startsWith("-") ? 0L: 2L);
            
            resNegras = (resultado.endsWith("1") || resultado.endsWith("+"))? 1L:
                        (resultado.endsWith("0") || resultado.endsWith("-") ? 0L: 2L);
        }
        
        PartidaDTO p = new PartidaDTO();
        p.setPartida(new Partida());
        
        p.getPartida().setPar_jug_blancas(Long.valueOf(nroJugBlancas));
        p.getPartida().setPar_jug_negras(nroJugNegras != null && !nroJugNegras.equals("") ? Long.valueOf(nroJugNegras): 0L);
        p.getPartida().setPar_resultado_blancas(resBlancas);
        p.getPartida().setPar_resultado_negras(resNegras);
        p.getPartida().setPar_tipo_res_blancas(tipoResBlancas);
        p.getPartida().setPar_tipo_res_negras(tipoResNegras);
        
        return p;
    }
    
    private static Date getFecha (Elements elemFecha)
    {
        Date ret = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Pattern pFecha = Pattern.compile("[0-9]{4}.[0-9]{2}.[0-9]{2}");
        String strFecha = elemFecha.get(0).text();
        
        Matcher m = pFecha.matcher(strFecha);
        if (m.find())
        {
            strFecha = m.group();
            try
            {
                ret = sdf.parse(strFecha);
            }
            catch (Exception e){
                e.printStackTrace();
                ret = null;
            }
        }        
        
        return ret;
    }
}
