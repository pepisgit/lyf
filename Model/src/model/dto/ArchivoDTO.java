package model.dto;

public class ArchivoDTO
{
    private String tipo; // A = Archivo; D = Directorio
    private String nombre;
    private String tamano;
    
    
    private long tamanoEnBytes;
    private String ultimaModif;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamanoEnBytes(long tamanoEnBytes) {
        this.tamanoEnBytes = tamanoEnBytes;
    }

    public long getTamanoEnBytes() {
        return tamanoEnBytes;
    }

    public void setUltimaModif(String ultimaModif) {
        this.ultimaModif = ultimaModif;
    }

    public String getUltimaModif() {
        return ultimaModif;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String getTipo()
    {
        return tipo;
    }
}
