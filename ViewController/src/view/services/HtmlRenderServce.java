package view.services;

import java.text.DecimalFormat;

import java.util.List;

import model.dto.JugadorTorneoDTO;

import model.entities.JugadorTorneo;
import model.entities.Partida;
import model.entities.PremioTorneo;
import model.entities.Ronda;
import model.entities.Torneo;

import model.util.Constantes;


public class HtmlRenderServce 
{
    
    public static String getHtmlTournamentPrizes(List<PremioTorneo> listaPremios, Long torId)
    {
        StringBuffer ret = new StringBuffer("");
        
        if (listaPremios != null && listaPremios.size() > 0)
        {
            ret.append("<div align=center id='dbdata:prm_" + torId + "'>\n");
            ret.append("<table border=0 cellpadding=0 cellspacing=0 \n");
            
            // Encabezado
            ret.append("<tr height=17>\n<td bgcolor=#f0f1f4 align=center colspan=3 height=17 width=398 style='border-left:1px solid; border-right:1px solid; border-top:1px solid; border-bottom:1px solid'>\n");
            ret.append("<b>Premios</b>");
            ret.append("</td>\n</tr>");
            
            for(PremioTorneo pr: listaPremios)
            {
                ret.append("<tr height=17>\n");
                
                // Logro

                ret.append ("<td align=left height=17 style='border-top:none;border-left:1px solid;border-right:none;border-bottom:1px solid;padding-right:12px;'>\n");
                if (pr.getPre_descripcion() != null)
                {
                    ret.append(pr.getPre_descripcion());
                }
                else
                    ret.append ("?");
                
                ret.append("</td>\n");
                
                // Jugador
                
                ret.append ("<td align=left height=17 style='border-top:none;border-left:1px solid;border-right:1px solid;border-bottom:1px solid; padding-left:8px;'>\n");
                
                if (pr.getApellido() != null || pr.getNombre() != null)
                {
                    ret.append((pr.getApellido() != null? pr.getApellido(): "?") + ", " +
                               (pr.getNombre() != null? pr.getNombre(): "?"));
                }
                else
                    ret.append("?");
                
                ret.append("</td>\n");
                
                // Premio
                
                ret.append("<td align=left ");
                ret.append ("style='border-top:none;border-left:none;border-right:1px solid;border-bottom:1px solid;'>\n");
                
                if (pr.getPre_premio() != null)
                    ret.append (pr.getPre_premio());
                else
                    ret.append ("Sin datos.");
                
                ret.append("</td></tr>\n");
            }
            
            ret.append("</table>\n");
            ret.append("</div>\n");
        }
        
        return ret.toString();
    }
    
    public static String getHtmlTorunamentStandings(List<JugadorTorneoDTO> listaJugadores)
    {
        StringBuffer ret = new StringBuffer(""); 
        DecimalFormat df = new DecimalFormat("#0.0");
        
        
        if (listaJugadores != null && listaJugadores.size() > 0)
        {
            ret.append("<div align=center>\n");
            ret.append("<table border=0 cellpadding=0 cellspacing=0 width=350>\n");

            ret.append("<col width=50><col class=columnaJugador width=225><col class=columnaPuntos width=75 >\n");
            
            // Encabezado
            ret.append("<tr height=17>\n<td bgcolor=#f0f1f4 align=center colspan=3 height=17 width=398 style='border-left:1px solid; border-right:1px solid; border-top:1px solid; border-bottom:1px solid'>\n");
            ret.append("<b>Posiciones</b>");
            ret.append("</td>\n</tr>");
            
            for (JugadorTorneoDTO j: listaJugadores)
            {
                ret.append("<tr height=17>\n");

                // Posición

                ret.append ("<td align=right height=17 style='border-top:none;border-left:1px solid;border-right:none;border-bottom:1px solid;padding-right:12px;'>\n");
                if (j.getPosicionFinal() != null)
                {
                    ret.append(j.getPosicionFinal());
                }
                else
                    ret.append ("?");
                
                ret.append("</td>\n");
                
                // Jugador
                
                ret.append ("<td align=left height=17 style='border-top:none;border-left:1px solid;border-right:1px solid;border-bottom:1px solid; padding-left:8px;'>\n");
                
                ret.append((j.getJugApellido() != null? j.getJugApellido(): "?") + ", " +
                           (j.getJugNombre() != null? j.getJugNombre(): "?"));
                
                ret.append("</td>\n");
                
                // Puntaje
                
                ret.append("<td align=center ");
                ret.append ("style='border-top:none;border-left:none;border-right:1px solid;border-bottom:1px solid;'>\n");
                
                if (j.getPuntajeFinal() != null)
                    ret.append (df.format(j.getPuntajeFinal()));
                else
                    ret.append ("?");
                
                ret.append("</td></tr>\n");
                
            }
            
            ret.append("</table>\n");
            ret.append("</div>\n");
        }
        
        return ret.toString();
    }
    
    public static String getHtmlResultsTournament(Torneo t)
    {
        StringBuffer ret = new StringBuffer(""); 
        
        if (t.getRondas() != null && t.getRondas().size() > 0)
        {
            String htmlRonda = "";
            for (Ronda ronda: t.getRondas())
            {
                htmlRonda = getHtmlResultsRound(ronda);
                ret.append(htmlRonda);
                
                ret.append("\n<br/>");
            }
        }
        else
            ret.append("Sin Datos");
        
        return ret.toString();
    }
    
    
    public static String getHtmlResultsRound(Ronda ronda)
    {
        StringBuffer ret = new StringBuffer("");
        List<Partida> partidas;
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if (ronda != null)
        {
            partidas = ronda.getPartidas();
        
            if (partidas != null && partidas.size() > 0)
            {
                ret.append("<div align=center>\n");
                ret.append("<table border=0 cellpadding=0 cellspacing=0 width=500>\n");
                
                ret.append("<col class=columnaResultado width=215><col class=columnaResultado width=34 span=2><col class=columnaResultado width=215 >\n");
                
                // Encabezado
                ret.append("<tr height=17>\n<td bgcolor=#f0f1f4 align=center class=celdaResultado colspan=4 height=17 width=498 style='border-left:1px solid; border-right:1px solid; border-top:1px solid; border-bottom:1px solid'>\n");
                ret.append("<b>Ronda " + ronda.getRon_id() + "</b>");
                ret.append("</td>\n</tr>");
                
                String jugadorBlancas = "?";
                String jugadorNegras = "?";
                
                // Partidas
                for (Partida p: partidas)
                {
                    ret.append("<tr height=17>\n<td align=right class=celdaResultado height=17 style='border-top:none;border-left:1px solid;border-right:none;border-bottom:1px solid;padding-right:8px;' >\n");
                    
                    if (p.getJugadorBlancas() != null)
                        jugadorBlancas = p.getJugadorBlancas().getJugApellido() + ", " + p.getJugadorBlancas().getJugNombre();
                    
                    if (p.getJugadorNegras() != null)
                        jugadorNegras = p.getJugadorNegras().getJugApellido() + ", " + p.getJugadorNegras().getJugNombre();
                    
                    ret.append(jugadorBlancas);
                    ret.append("</td>\n");
                    
                    ret.append("<td align=center class=celdaResultado style='border-top:none;border-left:1px solid;border-right:none;border-bottom:1px solid;' >\n");
                    String resultBlancas = "";
                    String resultNegras = "";
                    
                    resultBlancas = getLayoutResult(p.getPar_resultado_blancas() , p.getPar_tipo_res_blancas());
                    resultNegras  = getLayoutResult(p.getPar_resultado_negras(), p.getPar_tipo_res_negras());
    
                    ret.append(resultBlancas + "</td>\n");
                    ret.append("<td align=center class=celdaResultado style='border-top:none;border-left:1px solid;border-right:1px solid;border-bottom:1px solid;'>\n");
                    ret.append(resultNegras + "</td>\n");
                    
                    ret.append("<td align=left class=celdaResultado height=17 style='border-top:none;border-left:none;border-right:1px solid;border-bottom:1px solid;padding-left:8px;'>\n");
                    if (!p.getPar_tipo_res_blancas().equals(Long.valueOf(Constantes.TIPO_RESULTADO_BYE)))
                        ret.append(jugadorNegras);
                    else
                        ret.append("BYE");
                    
                    ret.append("</td></tr>\n");
                }
                
                ret.append("</table>\n");
                ret.append("</div>\n");
            }
        }

        return ret.toString();
    }
    
    public static String getLayoutResult (Long resultado, Long tipo)
    {
        String ret = null;
        
        ret = resultado != null? resultado.toString():"?";
        if (ret.equals("2"))
            ret = "&#189;"; // Símbolo 1/2
        
        if (tipo.equals(Long.valueOf(Constantes.TIPO_RESULTADO_AUSENCIA)))
            if(ret.equals("1"))
                ret = "+";
            else if (ret.equals("0"))
                ret = "-";
        
        return ret;
    }
}
