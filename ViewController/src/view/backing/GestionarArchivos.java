package view.backing;

import com.google.common.io.Files;

import java.awt.image.BufferedImage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.InputStream;

import java.io.OutputStream;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import javax.faces.component.html.HtmlInputHidden;

import javax.faces.component.html.HtmlInputText;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import javax.imageio.ImageIO;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import model.dto.ArchivoDTO;

import org.apache.commons.io.IOUtils;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import view.managed.GestionarArchivosBean;

import view.services.FileService;

import view.util.Util;

public class GestionarArchivos 
{
    private GestionarArchivosBean gestionarArchivosBean;
    private HtmlInputHidden hdnInit;
    private HtmlInputText txtRuta;
    
    public String lnkBuscar_action() 
    {
        try 
        {
            /*
            if (this.getHdnInit().getValue() == null)
            {
                String pathServlet = Util.getPathImagenes();
                this.txtRuta.setValue(pathServlet);
            }*/
            
            this.getHdnInit().setValue("R");
            this.armarLista();
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            Util.addMessageError("Ocurrió un error al intentar buscar la ruta.");
            return null;
        }
        return null;

    }    
    
    private void armarLista()
    {
        try
        {
            String ruta = this.getTxtRuta().getValue().toString();
            
            if ((ruta == null) || (ruta.trim().equals(""))) 
            {
                gestionarArchivosBean.setListaArchivos(null);
                return;
            }
            
            // Abro el directorio
            File dir = new File(ruta);
            
            // Controlo que la ruta exista
            if ((dir == null) || (!dir.exists())) 
            {
                Util.addMessageInfo("Ruta no encontrada.");
                gestionarArchivosBean.setListaArchivos(null);
                return;
            }
            
            // Subo la ruta a process
            gestionarArchivosBean.setRuta(ruta);
            
            // Creo la lista
            List<ArchivoDTO> lstArchivos = new ArrayList<ArchivoDTO>();
            
            if (dir.isFile()) 
            {
                // ES UN ARCHIVO
                ArchivoDTO d = new ArchivoDTO();
                d.setNombre(dir.getName());
                d.setTamanoEnBytes(dir.length());
                d.setTamano(this.tamanoArch(dir.length()));
                d.setUltimaModif((new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date(dir.lastModified())));
                lstArchivos.add(d);
            }
            else
            {
                // ES UN DIRECTORIO
                // Si la ruta no termina con barra agregarla
                String ultimo = ruta.substring(ruta.length()-1,ruta.length());
                if (!ultimo.equals(System.getProperty("file.separator"))) {
                    ruta = ruta + System.getProperty("file.separator");
                }
                
                // Recupero archivos del directorio
                String[] ficheros = dir.list();
                
                // Controlo que la ruta tenga algo
                if ((ficheros == null) || (ficheros.length == 0)) 
                {
                    Util.addMessageInfo("El directorio está vacio");
                    gestionarArchivosBean.setListaArchivos(null);
                    return;
                }
                
                // Cargo lista
                for (int i = 0; i < ficheros.length; i++) 
                {
                    File f = new File(ruta + ficheros[i]);
                    ArchivoDTO d = new ArchivoDTO();
                    d.setNombre(f.getName());
                    d.setUltimaModif((new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(new Date(f.lastModified())));
                    d.setTamanoEnBytes(f.length());
                    d.setTamano(this.tamanoArch(f.length()));
                    d.setTipo(f.isFile()? "A":"D");
                    
                    lstArchivos.add(d);
                }
                
            }
            
            // Cargatr lista de grilla
            gestionarArchivosBean.setListaArchivos(lstArchivos);
        }
        catch (Exception e)
        {
            Util.addMessageError("Ocurrió un error al intentar leer los archivos.");
        }
    }
    
    public String eliminar_action()
    {
        String nomArchivoDirectorio = gestionarArchivosBean.getNomArchivo();
        String ra = gestionarArchivosBean.getRuta();
        
        this.getTxtRuta().setValue(ra);
        // Si la ruta no termina con barra agregarla
        String ul = ra.substring(ra.length()-1,ra.length());
        if (!ul.equals(System.getProperty("file.separator"))) {
            ra = ra + System.getProperty("file.separator");
        }
        String finalUrl = ra + nomArchivoDirectorio;
        File archivoDirectorio = new File(finalUrl);
        if(archivoDirectorio.delete())
        {
            Util.addMessageInfo("El archivo ha sido borrado exitosamente.");
            this.armarLista();
        }
        else
        {
            Util.addMessageError("La operación no se pudo completar exitosamente.");
        }
        return null;
    }

    public void upload_listener (FileUploadEvent event) throws Exception
    {
        UploadedFile item = event.getUploadedFile();
        InputStream input = item.getInputStream();
        
        try
        {
            String nombreArchivoOrigen = item.getName();
            String path = gestionarArchivosBean.getRuta();
            String rutaDestino = path + nombreArchivoOrigen;

            File fileOutput = new File (rutaDestino);
            OutputStream output = new FileOutputStream(fileOutput);

            IOUtils.copy(input, output);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void refresh_listener(ActionEvent event)
    {
        System.out.println("Listener ajax");
        return;
    }

    private void descargaArchivo(String ubicacionArchivo, String nombreArchivo) 
    {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try 
        {
            // Recupero archivo
            File f = new File (ubicacionArchivo);
            
            // Recupero response
            FacesContext fctx = FacesContext.getCurrentInstance();
            HttpServletResponse res = (HttpServletResponse) fctx.getExternalContext().getResponse(); 
            ServletOutputStream out = res.getOutputStream();
            
            //Configurar el tipo de archivo.
            res.setContentType(new MimetypesFileTypeMap().getContentType(f));                                                
                                            
            //Configurar cabecera y nombre de archivo a desplegar en DialogBox.
            res.setHeader ("Content-Disposition", "attachment;filename=\"" + nombreArchivo + "\"");
            
            // Cargo archivo en Buffer de entrada
            bis = new BufferedInputStream((new FileInputStream(f)));
           
            // Cargar archivo en Buffer de salida
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead; 
            while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            Util.addMessageError("Ocurrió un error al intentar descargar el archivo.");
        } finally {
            try {
                // Cierro Streams
                bis.close();
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public String download_action() 
    {
        try 
        {
            String nomArch = gestionarArchivosBean.getNomArchivo();
            String ra = gestionarArchivosBean.getRuta();
            
            this.getTxtRuta().setValue(ra);
            // Si la ruta no termina con barra agregarla
            
            String ul = ra.substring(ra.length()-1,ra.length());
            if (!ul.equals(System.getProperty("file.separator"))) {
                ra = ra + System.getProperty("file.separator");
            }
            
            this.descargaArchivo(ra + nomArch, nomArch);
        }
        catch (Exception e)
        {
            Util.addMessageError("Ocurrió un error al intentar descargar el archivo.");
            e.printStackTrace();
            return null;
        }
        return null;

    }
    
    public String abrir_directorio_action()
    {
        try 
        {
            //Recupero nombre de Directorio
            String nomDir = gestionarArchivosBean.getNomDir();
            
            if (nomDir != null) 
            {
                String ra = gestionarArchivosBean.getRuta();
                this.getTxtRuta().setValue(ra);

                // Si la ruta no termina con barra agregarla
                String ul = ra.substring(ra.length()-1,ra.length());
                if (!ul.equals(System.getProperty("file.separator")))
                {
                    ra = ra + System.getProperty("file.separator");
                }
                
                this.getTxtRuta().setValue(ra + nomDir);
                gestionarArchivosBean.setNomDir(nomDir);
            }
            // Armar lista
            this.armarLista();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Util.addMessageError("Ocurrió un error al intentar explorar el directorio.");
            return null;
        }
        return null;
    }
    
    public String subir_action() 
    {
         try 
         {
             // Seteo la ruta en el txtRuta
             String ruta = gestionarArchivosBean.getRuta();
             this.getTxtRuta().setValue(ruta);
             
             // Controlo si no alcanzó al directorio raiz
             if ((ruta == null) || (ruta.trim().equals("")) || (ruta.trim().equals(System.getProperty("file.separator")))) 
             {
                 Util.addMessageInfo("Ha alcanzado el directorio raíz.");
                 return null;
             }
             
             File f = new File(ruta);
             if (f.getParent() == null)
             {
                 Util.addMessageInfo("Ha alcanzado el directorio raíz.");
                 return null;
             }
             
             // Setear nueva ruta
             this.getTxtRuta().setValue(f.getParent());
             // Armar lista
             this.armarLista();
         }
         catch (Exception e)
         {
             Util.addMessageError("Ocurrió un error al intentar subir al directorio padre.");
             e.printStackTrace();
             return null;
         }
         return null;

    }    
    
    private String tamanoArch (long valor) {
        try {
            double tam = valor;
            if (tam < 1073741824.0) {
                if (tam < 1048576.0) {
                    if (tam < 1024.0) {
                        return (new DecimalFormat("#,##0").format(tam) + "B");
                    } else {
                        tam = Util.round((tam/1024.0), 2);
                        return (new DecimalFormat("#,###,##0.00").format(tam) + "KB");
                    }
                } else {
                    tam = Util.round((tam/1048576.0), 2);
                    return (new DecimalFormat("#,###,###,##0.00").format(tam) + "MB");
                }
            } else {
                tam = Util.round((tam/1073741824.0), 2);
                return (new DecimalFormat("###,###,###,##0.00").format(tam) + "GB");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Util.addMessageError("Ocurrió un error al intentar determinar el tamaño del archivo.");
            return "0B";
        }
    }    
    
    public void setGestionarArchivosBean(GestionarArchivosBean gestionarArchivosBean)
    {
        this.gestionarArchivosBean = gestionarArchivosBean;
    }

    public GestionarArchivosBean getGestionarArchivosBean()
    {
        return gestionarArchivosBean;
    }

    public void setHdnInit(HtmlInputHidden hdnInit)
    {
        this.hdnInit = hdnInit;
    }

    public HtmlInputHidden getHdnInit()
    {
        return hdnInit;
    }

    public void setTxtRuta(HtmlInputText txtRuta)
    {
        this.txtRuta = txtRuta;
        this.txtRuta.setValue(Util.getPathImagenes());
    }

    public HtmlInputText getTxtRuta()
    {
        return txtRuta;
    }
}
