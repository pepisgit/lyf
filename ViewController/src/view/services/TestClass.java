package view.services;

import java.io.File;

import model.dto.TorneoDTO;

import view.services.swissperf.SPTournamentTO;
import view.services.swissperf.SwissPerfectService;

public class TestClass 
{
    public static void main(String[] args)
    {
        TestClass t = new TestClass();
        t.testCRService();
    }
    
    public void testCRService()
    {
        TorneoDTO t = null;
        
        try
        {
            t = ChessResultsService.readCRTournamentById(308079L);//222774 308079
            
            System.out.println(t.getNombreTorneo());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void test()
    {
        File iniFile = new File ("D:\\Mis Documentos\\Ajedrez\\swissperfect\\Archivo de Torneos para el GRAU\\0003 Torneos 2015\\065 Gran Torneo Abierto Fin de año\\Torneo Fin de Año.ini");
        File trnFile = new File ("D:\\Mis Documentos\\Ajedrez\\swissperfect\\Archivo de Torneos para el GRAU\\0003 Torneos 2015\\065 Gran Torneo Abierto Fin de año\\Torneo Fin de Año.trn");
        File scoFile = new File ("D:\\Mis Documentos\\Ajedrez\\swissperfect\\Archivo de Torneos para el GRAU\\0003 Torneos 2015\\065 Gran Torneo Abierto Fin de año\\Torneo Fin de Año.sco");
        
        SPTournamentTO torneo = SwissPerfectService.getSPTournament(trnFile, scoFile, iniFile);
        
        torneo.printTournamentInfo(true, true, true);
        
        return;
    }

}
