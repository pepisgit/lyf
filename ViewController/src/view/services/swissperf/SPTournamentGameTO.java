package view.services.swissperf;

import java.util.List;

// POJO para transferencia de datos de archivos .sco de SWISSPERFECT

public class SPTournamentGameTO
{
    private Long round;
    
    private Long white; // Blancas (id del pojo SPTournamentPlayerTO)
    private Long black; // Negras  (id del pojo SPTournamentPlayerTO)

    private Long wScore; // Resultado blancas
    private Long bScore; // Resultado negras

    private Long wType; // Tipo de jugador Blancas
    private Long bType; // Tipo de jugador Negras

    private Long wSubsco; // Resultado desempate blancas
    private Long bSubsco; // Resultado desempate negras
    
    public SPTournamentGameTO(){
        
    }
    
    public SPTournamentGameTO(String[] lista)
    {
        this.round = !stringVacio(lista[0])? Long.valueOf(lista[0].trim()): null;
        this.white = !stringVacio(lista[1])? Long.valueOf(lista[1].trim()): null;
        this.black = !stringVacio(lista[2])? Long.valueOf(lista[2].trim()): null;
        this.wScore = !stringVacio(lista[3])? Long.valueOf(lista[3].trim()): null;
        this.bScore = !stringVacio(lista[4])? Long.valueOf(lista[4].trim()): null;
        this.wType = !stringVacio(lista[5])? Long.valueOf(lista[5].trim()): null;
        this.bType = !stringVacio(lista[6])? Long.valueOf(lista[6].trim()): null;
        this.wSubsco = !stringVacio(lista[7])? Long.valueOf(lista[7].trim()): null;
        this.bSubsco = !stringVacio(lista[8])? Long.valueOf(lista[8].trim()): null;
    }

    public void printGameData()
    {
        System.out.println("RONDA         " + this.round);
        System.out.println("BLANCAS       " + this.white);
        System.out.println("NEGRAS        " + this.black);
        System.out.println("SCORE BLANCAS " + this.wScore);
        System.out.println("SCORE NEGRAS  " + this.bScore);
        System.out.println("TIPO  BLANCAS " + this.wType);
        System.out.println("TIPO  NEGRAS  " + this.bType);
        System.out.println("SUBSC BLANCAS " + this.wSubsco);
        System.out.println("SUBSC NEGRAS  " + this.bSubsco);
    }

    private boolean stringVacio(String str)
    {
        return str==null || str.trim().equals("");
    }
    
    public void setRound(Long round) {
        this.round = round;
    }

    public Long getRound() {
        return round;
    }

    public void setWhite(Long white) {
        this.white = white;
    }

    public Long getWhite() {
        return white;
    }

    public void setBlack(Long black) {
        this.black = black;
    }

    public Long getBlack() {
        return black;
    }

    public void setWScore(Long wScore) {
        this.wScore = wScore;
    }

    public Long getWScore() {
        return wScore;
    }

    public void setBScore(Long bScore) {
        this.bScore = bScore;
    }

    public Long getBScore() {
        return bScore;
    }

    public void setWType(Long wType) {
        this.wType = wType;
    }

    public Long getWType() {
        return wType;
    }

    public void setBType(Long bType) {
        this.bType = bType;
    }

    public Long getBType() {
        return bType;
    }

    public void setWSubsco(Long wSubsco) {
        this.wSubsco = wSubsco;
    }

    public Long getWSubsco() {
        return wSubsco;
    }

    public void setBSubsco(Long bSubsco) {
        this.bSubsco = bSubsco;
    }

    public Long getBSubsco() {
        return bSubsco;
    }
}
