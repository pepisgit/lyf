package view.managed;

import java.util.List;

import model.dto.ArchivoDTO;

public class GestionarArchivosBean 
{
    private String ruta;
    private String nomDir;
    private String nomArchivo;
    
    private List<ArchivoDTO> listaArchivos;



    public void setListaArchivos(List<ArchivoDTO> listaArchivos) {
        this.listaArchivos = listaArchivos;
    }

    public List<ArchivoDTO> getListaArchivos() {
        return listaArchivos;
    }

    public void setRuta(String ruta)
    {
        this.ruta = ruta;
    }

    public String getRuta()
    {
        return ruta;
    }

    public void setNomDir(String nomDir)
    {
        this.nomDir = nomDir;
    }

    public String getNomDir()
    {
        return nomDir;
    }

    public void setNomArchivo(String nomArchivo)
    {
        this.nomArchivo = nomArchivo;
    }

    public String getNomArchivo()
    {
        return nomArchivo;
    }
}
