package view.services.swissperf;


import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nl.knaw.dans.common.dbflib.CorruptedTableException;
import nl.knaw.dans.common.dbflib.DbfLibException;
import nl.knaw.dans.common.dbflib.Field;
import nl.knaw.dans.common.dbflib.IfNonExistent;
import nl.knaw.dans.common.dbflib.Record;
import nl.knaw.dans.common.dbflib.Table;
import nl.knaw.dans.common.dbflib.ValueTooLargeException;

import org.ini4j.Wini;

public class SwissPerfectService
{
    public static SPTournamentTO getSPTournament (File trnFile, File scoFile, File iniFile)
    {
        SPTournamentTO tournament = new SPTournamentTO();
        List<SPTournamentPlayerTO> players = new ArrayList<SPTournamentPlayerTO>();
        List<SPTournamentGameTO> games = new ArrayList<SPTournamentGameTO>();
        
        List<String[]> listPlayers = getDbfData (trnFile);
        List<String[]> listGames = getDbfData (scoFile);
        
        if (listPlayers != null)
        {
            for (String[] s: listPlayers)
            {
                SPTournamentPlayerTO player = new SPTournamentPlayerTO(s);
                players.add(player);
            }
            tournament.setListPlayers(players);
        }
        else
        {
            tournament.setMsg("No se pudo leer archivo .trn");
            return tournament;
        }
        
        if (listGames != null)
        {
            for (String[] s: listGames)
            {
                SPTournamentGameTO player = new SPTournamentGameTO(s);
                games.add(player);
            }
            tournament.setListGames(games);
        }
        else
        {
            tournament.setMsg("No se pudo leer archivo .sco");
            return tournament;
        }
        
        // Carga datos del Torneo (archivo .ini)
        
        if (!loadIniTournamentData(iniFile, tournament)) 
        {
            tournament.setMsg("No se pudo leer archivo .ini");
        }
        
        return tournament;
    }
    
    private static boolean loadIniTournamentData (File iniFile, SPTournamentTO torneo)
    {
        try
        {
            Wini ini = new Wini(iniFile);
            String seccion = "Tournament Info";
            
            String name = ini.get(seccion, "Name");
            String arbiter = ini.get(seccion, "Arbiter");
            String organiser = ini.get(seccion, "Organiser");
            
            int rounds = ini.get(seccion, "Rounds", int.class);
            int multigames = ini.get(seccion, "Multi Games", int.class);
            int system = ini.get(seccion, "System", int.class);
            int teamOrIndividual = ini.get(seccion, "Team or Individual", int.class);
            int useMinorScores = ini.get(seccion, "Use Minor Scores", int.class);
            
            torneo.setName(name);
            torneo.setArbiter(arbiter);
            torneo.setOrganiser(organiser);
            torneo.setRounds(Long.valueOf(rounds));
            torneo.setMultigames(Long.valueOf(multigames));
            torneo.setSystem(Long.valueOf(system));
            torneo.setTeamOrIndividual(Long.valueOf(teamOrIndividual));
            torneo.setUseMinorScores(Long.valueOf(useMinorScores));
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
    
    public static List<String[]> getDbfData(File dbfFile)
    {
        List<String[]> listaDatos = new ArrayList<String[]>();
        
        final Table table = new Table(dbfFile);
        try
        {
            table.open(IfNonExistent.ERROR);

            final List<Field> fields = table.getFields();
            final Iterator<Record> recordIterator = table.recordIterator();
            int fCount = 0;
            
            while(recordIterator.hasNext())
            {
                final Record record = recordIterator.next();
                String[] strRecord = new String[fields.size()];
                fCount = 0;
                
                for(final Field field: fields)
                {
                    try
                    {
                        byte[] rawValue = record.getRawValue(field);
                        strRecord[fCount] = rawValue == null ? "<NULL>" : new String(rawValue);
                        
                        //System.out.println(field.getName() + " : " + (rawValue == null ? "<NULL>" : new String(rawValue)));
                    }
                    catch(ValueTooLargeException vtle)
                    {
                        // Cannot happen :)
                    }
                    fCount++;
                }
                listaDatos.add(strRecord);
            }
        }
        catch(IOException ioe)
        {
            System.out.println("Trouble reading table or table not found");
            ioe.printStackTrace();
            return null;
        }
        catch(DbfLibException dbflibException)
        {
            System.out.println("Problem getting raw value");
            dbflibException.printStackTrace();
            return null;
        }
        finally
        {
            try
            {
                table.close();
            } catch (IOException ex)
            {
                System.out.println("Unable to close the table");
            }
        }
        
        return listaDatos;
    }
    
    public static void main(String[] args)
    {
        String archivo = "D:\\Mis Documentos\\Ajedrez\\swissperfect\\Archivo de Torneos para el GRAU\\0003 Torneos 2015\\065 Gran Torneo Abierto Fin de año\\Torneo Fin de Año.sco";
        File f = new File(archivo);
        
        final Table table = new Table(f);
        
        try
        {
            table.open(IfNonExistent.ERROR);

            final Format dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            System.out.println("TABLE PROPERTIES");
            System.out.println("Name          : " + table.getName());
            System.out.println("Last Modified : " + dateFormat.format(table.getLastModifiedDate()));
            System.out.println("--------------");
            System.out.println();
            System.out.println("FIELDS (COLUMNS)");

            final List<Field> fields = table.getFields();

            for(final Field field: fields)
            {
                System.out.println("  Name       : " + field.getName());
                System.out.println("  Type       : " + field.getType());
                System.out.println("  Length     : " + field.getLength());
                System.out.println("  Dec. Count : " + field.getDecimalCount());
                System.out.println();
            }
            
            System.out.println("--------------");
            System.out.println();
            System.out.println("RECORDS");

            final Iterator<Record> recordIterator = table.recordIterator();
            int count = 0;

            while(recordIterator.hasNext())
            {
                final Record record = recordIterator.next();
                System.out.println(count++);

                for(final Field field: fields)
                {
                    try
                    {
                        byte[] rawValue = record.getRawValue(field);
                        System.out.println(field.getName() + " : " + (rawValue == null ? "<NULL>" : new String(rawValue)));
                    }
                    catch(ValueTooLargeException vtle)
                    {
                        // Cannot happen :)
                    }
                }

                System.out.println();
            }

            System.out.println("--------------");
        }
        catch(IOException ioe)
        {
            System.out.println("Trouble reading table or table not found");
            ioe.printStackTrace();
        }
        catch(DbfLibException dbflibException)
        {
            System.out.println("Problem getting raw value");
            dbflibException.printStackTrace();
        }
        finally
        {
            try
            {
                table.close();
            } catch (IOException ex)
            {
                System.out.println("Unable to close the table");
            }
        }
    }
}
