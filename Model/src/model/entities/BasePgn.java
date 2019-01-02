package model.entities;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SqlResultSetMapping(name="basePgnMap", 
  entities={ 
    @EntityResult(entityClass=BasePgn.class)})

@Entity
@Table(name = "\"base_pgn\"")
public class BasePgn implements Serializable 
{
    @Column(name = "bpgn_black")
    private String bpgn_black;
    @Temporal(TemporalType.DATE)
    @Column(name = "bpgn_date")
    private Date bpgn_date;
    @Column(name = "bpgn_eco")
    private String bpgn_eco;
    @Column(name = "bpgn_event")
    private String bpgn_event;
    @Id
    @Column(name = "bpgn_id", nullable = false)
    private Integer bpgn_id;
    @Column(name = "bpgn_par_id")
    private Integer bpgn_par_id;
    @Column(name = "bpgn_pgn_text", nullable = false)
    private String bpgn_pgn_text;
    @Column(name = "bpgn_result")
    private String bpgn_result;
    @Column(name = "bpgn_round")
    private Integer bpgn_round;
    @Column(name = "bpgn_white")
    private String bpgn_white;

    public BasePgn() {
    }

    public BasePgn(BasePgn b){
        this.bpgn_black = b.bpgn_black;
        this.bpgn_date = b.bpgn_date;
        this.bpgn_eco = b.bpgn_eco;
        this.bpgn_event = b.bpgn_event;
        this.bpgn_id = b.bpgn_id;
        this.bpgn_result = b.bpgn_result;
        this.bpgn_round = b.bpgn_round;
        this.bpgn_white = b.bpgn_white;
        this.bpgn_pgn_text = b.bpgn_pgn_text;
        this.bpgn_par_id = b.bpgn_par_id;
    }

    public BasePgn(String bpgn_black, Date bpgn_date, String bpgn_eco, String bpgn_event, Integer bpgn_id,
                   String bpgn_result, Integer bpgn_round, String bpgn_white, String bpgn_pgn_text,
                   Integer bpgn_par_id) {
        this.bpgn_black = bpgn_black;
        this.bpgn_date = bpgn_date;
        this.bpgn_eco = bpgn_eco;
        this.bpgn_event = bpgn_event;
        this.bpgn_id = bpgn_id;
        this.bpgn_result = bpgn_result;
        this.bpgn_round = bpgn_round;
        this.bpgn_white = bpgn_white;
        this.bpgn_pgn_text = bpgn_pgn_text;
        this.bpgn_par_id = bpgn_par_id;
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

    public void setBpgn_id(Integer bpgn_id) {
        this.bpgn_id = bpgn_id;
    }

    public Integer getBpgn_id() {
        return bpgn_id;
    }

    public void setBpgn_par_id(Integer bpgn_par_id) {
        this.bpgn_par_id = bpgn_par_id;
    }

    public Integer getBpgn_par_id() {
        return bpgn_par_id;
    }

    public void setBpgn_pgn_text(String bpgn_pgn_text) {
        this.bpgn_pgn_text = bpgn_pgn_text;
    }

    public String getBpgn_pgn_text() {
        return bpgn_pgn_text;
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
}
