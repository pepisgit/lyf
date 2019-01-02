package view.services;

import com.google.gson.Gson;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dto.JugadorDTO;

import view.managed.EditarTorneoBean;

public class JsonServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        EditarTorneoBean bean = (EditarTorneoBean) request.getSession().getAttribute("editarTorneoBean");
        
        String origen = request.getParameter("origen");
        
        List <JugadorDTO> list = null;
        
        if (origen.equals("torneo"))
        {
            list = bean.getListaJugadores();
        }
        else if (origen.equals("todos"))
        {
            list = bean.getListaAllJugadores();
        }
        
        List <JugadorDTO> ret = new ArrayList<JugadorDTO>();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String query = normalizarCaracteres(request.getParameter("query"));
        String nombreApellido = null;
        
        for (JugadorDTO j: list)
        {
            nombreApellido = normalizarCaracteres(j.getApellidoNombre()).toUpperCase();
            if (nombreApellido.indexOf(query.toUpperCase()) != -1)
            {
                ret.add(j);
            }
        }

        if (ret.size() > 0)
            response.getWriter().write(new Gson().toJson(ret));
        else
            response.getWriter().write("");
    }
    
    private String normalizarCaracteres(String input) 
    {
        if (input==null) return null;
        
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii =    "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }    
}
