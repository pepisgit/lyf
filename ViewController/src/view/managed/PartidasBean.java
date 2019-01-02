package view.managed;

public class PartidasBean 
{
    private String partidaPgn;
    private String blancas;
    private String negras;
    private String resultado;
    
    private Integer partidaId;
    
    private String filtroEvento;
    private String filtroBlancas;
    private String filtroNegras;
    private String filtroECO;

    public void clear(){
        this.partidaPgn = null;
        this.blancas = null;
        this.negras = null;
        this.resultado = null;
    }

    public String getPartidaVisor() {
        return partidaPgn != null? partidaPgn.replaceAll("[\n\r]"," "): null;
    }

    public void setBlancas(String blancas) {
        this.blancas = blancas;
    }

    public String getBlancas() {
        return blancas;
    }

    public void setNegras(String negras) {
        this.negras = negras;
    }

    public String getNegras() {
        return negras;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setPartidaPgn(String partidaPgn) {
        this.partidaPgn = partidaPgn;
    }

    public String getPartidaPgn() {
        return partidaPgn;
    }

    public void setFiltroEvento(String filtroEvento) {
        this.filtroEvento = filtroEvento;
    }

    public String getFiltroEvento() {
        return filtroEvento;
    }

    public void setFiltroBlancas(String filtroBlancas) {
        this.filtroBlancas = filtroBlancas;
    }

    public String getFiltroBlancas() {
        return filtroBlancas;
    }

    public void setFiltroNegras(String filtroNegras) {
        this.filtroNegras = filtroNegras;
    }

    public String getFiltroNegras() {
        return filtroNegras;
    }

    public void setFiltroECO(String filtroECO) {
        this.filtroECO = filtroECO;
    }

    public String getFiltroECO() {
        return filtroECO;
    }

    public void setPartidaId(Integer partidaId)
    {
        this.partidaId = partidaId;
    }

    public Integer getPartidaId()
    {
        return partidaId;
    }
}
