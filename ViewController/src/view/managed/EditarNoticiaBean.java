package view.managed;

import java.io.File;

import java.text.DecimalFormat;

import javax.faces.context.FacesContext;

import model.util.Constantes;

import view.services.FileService;

public class EditarNoticiaBean 
{
    private int modoEdicion; // 0 = Altas, 1 = Modificaciones
    
    private String txtContenido;
    private String txtResumen;
    private String txtTitulo;
    private String fbComments;
    
    private Long idNoticia; // Id de noticia, en caso de modificaciones
    
    private String imagenPortada;
    private String imagenCrop;
    private String imagenInsert;
    private String extensionImagenPortada;
    private String extensionImagenSubida;
    private String optEpigrafe;
    private String epigrafe;

    private String ultimaImagenSubida;
    private String ultimaImagenSubidaHtmlId;
    
    private Integer contadorImagenes;
    private boolean cambioImagenPortada;
    
    public void clean()
    {
       this.setTxtContenido(null);
       this.setTxtResumen(null);
       this.setTxtTitulo(null);
       //this.setIdNoticia(null);
       this.setImagenPortada(null);
       this.setImagenCrop(null);
       this.setImagenInsert(null);
       this.setExtensionImagenPortada(null);
       this.setExtensionImagenSubida(null);
       this.setUltimaImagenSubida(null);
       this.setCambioImagenPortada(false);
       this.setContadorImagenes(0);
    }

    public void reemplazarRutasImagenes()
    {
        DecimalFormat df8 = new DecimalFormat("00000000");
        String rutaTemp = "imagenes/temp/not_img_" + df8.format(this.idNoticia);
        
        //String rutaReal = "imagenes/noticias/not_img_" + df8.format(this.idNoticia);
        String rutaReal = "../imagenesLyF/noticias/not_img_" + df8.format(this.idNoticia);
        
        this.txtContenido = this.txtContenido.replaceAll(rutaTemp, rutaReal);
    }
    
    public void cargarImagenPortadaDefault()
    {
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/");
        String archivoImagen = path + Constantes.IMAGEN_PORTADA_DEFAULT;
        String archivoDestino = path + "imagenes" + File.separator + "temp" + File.separator + "tmpPortadaCrop.jpg";
        String archivoDestino2 = path + "imagenes" + File.separator + "temp" + File.separator + "tmpPortadaThumb.jpg";
        
        File fOrigen = new File(archivoImagen);
        File fDestino = new File(archivoDestino);
        File fDestino2 = new File(archivoDestino2);
        
        FileService.FileCopy(fOrigen, fDestino);
        FileService.FileCopy(fOrigen, fDestino2);
    }

    public void setModoEdicion(int modoEdicion) {
        this.modoEdicion = modoEdicion;
    }

    public int getModoEdicion() {
        return modoEdicion;
    }

    public void setTxtContenido(String txtContenido) {
        this.txtContenido = txtContenido;
    }

    public String getTxtContenido() {
        return txtContenido;
    }

    public void setTxtResumen(String txtResumen) {
        this.txtResumen = txtResumen;
    }

    public String getTxtResumen() {
        return txtResumen;
    }

    public void setTxtTitulo(String txtTitulo) {
        this.txtTitulo = txtTitulo;
    }

    public String getTxtTitulo() {
        return txtTitulo;
    }

    public void setIdNoticia(Long idNoticia) {
        this.idNoticia = idNoticia;
    }

    public Long getIdNoticia() {
        return idNoticia;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public void setImagenCrop(String imagenCrop) {
        this.imagenCrop = imagenCrop;
    }

    public String getImagenCrop() {
        return imagenCrop;
    }

    public void setExtensionImagenPortada(String extensionImagenPortada) {
        this.extensionImagenPortada = extensionImagenPortada;
    }

    public String getExtensionImagenPortada() {
        return extensionImagenPortada;
    }

    public void setCambioImagenPortada(boolean cambioImagenPortada) {
        this.cambioImagenPortada = cambioImagenPortada;
    }

    public boolean isCambioImagenPortada() {
        return cambioImagenPortada;
    }

    public void setImagenInsert(String imagenInsert) {
        this.imagenInsert = imagenInsert;
    }

    public String getImagenInsert() {
        return imagenInsert;
    }

    public void setOptEpigrafe(String optEpigrafe) {
        this.optEpigrafe = optEpigrafe;
    }

    public String getOptEpigrafe() {
        return optEpigrafe;
    }

    public void setUltimaImagenSubida(String ultimaImagenSubida) {
        this.ultimaImagenSubida = ultimaImagenSubida;
    }

    public String getUltimaImagenSubida() {
        return ultimaImagenSubida;
    }

    public void setExtensionImagenSubida(String extensionImagenSubida) {
        this.extensionImagenSubida = extensionImagenSubida;
    }

    public String getExtensionImagenSubida() {
        return extensionImagenSubida;
    }

    public void setContadorImagenes(Integer contadorImagenes) {
        this.contadorImagenes = contadorImagenes;
    }

    public Integer getContadorImagenes() {
        return contadorImagenes;
    }

    public void setEpigrafe(String epigrafe) {
        this.epigrafe = epigrafe;
    }

    public String getEpigrafe() {
        return epigrafe;
    }

    public void setUltimaImagenSubidaHtmlId(String ultimaImagenSubidaHtmlId)
    {
        this.ultimaImagenSubidaHtmlId = ultimaImagenSubidaHtmlId;
    }

    public String getUltimaImagenSubidaHtmlId()
    {
        return ultimaImagenSubidaHtmlId;
    }

    public void setFbComments(String fbComments) {
        this.fbComments = fbComments;
    }

    public String getFbComments() {
        return fbComments;
    }
}
