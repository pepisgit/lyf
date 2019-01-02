package view.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileService 
{
    public static void FileCopy (File inFile, File outFile)
    {
       try 
       {
            if (inFile.exists())
            {
                FileInputStream in = new FileInputStream(inFile);
                FileOutputStream out = new FileOutputStream(outFile);
                int c;
                while( (c = in.read() ) != -1){
                    out.write(c);
                }
                in.close();
                out.close();
            }
           else
                System.out.println("No se encuentra el archivo de origen.");
       } 
       catch(IOException e) 
       {
            System.err.println("Hubo un error de entrada/salida!!!");
            e.printStackTrace();
       }
    }    
    
    public static String getFileToString(String ruta)
    {
        String ret = null;
        File f = new File (ruta);
        
        try
        {            
            if (f.exists())
            {
                StringBuilder bs = new StringBuilder("");
                BufferedReader br = new BufferedReader(new FileReader(f));
                String linea = null;
                while ((linea = br.readLine()) != null) {
                    bs.append(linea);
                    bs.append("\n");
                }
                ret = bs.toString();                
            }
            
        }   
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return ret;
    }
    
    // Devuelve la extensión de un archivo
    public static String getExtension (String nombreArchivo)
    {
        String ret = "";
        int i = nombreArchivo.lastIndexOf('.');
        if (i > 0) {
            ret = nombreArchivo.substring(i + 1);
        }
        return ret;
    }
    
    // Devuelve un array de Archivos que coinciden con el filtro
    public static File[] finder(String dirName, String startsWith, String endsWith)
    {
        File dir = new File(dirName);

        FilenameFilter select = new FileListFilter(startsWith, endsWith);
        
        return dir.listFiles(select);
    }    
    
    static class FileListFilter implements FilenameFilter 
    {
          private String name; 
          private String extension; 
    
          public FileListFilter(String name, String extension) {
            this.name = name;
            this.extension = extension;
          }
    
          public boolean accept(File directory, String filename) {
            boolean fileOK = true;
    
            if (name != null) {
              fileOK &= filename.startsWith(name);
            }
    
            if (extension != null) {
              fileOK &= filename.endsWith('.' + extension);
            }
            return fileOK;
          }
    }
    
    
    
    
    
    
    
    
    
    
    
}
