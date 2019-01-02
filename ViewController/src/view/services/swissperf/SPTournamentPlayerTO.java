package view.services.swissperf;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

// POJO para transferencia de datos de archivos .trn de SWISSPERFECT

public class SPTournamentPlayerTO
{
    private Long order;
    private Long id;
    private String startNro;
    private String surname;
    private String firstname;
    private String feder;
    private String club;
    
    private Long intlRtg;
    private Long locRtg;
    private Long locRtg2;
    private String intlId;
    private String locId;
    private String title;
    private String sex;
    private Date birthDate;
    
    private Long lateEntry;
    private Long withdrawal; // Nro. de Ronda en la que ya no juega por retirada
    private Long absent;
    private Long bonus;
    private Long techScore;
    private Long forbPairs;
    
    private Long team;
    private Long board;
    
    private String memo;
    
    private Long jug_id; // Id del Jugador en Base de Datos

    public SPTournamentPlayerTO(){
        
    }
    
    public SPTournamentPlayerTO(String[] lista)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("mmddyyyy");
        
        this.order = Long.valueOf(lista[0].trim());
        this.id = Long.valueOf(lista[1].trim());
        this.startNro = lista[2];
        this.surname = lista[3];
        this.firstname = lista[4];
        this.feder = lista[5];
        this.club = lista[6];
        this.intlRtg = !stringVacio(lista[7])? Long.valueOf(lista[7].trim()): null;
        this.locRtg = !stringVacio(lista[8])? Long.valueOf(lista[8].trim()): null;
        this.locRtg2 = !stringVacio(lista[9])? Long.valueOf(lista[9].trim()): null;
        this.intlId = lista[10];
        this.locId = lista[11];
        this.title = lista[12];
        this.sex = lista[13];
        this.lateEntry = !stringVacio(lista[15])? Long.valueOf(lista[15].trim()): null;
        this.withdrawal = !stringVacio(lista[16])? Long.valueOf(lista[16].trim()): null;
        this.absent = !stringVacio(lista[17])? Long.valueOf(lista[17].trim()): null;
        this.bonus = !stringVacio(lista[18])? Long.valueOf(lista[18].trim()): null;
        this.techScore = !stringVacio(lista[19])? Long.valueOf(lista[19].trim()): null;
        this.forbPairs = !stringVacio(lista[20])? Long.valueOf(lista[20].trim()): null;
        this.team = !stringVacio(lista[21])? Long.valueOf(lista[21].trim()): null;
        this.board = !stringVacio(lista[22])? Long.valueOf(lista[22].trim()): null;
        this.memo = lista[23];
        
        try
        {
            this.birthDate = lista[14] != null? sdf.parse(lista[14]): null;
        }
        catch (ParseException e)
        {
            this.birthDate = null;
        }
        
    }

    public void printPlayerData()
    {
        System.out.println("ORDEN       " + this.order);
        System.out.println("ID          " + this.id);
        System.out.println("START NRO   " + this.startNro);
        System.out.println("APELLIDO    " + this.surname);
        System.out.println("NOMBRE      " + this.firstname);
        
        System.out.println("LATE ENTRY  " + this.lateEntry);
        System.out.println("WITHDRAWAL  " + this.withdrawal);
        System.out.println("ABSENT      " + this.absent);
        System.out.println("BONUS       " + this.bonus);
        System.out.println("TECH SCORE  " + this.techScore);
        System.out.println("FORB PAIRS  " + this.forbPairs);
    }

    private boolean stringVacio(String str)
    {
        return str==null || str.trim().equals("");
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Long getOrder() {
        return order;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStartNro(String startNro) {
        this.startNro = startNro;
    }

    public String getStartNro() {
        return startNro;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFeder(String feder) {
        this.feder = feder;
    }

    public String getFeder() {
        return feder;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getClub() {
        return club;
    }

    public void setIntlRtg(Long intlRtg) {
        this.intlRtg = intlRtg;
    }

    public Long getIntlRtg() {
        return intlRtg;
    }

    public void setLocRtg(Long locRtg) {
        this.locRtg = locRtg;
    }

    public Long getLocRtg() {
        return locRtg;
    }

    public void setLocRtg2(Long locRtg2) {
        this.locRtg2 = locRtg2;
    }

    public Long getLocRtg2() {
        return locRtg2;
    }

    public void setIntlId(String intlId) {
        this.intlId = intlId;
    }

    public String getIntlId() {
        return intlId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public String getLocId() {
        return locId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setLateEntry(Long lateEntry) {
        this.lateEntry = lateEntry;
    }

    public Long getLateEntry() {
        return lateEntry;
    }

    public void setWithdrawal(Long withdrawal) {
        this.withdrawal = withdrawal;
    }

    public Long getWithdrawal() {
        return withdrawal;
    }

    public void setAbsent(Long absent) {
        this.absent = absent;
    }

    public Long getAbsent() {
        return absent;
    }

    public void setBonus(Long bonus) {
        this.bonus = bonus;
    }

    public Long getBonus() {
        return bonus;
    }

    public void setTechScore(Long techScore) {
        this.techScore = techScore;
    }

    public Long getTechScore() {
        return techScore;
    }

    public void setForbPairs(Long forbPairs) {
        this.forbPairs = forbPairs;
    }

    public Long getForbPairs() {
        return forbPairs;
    }

    public void setTeam(Long team) {
        this.team = team;
    }

    public Long getTeam() {
        return team;
    }

    public void setBoard(Long board) {
        this.board = board;
    }

    public Long getBoard() {
        return board;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemo() {
        return memo;
    }

    public void setJug_id(Long jug_id)
    {
        this.jug_id = jug_id;
    }

    public Long getJug_id()
    {
        return jug_id;
    }
}
