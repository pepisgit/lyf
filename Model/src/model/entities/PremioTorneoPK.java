package model.entities;

import java.io.Serializable;

public class PremioTorneoPK implements Serializable 
{
    private Integer pre_id;
    private Integer pre_tor_id;

    public PremioTorneoPK() {
    }

    public PremioTorneoPK(Integer pre_id, Integer pre_tor_id) {
        this.pre_id = pre_id;
        this.pre_tor_id = pre_tor_id;
    }

    public void setPre_id(Integer pre_id) {
        this.pre_id = pre_id;
    }

    public Integer getPre_id() {
        return pre_id;
    }

    public void setPre_tor_id(Integer pre_tor_id) {
        this.pre_tor_id = pre_tor_id;
    }

    public Integer getPre_tor_id() {
        return pre_tor_id;
    }
}
