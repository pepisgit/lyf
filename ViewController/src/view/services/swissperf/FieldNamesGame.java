package view.services.swissperf;

public enum FieldNamesGame
{
    ORDER       (0),
    ID          (1),
    START_NO    (2),
    SURNAME     (3),
    FIRSTNAME   (4),
    FEDER       (5),
    CLUB        (6),
    INTL_RTG    (7),
    LOC_RTG     (8),
    LOC_RTG2    (9),
    INTL_ID     (10),
    LOC_ID      (11),
    TITLE       (12),
    SEX         (13),
    BIRTH_DATE  (14),
    LATE_ENTRY  (15),
    WITHDRAWAL  (16),
    ABSENT      (17),
    BONUS       (18),
    TECH_SCORE  (19),
    FORB_PAIRS  (20),
    TEAM        (21),
    BOARD       (22),
    MEMO        (23);
    
    private int value;
    
    private FieldNamesGame (int value){
        this.value = value;
    }    
}
