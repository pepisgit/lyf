package view.services.swissperf;

public enum FieldNamesPlayer 
{
    ROUND   (0),
    WHITE   (1),
    BLACK   (2),
    W_SCORE (3),
    B_SCORE (4),
    W_TYPE  (5),
    B_TYPE  (6),
    W_SUBSCO(7),
    B_SUBSCO(8);
    
    private int value;
    
    private FieldNamesPlayer (int value){
        this.value = value;
    }
}
