package view.backing;

import view.managed.SessionBean;

public class Cronograma
{
    private SessionBean session;
    
    public String getHtmlCronograma()
    {
        String ret = null;
        try
        {
            ret = session.getServicio().getParametro(30L, 1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public SessionBean getSession() {
        return session;
    }
}
