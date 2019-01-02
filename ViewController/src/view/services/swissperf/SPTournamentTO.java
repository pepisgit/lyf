package view.services.swissperf;

import java.util.List;

// POJO para transferencia de datos de archivos .ini de SWISSPERFECT

public class SPTournamentTO 
{
    private String name;
    private String arbiter;
    private String organiser;

    private Long rounds;
    private Long multigames;
    private Long system;
    private Long teamOrIndividual;
    private Long useMinorScores;
    
    private List<SPTournamentPlayerTO> listPlayers;
    private List<SPTournamentGameTO> listGames;
    
    private String msg;

    
    public void printTournamentInfo(boolean tourn, boolean players, boolean games)
    {
        if(tourn)
        {
            System.out.println("DATOS DEL TORNEO:\n\n");
            this.printTournamentData();
            System.out.println();
        }
        if (players)
        {
            System.out.println("LISTA DE JUGADORES\n\n");
            this.printPlayersList();
            System.out.println();
        }
        if (games)
        {
            System.out.println("LISTA DE PARTIDAS\n\n");
            this.printGamesList();
            System.out.println();
        }
    }
    
    public void printTournamentData()
    {
        System.out.println("NOMBRE:       " + this.name);
        System.out.println("ARBITRO:      " + this.arbiter);
        System.out.println("ORGANIZADOR:  " + this.organiser);
        System.out.println("RONDAS:       " + this.rounds);
        System.out.println("SISTEMA:      " + this.system);
        System.out.println("EQUIPO O IND: " + this.teamOrIndividual);
        System.out.println("USE MINOR SCR:" + this.useMinorScores);
    }

    public void printPlayersList()
    {
        if (listPlayers != null && listPlayers.size() > 0)
        {
            for (SPTournamentPlayerTO player: listPlayers)
            {
                player.printPlayerData();
                System.out.println();
            }
        }
    }

    public void printGamesList()
    {
        if (listGames != null && listGames.size() > 0)
        {
            for (SPTournamentGameTO game: listGames)
            {
                game.printGameData();
                System.out.println();
            }
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setArbiter(String arbiter) {
        this.arbiter = arbiter;
    }

    public String getArbiter() {
        return arbiter;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setMultigames(Long multigames) {
        this.multigames = multigames;
    }

    public Long getMultigames() {
        return multigames;
    }

    public void setSystem(Long system) {
        this.system = system;
    }

    public Long getSystem() {
        return system;
    }

    public void setTeamOrIndividual(Long teamOrIndividual) {
        this.teamOrIndividual = teamOrIndividual;
    }

    public Long getTeamOrIndividual() {
        return teamOrIndividual;
    }

    public void setUseMinorScores(Long useMinorScores) {
        this.useMinorScores = useMinorScores;
    }

    public Long getUseMinorScores() {
        return useMinorScores;
    }

    public void setListPlayers(List<SPTournamentPlayerTO> listPlayers) {
        this.listPlayers = listPlayers;
    }

    public List<SPTournamentPlayerTO> getListPlayers() {
        return listPlayers;
    }

    public void setListGames(List<SPTournamentGameTO> listGames) {
        this.listGames = listGames;
    }

    public List<SPTournamentGameTO> getListGames() {
        return listGames;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setRounds(Long rounds) {
        this.rounds = rounds;
    }

    public Long getRounds() {
        return rounds;
    }
}
