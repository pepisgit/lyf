package view.backing;

import java.io.File;

import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.context.FacesContext;

import model.entities.Noticias;
import model.util.Constantes;

import org.richfaces.component.UIEditor;

import view.managed.EditarNoticiaBean;
import view.managed.SessionBean;
import view.managed.UserInfo;

import view.services.FileService;

public class GestionarNoticias
{
    private SessionBean session;
    private UserInfo userInfo;
    private EditarNoticiaBean editarNoticiasBean;
    
    private UIEditor txtEditor;

    public List<Noticias> getNoticiasUsuario()
    {
        List<Noticias> lista = session.getServicio().getNoticiasPorUsuario(userInfo.getUsuario().getUsr_id());
        
        //Noticias not = session.getServicio().getNoticiaById(1L);
        //System.out.println(not.getNot_contenido());
        
        return lista;
    }
    
    public String nuevaNoticia_action()
    {
        // Test secuencia
        Long seqNoticia = session.getServicio().getSequence("get_seq_noticias()");
        
        //String test = "sdsds not_img_00000001_001.jpg sdfsdf fsf not_img_00000001_002.jpg fsdf fs not_img_00000001_003.jpg fsdf not_img_00000001_005.jpg fsnot_img_00000001_004.jpg";
        //Integer maxImg = maximoContadorImagen(1L, test);
        //System.out.println(maxImg);
        
        //System.out.println("entró a nuevaNoticia_action()...");
        String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        String rutaPlantillaDefault = ruta + Constantes.PLANTILLA_NOTICIA_DEFAULT;
        
        String plantillaDefault = FileService.getFileToString(rutaPlantillaDefault);
        //System.out.println(rutaPlantillaDefault);
        
        // Seteo el modo en ALTAS
        this.getEditarNoticiasBean().clean();
        this.getEditarNoticiasBean().setModoEdicion(0);
        this.getEditarNoticiasBean().setImagenPortada ("imagenes/temp/tmpPortadaThumb.jpg");
        this.getEditarNoticiasBean().setImagenCrop("imagenes/temp/tmpPortadaCrop.jpg");
        this.getEditarNoticiasBean().setTxtContenido(plantillaDefault);
        this.getEditarNoticiasBean().setOptEpigrafe("E");
        this.getEditarNoticiasBean().setContadorImagenes(new Integer(0));
        this.getEditarNoticiasBean().setIdNoticia(seqNoticia);
        
        this.getEditarNoticiasBean().cargarImagenPortadaDefault();
        
        return "editar";
    }
    
    public String editarNoticia_action()
    {
        // Seteo el modo en MODIFICACIONES
        this.getEditarNoticiasBean().clean();
        this.getEditarNoticiasBean().setModoEdicion(1);
        
        // Carga noticia en el bean de sesión
        Noticias noticia = session.getServicio().getNoticiaById(this.getEditarNoticiasBean().getIdNoticia());
        this.getEditarNoticiasBean().setTxtContenido(noticia.getNot_contenido());
        this.getEditarNoticiasBean().setTxtResumen(noticia.getNot_resumen());
        this.getEditarNoticiasBean().setTxtTitulo(noticia.getNot_titulo());
        this.getEditarNoticiasBean().setFbComments(noticia.getNot_fb_comments());
        this.getEditarNoticiasBean().setIdNoticia(noticia.getNot_id());
        this.getEditarNoticiasBean().setImagenPortada (noticia.getNot_foto());
        this.getEditarNoticiasBean().setImagenCrop("imagenes/temp/tmpPortadaCrop.jpg");
        
        this.getEditarNoticiasBean().setContadorImagenes(maximoContadorImagen(noticia.getNot_id(), noticia.getNot_contenido()));
        this.getEditarNoticiasBean().cargarImagenPortadaDefault();
        
        return "editar";
    }
    
    public String depurarNoticia_action()
    {
        Noticias noticia = session.getServicio().getNoticiaById(this.getEditarNoticiasBean().getIdNoticia());
        
        String contenido = noticia.getNot_contenido();
        String imagenPortadaOrigen = noticia.getNot_foto();
        String regex = "<img.*imagenes\\/noticia_\\d{3}_archivos.*\\.(jpg|bmp|png|gif|pgn|pdf|swf|js|xml)\"";
        
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
                    .matcher(contenido);
        
        while (m.find()) 
        {
            allMatches.add(m.group());
        }
        
        if (allMatches.size() > 0)
        {
            String rutaImagenOrigen = null;
            String rutaImagenDestino = null;
            String extensionImagen = null;
            String nombreImagenOrigen = null;
            String nombreImagenDestino = null;
            
            DecimalFormat df3 = new DecimalFormat("000");
            DecimalFormat df8 = new DecimalFormat("00000000");            
            
            File fOrigen = null;
            String ruta = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            int contador = 0;
            
            long lenAntes = 0;
            long lenDespues = 0;
            
            if (allMatches.size() > 0)
            {
                for (String match: allMatches)
                {
                    rutaImagenOrigen = ruta + match.substring(match.indexOf("imagenes"), match.lastIndexOf("\""));
                    String rutaOrigenHtml = match.substring(match.indexOf("imagenes"), match.lastIndexOf("\""));
                    
                    //System.out.println("Ruta Origen: [" + rutaImagenOrigen + "]");
                    fOrigen = new File(rutaImagenOrigen);
                    
                    if (fOrigen.exists())
                    {
                        contador++;
                        nombreImagenOrigen = fOrigen.getName();
                        extensionImagen = FileService.getExtension(nombreImagenOrigen);
                        
                        nombreImagenDestino = "not_img_" + df8.format(noticia.getNot_id()) + "_" + df3.format(contador) 
                                               + "." + extensionImagen;
                        rutaImagenDestino = session.getRutaImagenes() + "noticias" + File.separator + nombreImagenDestino;
                        
                        //System.out.println("Ruta destino: [" + rutaImagenDestino + "]");
                        
                        File fDestino = new File(rutaImagenDestino);
                        lenAntes = fDestino.length();
                        //System.out.println(fDestino.length());
                        
                        FileService.FileCopy(fOrigen, fDestino);
                        
                        //System.out.println(fDestino.length());
                        lenDespues = fDestino.length();
                        
                        
                        if (lenDespues > lenAntes) // Verifico si copió
                        {
                            contenido = contenido.replace(rutaOrigenHtml, "../imagenesLyF/noticias/" + nombreImagenDestino);
                        }
                    }
                }
                noticia.setNot_contenido(contenido);
                
                rutaImagenOrigen = ruta + imagenPortadaOrigen;
                fOrigen = new File(rutaImagenOrigen);
                
                if (fOrigen.exists())
                {
                    nombreImagenDestino = "img_portada_" + df8.format(noticia.getNot_id()) + "." + extensionImagen;
                    String nombreImagenHtml = "../imagenesLyF/noticias/" + nombreImagenDestino;
                    
                    rutaImagenDestino = session.getRutaImagenes() + "noticias" + File.separator + nombreImagenDestino;
                    File fDestino = new File(rutaImagenDestino);
                    FileService.FileCopy(fOrigen, fDestino);
                    
                    noticia.setNot_foto(nombreImagenHtml);
                }
                session.getServicio().merge(noticia);
            }
            
            //System.out.println(contenido);
        }
        
        return null;
    }

    /*
    String writerNames[] = ImageIO.getWriterFormatNames();
    for (int i=0; i<writerNames.length; i++)
        System.out.println("Formato: [" + writerNames[i] + "]");
    */
    
    public String desactivarNoticia_action()
    {
        Noticias noticia = session.getServicio().getNoticiaById(this.getEditarNoticiasBean().getIdNoticia());
        
        if (noticia != null)
        {
            try
            {
                session.getServicio().activacionNoticia(noticia.getNot_id(), false);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    public String activarNoticia_action()
    {
        Noticias noticia = session.getServicio().getNoticiaById(this.getEditarNoticiasBean().getIdNoticia());
        
        if (noticia != null)
        {
            try
            {
                session.getServicio().activacionNoticia(noticia.getNot_id(), true);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    public String eliminarNoticia_action()
    {
        Noticias noticia = session.getServicio().getNoticiaById(this.getEditarNoticiasBean().getIdNoticia());
        
        if (noticia != null)
        {
            try
            {
                session.getServicio().removeNoticia(noticia.getNot_id());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    private Integer maximoContadorImagen(Long idNoticia, String cadena)
    {
      DecimalFormat df8 = new DecimalFormat("00000000");
      boolean continua = true;
      Integer cont = 0;
      String patron = "not_img_" + df8.format(idNoticia) + "_";
      
      while (continua)
      {
          int indice = cadena.indexOf(patron);
          if (indice > -1)
          {
              String numeroImagen = cadena.substring(indice + 17, indice + 20);
              if (numeroImagen.matches("\\d+"))
              {
                  Integer num = Integer.valueOf(numeroImagen);
                  if (num > cont)
                      cont = num;
              }
              cadena = cadena.substring(cadena.indexOf(patron) + patron.length(), cadena.length());
          }
          else
              continua = false;
      }
      return cont;
    }
    
    public String getHabilitaDepuracion()
    {
        if (userInfo != null && userInfo.getUsuario() != null && 
            userInfo.getUsuario().getRol() != null)
        {
            if (userInfo.getUsuario().getRol().getRol_id() == Constantes.ROL_ADMIN)
            {
                return "S";
            }
        }
        return "N";
    }    
    
    public void setSession(SessionBean session)
    {
        this.session = session;
    }

    public SessionBean getSession()
    {
        return session;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setEditarNoticiasBean(EditarNoticiaBean editarNoticiasBean) {
        this.editarNoticiasBean = editarNoticiasBean;
    }

    public EditarNoticiaBean getEditarNoticiasBean() {
        return editarNoticiasBean;
    }


    public void setTxtEditor(UIEditor txtEditor) {
        this.txtEditor = txtEditor;
    }

    public UIEditor getTxtEditor() {
        return txtEditor;
    }
}
