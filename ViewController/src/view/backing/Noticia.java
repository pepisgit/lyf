package view.backing;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.component.html.HtmlSelectBooleanCheckbox;

import javax.servlet.http.HttpServletRequest;

import model.entities.Noticias;

import view.managed.PageBean;
import view.managed.SessionBean;

@ManagedBean(name="backing_noticia")
@RequestScoped
public class Noticia
{
    private SessionBean session;
    private PageBean pageBean;
    
    public String getNoticiaActual()
    {
        javax.faces.context.FacesContext facesContext = javax.faces.context.FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();
        
        String idNoticia = request.getParameter("idnoticia");

        if (idNoticia == null){
            idNoticia = pageBean.getIdNoticia().toString();
        }
        else{
            pageBean.setIdNoticia(Long.valueOf(idNoticia));
        }
        //System.out.println("Id Noticia: " + pageBean.getIdNoticia());
        
        Noticias not = session.getServicio().getNoticiaById(Long.valueOf(idNoticia));
        pageBean.setShowFacebookComments(not.getNot_fb_comments());
        //Noticias not = session.getServicio().getNoticiaById(pageBean.getIdNoticia());
        
        return not.getNot_contenido();
    }
    
    public String getFacebookCommentDiv()
    {
        //String urlNoticia = "http://68.169.55.209:8080/clublyf/noticia.jsf?idnoticia=" + pageBean.getIdNoticia();
        String urlNoticia = "http://ajedrezpensado.com.ar?idn=" + pageBean.getIdNoticia();
        String ret = "<div class=\"fb-comments\" data-href=\"" + urlNoticia + "\" data-num-posts=\"5\" data-width=\"100%\"></div>";
        
        System.out.println(ret);
        
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

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public PageBean getPageBean() {
        return pageBean;
    }

}
