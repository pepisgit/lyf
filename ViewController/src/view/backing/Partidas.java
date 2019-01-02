package view.backing;

import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.html.HtmlForm;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import model.entities.BasePgn;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import view.managed.PartidasBean;
import view.managed.SessionBean;
import view.managed.UserInfo;

import view.services.pgn.PgnService;


public class Partidas {
    private SessionBean session;
    private UserInfo userInfo;
    private PartidasBean partidasBean;
    
    private HtmlForm formPartidas;
    private HtmlInputHidden hdnInicial;
    
    public void subirPartidas_listener(FileUploadEvent event) throws Exception 
    {
        UploadedFile item = event.getUploadedFile();
        
        try
        {
            PgnService servicio = new PgnService();
            List<BasePgn> lista = servicio.getBasePgnList(item.getInputStream());
            
            if (lista != null && lista.size() > 0)
            {
                System.out.println(String.valueOf(lista.size()) + " partidas leidas." );
                session.getServicio().persistPgnList(lista);
                System.out.println("Fin persistencia...");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public String verPartida_action() 
    {
        System.out.println("Ver partida");
        BasePgn partidaVisor = session.getServicio().qPartidaPgn(partidasBean.getPartidaId());
        partidasBean.setPartidaPgn(partidaVisor.getBpgn_pgn_text());
            
        System.out.println(partidasBean.getPartidaVisor());
        //partidasBean.setBlancas("Test");
        return null;
    }
    
    public String migrar_action()
    {
        //String archivoResultados = "D:\\TMP\\resultados_iiclasif2014.csv";
        String archvoPosciones = "D:\\TMP\\posiciones_iiclasif2014.csv";;
        //session.procesarXlsResultados(27L, archivoResultados);
        session.procesarXlsPosiciones(27L, archvoPosciones);
        return null;
    }
    
    public String guardarPartida_action() 
    {
        try
        {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
            String partida = null;
            
            String fileName = "ajedrez_lyf_partida.pgn";
            
            // TODO tirar query
            partida = getPartidasBean().getPartidaPgn();
            
            response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            response.setContentType("text/plain"); // Check http://www.w3schools.com/media/media_mimeref.asp for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
            response.setContentLength(partida.length()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.
    
            OutputStream output = response.getOutputStream();
            
            output.write(partida.getBytes());
            output.flush();
            output.close();
            fc.responseComplete();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;    
        }
        return null;
    }
    
    public List<BasePgn> getPartidasBasePgn()
    {
        List<BasePgn> ret = new ArrayList<BasePgn>();
        
        ret = session.getServicio().getBasePgn();
        
        return ret;
    }
    
    public List<SelectItem> getItemsEventos()
    {
        List<SelectItem> ret = new ArrayList<SelectItem>();
        List<String> listaEventos = session.getServicio().getPartidasEventos();
        
        SelectItem item = new SelectItem();
        item.setValue("");
        item.setLabel("");
        
        ret.add(item);
        
        
        if (listaEventos != null && listaEventos.size() > 0){
            for (String valor: listaEventos){
                item = new SelectItem();
                item.setValue(valor);
                item.setLabel(valor);
                
                ret.add(item);
            }
        }
        
        return ret;
    }

    public List<SelectItem> getItemsBlancas()
    {
        List<SelectItem> ret = new ArrayList<SelectItem>();
        List<String> listaEventos = session.getServicio().getPartidasBlancas();
        
        SelectItem item = new SelectItem();
        item.setValue("");
        item.setLabel("");
        
        ret.add(item);
        
        
        if (listaEventos != null && listaEventos.size() > 0){
            for (String valor: listaEventos){
                item = new SelectItem();
                item.setValue(valor);
                item.setLabel(valor);
                
                ret.add(item);
            }
        }
        
        return ret;
    }
    
    public List<SelectItem> getItemsNegras()
    {
        List<SelectItem> ret = new ArrayList<SelectItem>();
        List<String> listaEventos = session.getServicio().getPartidasNegras();
        
        SelectItem item = new SelectItem();
        item.setValue("");
        item.setLabel("");
        
        ret.add(item);
        
        
        if (listaEventos != null && listaEventos.size() > 0){
            for (String valor: listaEventos){
                item = new SelectItem();
                item.setValue(valor);
                item.setLabel(valor);
                
                ret.add(item);
            }
        }
        
        return ret;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public Object subirPartidas_action() {
        // Add event code here...
        return null;
    }

    public void setPartidasBean(PartidasBean partidasBean)
    {
        // Si entra a la página por primera vez, limpia el bean
        
        if (!FacesContext.getCurrentInstance().isPostback()){
            if (partidasBean != null){
                partidasBean.clear();
                System.out.println("Bean limpio...");
            }
        }
        
        System.out.println("Partidas bean");
        this.partidasBean = partidasBean;
    }

    public void unload(ActionEvent event) {
        if (partidasBean != null){
            partidasBean.clear();
            System.out.println("Bean limpio...");
        }        
    }

    public PartidasBean getPartidasBean() {
        return partidasBean;
    }

    public void setFormPartidas(HtmlForm formPartidas) {
        System.out.println("form partidas");
        this.formPartidas = formPartidas;
    }

    public HtmlForm getFormPartidas() {
        return formPartidas;
    }

    public void setHdnInicial(HtmlInputHidden hdnInicial) {
        System.out.println("Hdn inicial");
        this.hdnInicial = hdnInicial;
    }

    public HtmlInputHidden getHdnInicial() {
        return hdnInicial;
    }
}
