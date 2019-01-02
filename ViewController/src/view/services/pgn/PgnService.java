package view.services.pgn;

import java.io.File;

import java.io.FileInputStream;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.entities.BasePgn;

public class PgnService 
{
    
    public static List<BasePgn> getBasePgnList (InputStream file) throws Exception
    {
        List<BasePgn> ret = new ArrayList<BasePgn>();
        
        PGNSource source = new PGNSource(file);
        Iterator<PGNGame> i = source.listGames().iterator();
        
        while (i.hasNext())
        {
                PGNGame game = i.next();
                
                Iterator<String> tagsIterator = game.getTagKeysIterator();
                
                while (tagsIterator.hasNext()) {
                        String key = tagsIterator.next();
                        System.out.println(key + " {" + game.getTag(key) + "}");
                }

                /*System.out.println("\nEvento: " + game.getBpgn_event());
                System.out.println("\nFecha: " + game.getBpgn_date());
                System.out.println("\nECO: " + game.getBpgn_eco());
                System.out.println("\nResult: " + game.getBpgn_result());
                System.out.println("\nBlancas: " + game.getBpgn_white());
                System.out.println("\nNegras: " + game.getBpgn_black());*/
                
                BasePgn b = new BasePgn(game.getBpgn_black(),
                                        game.getBpgn_date(),
                                        game.getBpgn_eco(),
                                        game.getBpgn_event(),
                                        null,
                                        game.getBpgn_result(),
                                        game.getBpgn_round(),
                                        game.getBpgn_white(),
                                        game.getPgn(),
                                        null);
               ret.add(b);
        }
        
        return ret;
    }
    
    //D:\Proyectos\LyF2\tmp
    
    public static void main(String[] args) throws Exception
    {
        PgnService serv = new PgnService();
        
        try
        {
            File f = new File ("D:\\Proyectos\\LyF2\\tmp\\prueba.pgn");
            List<BasePgn> result = null;
            
            if (f != null){
                result = serv.getBasePgnList(new FileInputStream(f));
                System.out.println("Total de partidas en archivo: " + result.size());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
