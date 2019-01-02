package view.backing;

import java.io.File;
import java.io.FileInputStream;

import java.io.OutputStream;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpServletResponse;

import model.dto.JugadorDTO;

import model.entities.GrauHistorial;
import model.entities.GrauRanking;
import model.entities.Jugador;

import view.managed.RankingBean;
import view.managed.SessionBean;

import view.util.Util;

public class Ranking 
{
    private SessionBean session;
    private RankingBean bean;
    
    public List<JugadorDTO> getListaRanking()
    {
        List<JugadorDTO> lista = null;
        try
        {
            lista = session.getServicio().getJugadoresRankingDTO();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<GrauRanking> getGrauRanking()
    {
        List<GrauRanking> lista = null;
        try
        {
            lista = session.getServicio().qGrauRanking(null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lista;        
    }
    
    public String verHistorial_action()
    {
        Long idJugador = bean.getJugador().getJugId();
        
        List<GrauHistorial> historial = session.getServicio().qGrauHistorial(idJugador);
        bean.setHistorial(historial);
        
        return null;
    }
    
    public void downloadFile(ActionEvent event) {
     
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
     
            int read = 0;
     
            String rutaPdf = session.getRutaImagenes() + "Ranking" + File.separator;
            
            // TODO Chequear existencia
            File f = new File (rutaPdf + bean.getPfdMostrar());
            if (!f.exists())
            {
                Util.addMessageInfo("No hay datos para este informe.");
                return;
            }
            
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + bean.getPfdMostrar() + "\"");
     
            try 
            {
                FileInputStream fis = new FileInputStream(rutaPdf + bean.getPfdMostrar());
                OutputStream os = null;
                byte[] bytes1 = new byte[1024];
     
                os = response.getOutputStream();
     
                while ((read = fis.read(bytes1)) != -1) {
                    os.write(bytes1, 0, read);
                }
     
                os.flush();
                os.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            FacesContext.getCurrentInstance().responseComplete();
    }    

    public String downloadFileAction()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
 
        int read = 0;
 
        String rutaPdf = session.getRutaImagenes() + "Ranking" + File.separator;

        File f = new File (rutaPdf + bean.getPfdMostrar());
        if (!f.exists())
        {
            Util.addMessageInfo("No hay datos para este informe.");
            return null;
        }
 
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + bean.getPfdMostrar() + "\"");
 
        try 
        {
            FileInputStream fis = new FileInputStream(rutaPdf + bean.getPfdMostrar());
            OutputStream os = null;
            byte[] bytes1 = new byte[1024];
 
            os = response.getOutputStream();
 
            while ((read = fis.read(bytes1)) != -1) {
                os.write(bytes1, 0, read);
            }
 
            os.flush();
            os.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().responseComplete();
        
        return null;
    }    

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setBean(RankingBean bean) {
        this.bean = bean;
    }

    public RankingBean getBean() {
        return bean;
    }
}
