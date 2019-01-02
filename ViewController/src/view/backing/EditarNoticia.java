package view.backing;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;

import java.io.File;
import java.io.InputStream;

import java.text.DecimalFormat;
import java.util.Date;

import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneRadio;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import javax.imageio.ImageIO;

import model.util.Constantes;

import net.coobird.thumbnailator.Thumbnails;

import org.richfaces.component.UIEditor;
import org.richfaces.component.UIFileUpload;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import view.managed.EditarNoticiaBean;
import view.managed.SessionBean;
import view.managed.UserInfo;

import view.services.FileService;

public class EditarNoticia 
{
    private SessionBean session;
    private UserInfo userInfo;
    
    private EditarNoticiaBean editarNoticiasBean;
    private HtmlInputTextarea txtNoticia;
    private HtmlInputHidden hdnX1;
    private HtmlInputHidden hdnY1;
    private HtmlInputHidden hdnHeight;
    private HtmlInputHidden hdnWidth;
    private HtmlInputText txtEpigrafe;
    private HtmlSelectOneRadio radioEpigrafe;
    private HtmlSelectBooleanCheckbox chkFbComments;
    
    private UIEditor txtEditor;
    private UIFileUpload imagenUpload;
    
    public String publicar_action()
    {
        publicarNoticia(true);
        return "volver";
    }    

    public String borrador_action()
    {
        publicarNoticia(false);
        return "volver";
    }    
    
    public String mantener_sesion_action()
    {
        System.out.println("Llamada a MantenerSesion()");
        return null;
    }
    
    private void publicarNoticia(boolean activa)
    {
        try
        {
            Boolean fbComments = (Boolean) this.getChkFbComments().getValue();
            
            if (editarNoticiasBean.getModoEdicion() == Constantes.MODO_EDICION_ALTAS)
            {
                String rutaImagen = copiarImagenPortada(editarNoticiasBean.getIdNoticia());
                copiarImagenesNoticia();
                
                session.getServicio()
                    .persistNoticia(editarNoticiasBean.getIdNoticia(),
                                    editarNoticiasBean.getTxtTitulo(), 
                                    editarNoticiasBean.getTxtResumen(), 
                                    editarNoticiasBean.getTxtContenido(),
                                    fbComments.equals(Boolean.valueOf(true))? "S": "N",
                                    rutaImagen,
                                    userInfo.getUsuario().getUsr_id(), 
                                    null,
                                    activa,
                                    1L);
            }
            else
            {
                boolean portadaCambio = editarNoticiasBean.getImagenPortada().indexOf("../imagenesLyF") != -1;
                String rutaImagen = null;
                if (portadaCambio)
                {
                    rutaImagen = copiarImagenPortada(editarNoticiasBean.getIdNoticia());
                }
                copiarImagenesNoticia();

                session.getServicio()
                    .mergeNoticia(editarNoticiasBean.getIdNoticia(),
                                    editarNoticiasBean.getTxtTitulo(), 
                                    editarNoticiasBean.getTxtResumen(), 
                                    editarNoticiasBean.getTxtContenido(),
                                    fbComments.equals(Boolean.valueOf(true))? "S": "N",
                                    rutaImagen,
                                    userInfo.getUsuario().getUsr_id(), 
                                    null,
                                    activa,
                                    1L);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
   }
    
    private void copiarImagenesNoticia()
    {
        DecimalFormat df8 = new DecimalFormat("00000000");
        String prefijoArchivos = "not_img_" + df8.format(editarNoticiasBean.getIdNoticia()) + "_";
        
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        String rutaOrigen = path + "imagenes" + File.separator + "temp" + File.separator;
        
        String rutaDestino = session.getRutaImagenes() + "noticias" + File.separator;
        
        File[] archivosImagenes = FileService.finder(rutaOrigen, prefijoArchivos, null);
        
        // TODO Verificar si la imagen fue eliminada del editor, para no copiarla al pedo al server.
        
        if (archivosImagenes != null && archivosImagenes.length > 0)
        {
            String nombreArchivo = null;
            String pathDestino = null;
            
            for (int i = 0; i < archivosImagenes.length; i++)
            {
                nombreArchivo = archivosImagenes[i].getName();
                pathDestino = rutaDestino + nombreArchivo;
                File fDestino = new File(pathDestino);
                
                FileService.FileCopy(archivosImagenes[i], fDestino);
            }
        }
        editarNoticiasBean.reemplazarRutasImagenes();
    }
    
    // Copia la imagen de portada desde la carpeta temporal a la carpeta /noticias con un nombre nuevo
    private String copiarImagenPortada(Long idNoticia)
    {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        
        String rutaDesde = path + "imagenes" + File.separator + "temp" + File.separator + "tmpPortadaThumb.jpg";
        DecimalFormat df = new DecimalFormat("00000000");
        
        String rutaHasta = session.getRutaImagenes() + "noticias" + File.separator + "img_portada_" + df.format(idNoticia) + ".jpg";
        
        File fOrigen = new File(rutaDesde);
        File fDestino = new File(rutaHasta);
        
        FileService.FileCopy(fOrigen, fDestino);
        
        String rutaImagen = "../imagenesLyF/noticias/img_portada_" + df.format(idNoticia) + ".jpg";
        return rutaImagen;
    }
    
    // Muestra vista previa de imagen a insertar en el editor.
    public void insertarImagen_listener (FileUploadEvent event) throws Exception
    {
        UploadedFile item = event.getUploadedFile();
        InputStream input = item.getInputStream();
        
        try
        {
            String nombreArchivoOrigen = item.getName();
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            String extension = (FileService.getExtension(nombreArchivoOrigen)).toLowerCase();

            // Ruta real de la imagen
            String urlarchivo = "imagenes" + File.separator + "temp" + File.separator + "tmpInsertarImagen." + extension;
            String tempNombreArchivo = path + urlarchivo;
            
            // Ruta relativa para la página
            String urlImagen = "imagenes/temp/tmpInsertarImagen." + extension + "?" + (new Date()).getTime();
            
            BufferedImage image = ImageIO.read(input);

            File fileOutput = new File (tempNombreArchivo);
            ImageIO.write(image, extension, fileOutput);
            
            editarNoticiasBean.setImagenInsert(urlImagen);
            editarNoticiasBean.setExtensionImagenSubida(extension);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void subirImagen_listener(FileUploadEvent event) throws Exception
    {
        UploadedFile item = event.getUploadedFile();
        InputStream input = item.getInputStream();
        
        try
        {
            String nombreArchivoOrigen = item.getName();
            
            String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
            String extension = (FileService.getExtension(nombreArchivoOrigen)).toLowerCase();
            
            String urlarchivo = "imagenes" + File.separator + "temp" + File.separator + "tmpPortadaCrop." + extension;
            String tempNombreArchivo = path + urlarchivo;
            
            // Añado un valor dinamico (time) para evitar que el browser use el caché
            String urlImagen = "imagenes/temp/tmpPortadaCrop." + extension + "?" + (new Date()).getTime();
            System.out.println(urlImagen);
            
            BufferedImage image = ImageIO.read(input);
            BufferedImage imageResized = null;
            
            int width = image.getWidth();
            int height = image.getHeight();
            if (width > 650 || height > 510)
                imageResized = resize(image, 650, 510);
            else
                imageResized = image;
            
            
            File fileOutput = new File (tempNombreArchivo);
            ImageIO.write(imageResized, "jpg", fileOutput);
            
            System.out.println(tempNombreArchivo);
            editarNoticiasBean.setImagenCrop(urlImagen);
            
        }
        catch (Exception e){
            e.printStackTrace();
            // TODO Mandar mensaje de error
        }
    }
    
    // Inserta imagen seleccionada en el editor
    public String insertarImagen_action()
    {
        DecimalFormat df = new DecimalFormat("000");
        DecimalFormat df8 = new DecimalFormat("00000000");
        Long idNoticia = editarNoticiasBean.getIdNoticia();
        
        // Copiar imagen insertada en carpeta temporal con nombre nuevo
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        editarNoticiasBean.setContadorImagenes(editarNoticiasBean.getContadorImagenes() + 1);
        String nombreArchivo = "not_img_" + df8.format(idNoticia) + "_" + df.format(editarNoticiasBean.getContadorImagenes()) 
                               + "." + editarNoticiasBean.getExtensionImagenSubida();
        
        String imageHtmlId = "not_img" + df.format(editarNoticiasBean.getContadorImagenes());
        
        String pathDestino = path + "imagenes" + File.separator + "temp" + File.separator + nombreArchivo;
        String urlImagenWeb = "imagenes/temp/" + nombreArchivo;
        
        String pathOrigen = path + editarNoticiasBean.getImagenInsert().substring(0, editarNoticiasBean.getImagenInsert().indexOf("?"));
        
        System.out.println("pathDestino: " + pathDestino);
        System.out.println("urlImagenWeb: " + urlImagenWeb);
        System.out.println("pathOrigen: " + pathOrigen);
        
        File fOrigen = new File(pathOrigen);
        File fDestino = new File(pathDestino);
        
        FileService.FileCopy(fOrigen, fDestino);
        
        if (editarNoticiasBean.getOptEpigrafe().equals("E"))
        {
            String epigrafe = (String)getTxtEpigrafe().getValue();
            editarNoticiasBean.setEpigrafe(epigrafe != null? epigrafe: "Ep&iacute;grafe");
        }
        editarNoticiasBean.setUltimaImagenSubida(urlImagenWeb);
        editarNoticiasBean.setUltimaImagenSubidaHtmlId(imageHtmlId);
        editarNoticiasBean.setImagenInsert(null);
        
        return null;
    }
    
    // Cambia la imagen de portada
    public String cambiarImagen_action ()
    {
        String cX1 = (String)hdnX1.getValue();
        String cY1 = (String)hdnY1.getValue();
        String cHeight = (String)hdnHeight.getValue();
        String cWidth = (String)hdnWidth.getValue();
        
        System.out.println(cX1 + " " + cY1 + " " + cHeight + " " + cWidth);
        
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        String urlarchivo = "imagenes" + File.separator + "temp" + File.separator + "tmpPortadaCrop.jpg";
        String urlarchivoThumb = "imagenes" + File.separator + "temp" + File.separator + "tmpPortadaThumb.jpg";
        
        String urlImagenThumb = "imagenes/temp/tmpPortadaThumb.jpg";
        
        String tempNombreArchivo = path + File.separator + urlarchivo;
        String tempNombreArchivoOutput = path + File.separator + urlarchivoThumb;
        
        System.out.println(tempNombreArchivo);
        System.out.println(tempNombreArchivoOutput);
        
        BufferedImage imagen;
        
        try
        {
            long x1 = Math.round(Double.parseDouble(cX1));
            int intX1 = (Long.valueOf(x1)).intValue();

            long y1 = Math.round(Double.parseDouble(cY1));
            int intY1 = (Long.valueOf(y1)).intValue();
            
            long h = Math.round(Double.parseDouble(cHeight));
            int intH = (Long.valueOf(h)).intValue();
            
            long w = Math.round(Double.parseDouble(cWidth));
            int intW = (Long.valueOf(w)).intValue();
            
            imagen = ImageIO.read(new File(tempNombreArchivo));
            BufferedImage croppedImage = cropMyImage(imagen, intW, intH, intX1, intY1);
            BufferedImage resizedImage = resize (croppedImage, 111, 98);
            
            File fileOutput = new File (tempNombreArchivoOutput);
            ImageIO.write(resizedImage, "jpg", fileOutput);
            
            editarNoticiasBean.setImagenPortada(urlImagenThumb);
            editarNoticiasBean.setCambioImagenPortada(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) 
    {
        try
        {
            return Thumbnails.of(img).size(newW, newH).asBufferedImage();
            //return Thumbnails.of(img).forceSize(newW, newH).asBufferedImage();
        }
        catch(Exception e){
            return null;
        }
    }

    static Rectangle clip;
    
    public static BufferedImage cropMyImage(BufferedImage img, int cropWidth, int cropHeight, int cropStartX, int cropStartY) throws Exception 
    {
        BufferedImage clipped = null;
        Dimension size = new Dimension(cropWidth, cropHeight);
         
        createClip(img, size, cropStartX, cropStartY);
         
        try 
        {
            int w = clip.width;
            int h = clip.height;
             
            System.out.println("Crop Width " + w);
            System.out.println("Crop Height " + h);
            System.out.println("Crop Location " + "(" + clip.x + "," + clip.y + ")");
             
            clipped = img.getSubimage(clip.x, clip.y, w, h);
         
        }
        catch (RasterFormatException rfe) {
            System.out.println("Raster format error: " + rfe.getMessage());
            return null;
        }
        return clipped;
    }
         
    private static void createClip(BufferedImage img, Dimension size, int clipX, int clipY) throws Exception 
    {
        boolean isClipAreaAdjusted = false;
         
        /**Checking for negative X Co-ordinate**/
        if (clipX < 0) {
        clipX = 0;
        isClipAreaAdjusted = true;
        }
        /**Checking for negative Y Co-ordinate**/
        if (clipY < 0) {
        clipY = 0;
        isClipAreaAdjusted = true;
        }
         
        /**Checking if the clip area lies outside the rectangle**/
        if ((size.width + clipX) <= img.getWidth()
        && (size.height + clipY) <= img.getHeight()) {
         
        /**
        * Setting up a clip rectangle when clip area
        * lies within the image.
        */
         
        clip = new Rectangle(size);
        clip.x = clipX;
        clip.y = clipY;
        } else {
         
        /**
        * Checking if the width of the clip area lies outside the image.
        * If so, making the image width boundary as the clip width.
        */
        if ((size.width + clipX) > img.getWidth())
        size.width = img.getWidth() - clipX;
         
        /**
        * Checking if the height of the clip area lies outside the image.
        * If so, making the image height boundary as the clip height.
        */
        if ((size.height + clipY) > img.getHeight())
        size.height = img.getHeight() - clipY;
         
        /**Setting up the clip are based on our clip area size adjustment**/
        clip = new Rectangle(size);
        clip.x = clipX;
        clip.y = clipY;
         
        isClipAreaAdjusted = true;
         
        }
        if (isClipAreaAdjusted)
        System.out.println("Crop Area Lied Outside The Image."
        + " Adjusted The Clip Rectangle\n");
    }    
    
    public void changeRadioEpigrafe(ValueChangeEvent valueChangeEvent) 
    {
        String valor = (String)valueChangeEvent.getNewValue();
        editarNoticiasBean.setOptEpigrafe(valor);
        System.out.println("Cambio de radio a " + valor);
    }
    
    public String volver_action()
    {
        return "volver";
    }
    
    public void setSession(SessionBean session) {
        this.session = session;
    }

    public SessionBean getSession() {
        return session;
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

    public void setTxtNoticia(HtmlInputTextarea txtNoticia)
    {
        this.txtNoticia = txtNoticia;
    }

    public HtmlInputTextarea getTxtNoticia()
    {
        return txtNoticia;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setHdnX1(HtmlInputHidden hdnX1) {
        this.hdnX1 = hdnX1;
    }

    public HtmlInputHidden getHdnX1() {
        return hdnX1;
    }

    public void setHdnY1(HtmlInputHidden hdnY1) {
        this.hdnY1 = hdnY1;
    }

    public HtmlInputHidden getHdnY1() {
        return hdnY1;
    }

    public void setHdnHeight(HtmlInputHidden hdnHeight) {
        this.hdnHeight = hdnHeight;
    }

    public HtmlInputHidden getHdnHeight() {
        return hdnHeight;
    }

    public void setHdnWidth(HtmlInputHidden hdnWidth) {
        this.hdnWidth = hdnWidth;
    }

    public HtmlInputHidden getHdnWidth() {
        return hdnWidth;
    }

    public void setTxtEpigrafe(HtmlInputText txtEpigrafe) {
        this.txtEpigrafe = txtEpigrafe;
    }

    public HtmlInputText getTxtEpigrafe() {
        return txtEpigrafe;
    }

    public void setRadioEpigrafe(HtmlSelectOneRadio radioEpigrafe) {
        this.radioEpigrafe = radioEpigrafe;
    }

    public HtmlSelectOneRadio getRadioEpigrafe() {
        return radioEpigrafe;
    }

    public void setImagenUpload(UIFileUpload imagenUpload) {
        this.imagenUpload = imagenUpload;
    }

    public UIFileUpload getImagenUpload() {
        return imagenUpload;
    }

    public void setChkFbComments(HtmlSelectBooleanCheckbox chkFbComments) 
    {
        //chkFbComments.setValue(Boolean.valueOf(true));
        
        if (editarNoticiasBean.getModoEdicion() == Constantes.MODO_EDICION_CAMBIOS)
        {
            if (editarNoticiasBean.getFbComments().equals("S")) {
                chkFbComments.setValue(Boolean.valueOf(true));
            }
        }
        
        this.chkFbComments = chkFbComments;
    }

    public HtmlSelectBooleanCheckbox getChkFbComments() {
        return chkFbComments;
    }
}
