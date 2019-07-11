package view.managed;

import java.io.Serializable;

public class PageBean implements Serializable
{
    private Long idNoticia;
    private Long seccion;
    
    private String showFacebookComments;

    private String direccionClub = "Maipú 267, Córdoba";

    public void setIdNoticia(Long idNoticia) {
        this.idNoticia = idNoticia;
    }

    public Long getIdNoticia() {
        return idNoticia;
    }

    public void setSeccion(Long seccion) {
        this.seccion = seccion;
    }

    public Long getSeccion() {
        return seccion;
    }

    public void setDireccionClub(String direccionClub)
    {
        this.direccionClub = direccionClub;
    }

    public String getDireccionClub()
    {
        return direccionClub;
    }

    public void setShowFacebookComments(String showFacebookComments) {
        this.showFacebookComments = showFacebookComments;
    }

    public String getShowFacebookComments() {
        return showFacebookComments;
    }
}
