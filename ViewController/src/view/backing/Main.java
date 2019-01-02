package view.backing;

import java.util.List;

import model.entities.Noticias;
import model.entities.Menu;

import view.managed.SessionBean;
import view.managed.UserInfo;

public class Main 
{
    private SessionBean session;
    private UserInfo userInfo;
    
    
    public List<Noticias> getUltimasNoticias()
    {
        List<Noticias> lista = session.getServicio().getUltimasNoticias();
        
        //Noticias not = session.getServicio().getNoticiaById(1L);
        //System.out.println(not.getNot_contenido());
        
        return lista;
    }
    
    public List<Menu> getMenuUsuario()
    {
        if (userInfo != null && userInfo.getUsuario() != null)
        {
            List<Menu> lista = session.getServicio().qMenuByRol(userInfo.getUsuario().getRol().getRol_id());
            return lista;
        }
        else
            return null;
    }
    
    public String noticia_Action ()
    {
        return "noticia";
    }
    
    public void setSession(SessionBean session)
    {
        this.session = session;
    }

    public SessionBean getSession()
    {
        return session;
    }

  public void setUserInfo(UserInfo userInfo)
  {
    this.userInfo = userInfo;
  }

  public UserInfo getUserInfo()
  {
    return userInfo;
  }
}
