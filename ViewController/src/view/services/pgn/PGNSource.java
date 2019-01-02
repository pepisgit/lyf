package view.services.pgn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class PGNSource
{

    private String source;
    
    public PGNSource(String pgn) {
            if (pgn == null) {
                    throw new NullPointerException("PGN data is null");
            }
            
            this.source = pgn;
    }
    
    public PGNSource(File file) throws IOException {
            this(new FileInputStream(file));
    }
    
    public PGNSource(URL url) throws IOException {
            this(url.openStream());
    }
    
    public PGNSource(InputStream inputStream) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder buffer = new StringBuilder();
            
            while ((line = br.readLine()) != null) {
                    buffer.append(line + "\r\n");
            }
            
            br.close();
            this.source = buffer.toString();
    }
    
    @Override
    public String toString() {
            return source;
    }
    
    public List<PGNGame> listGames() throws PGNParseException, IOException, NullPointerException, MalformedMoveException {
            return PGNParser.parse(source);
    }
    
    public List<PGNGame> listGames(boolean force) throws PGNParseException, IOException, NullPointerException, MalformedMoveException {
            return PGNParser.parse(source, force);
    }
    

}
