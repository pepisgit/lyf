package view.util;

import java.io.File;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;

public class Util
{
    public static void addMessageError (String mensaje)
    {
        FacesMessage msgNoInput = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensaje);
        FacesContext.getCurrentInstance().addMessage("globalForm", msgNoInput);          
    }

    public static void addMessageInfo (String mensaje)
    {
        FacesMessage msgNoInput = new FacesMessage(FacesMessage.SEVERITY_INFO, null, mensaje);
        FacesContext.getCurrentInstance().addMessage("globalForm", msgNoInput);          
    }
    
    public static void addMessageWarning (String mensaje)
    {
        FacesMessage msgNoInput = new FacesMessage(FacesMessage.SEVERITY_WARN, null, mensaje);
        FacesContext.getCurrentInstance().addMessage("globalForm", msgNoInput);          
    }
    
    public static String getPathImagenes()
    {
       String ret = null;
       
       ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
       String path = servletContext.getRealPath("/");
       
       File dir = new File (path);
       String parent = dir.getParent();
       ret = parent + File.separator + "imagenesLyF" + File.separator;

       return ret;
    }

    public static double round(double valor, int decimales) {
        double elevado_a_10 = 1;
        while (decimales-- > 0) elevado_a_10 *= 10.0;
        return Math.round(valor * elevado_a_10) / elevado_a_10;
    }    
}
