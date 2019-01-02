package view.services.pgn;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PGNGame 
{

    private Map<String, String> tags;
    
    private List<PGNMove> moves;
    
    private String pgn;
    
    PGNGame() {
            tags = new HashMap<String, String>();
            moves = new LinkedList<PGNMove>();
    }
    
    PGNGame(String pgn) {
            this();
            this.pgn = pgn;
    }
    
    @Override
    public String toString() {
            return pgn == null ? "" : pgn;
    }
    
    private Date bpgn_date;

    private String bpgn_eco;

    private String bpgn_event;

    private String bpgn_result;

    private Integer bpgn_round;

    private String bpgn_white;

    private String bpgn_black;

    private String bpgn_pgn_text;
    
    
    void addTag(String key, String value) {
            tags.put(key, value);
    }
    
    void removeTag(String key) {
            tags.remove(key);
    }
    
    void addMove(PGNMove move) {
            moves.add(move);
    }
    
    void removeMove(PGNMove move) {
            moves.remove(move);
    }
    
    void removeMove(int index) {
            moves.remove(index);
    }
    
    public String getTag(String key) {
            return tags.get(key);
    }
    
    public Iterator<String> getTagKeysIterator() {
            return tags.keySet().iterator();
    }
    
    public boolean containsTagKey(String key) {
            return tags.containsKey(key);
    }
    
    public int getTagsCount() {
            return tags.size();
    }
    
    public PGNMove getMove(int index) {
            return moves.get(index);
    }
    
    public Iterator<PGNMove> getMovesIterator() {
            return moves.iterator();
    }
    
    public int getMovesCount() {
            return moves.size();
    }
    
    public int getMovePairsCount() {
            return moves.size() / 2;
    }

    public void setPgn(String pgn) {
        this.pgn = pgn;
    }

    public String getPgn() {
        return pgn;
    }

    public void setBpgn_black(String bpgn_black) {
        this.bpgn_black = bpgn_black;
    }

    public String getBpgn_black() {
        return bpgn_black;
    }

    public void setBpgn_date(Date bpgn_date) {
        this.bpgn_date = bpgn_date;
    }

    public Date getBpgn_date() {
        return bpgn_date;
    }

    public void setBpgn_eco(String bpgn_eco) {
        this.bpgn_eco = bpgn_eco;
    }

    public String getBpgn_eco() {
        return bpgn_eco;
    }

    public void setBpgn_event(String bpgn_event) {
        this.bpgn_event = bpgn_event;
    }

    public String getBpgn_event() {
        return bpgn_event;
    }

    public void setBpgn_result(String bpgn_result) {
        this.bpgn_result = bpgn_result;
    }

    public String getBpgn_result() {
        return bpgn_result;
    }

    public void setBpgn_round(Integer bpgn_round) {
        this.bpgn_round = bpgn_round;
    }

    public Integer getBpgn_round() {
        return bpgn_round;
    }

    public void setBpgn_white(String bpgn_white) {
        this.bpgn_white = bpgn_white;
    }

    public String getBpgn_white() {
        return bpgn_white;
    }

    public void setBpgn_pgn_text(String bpgn_pgn_text) {
        this.bpgn_pgn_text = bpgn_pgn_text;
    }

    public String getBpgn_pgn_text() {
        return bpgn_pgn_text;
    }
}
