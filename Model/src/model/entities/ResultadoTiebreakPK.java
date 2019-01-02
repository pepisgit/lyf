package model.entities;

import java.io.Serializable;

public class ResultadoTiebreakPK
    implements Serializable
{
    public Long rtb_jug_id;
    public Long rtb_tor_id;
    public Long rtb_ttb_id;

    public ResultadoTiebreakPK()
    {
    }

    public ResultadoTiebreakPK(Long rtb_jug_id, Long rtb_tor_id, Long rtb_ttb_id)
    {
        this.rtb_jug_id = rtb_jug_id;
        this.rtb_tor_id = rtb_tor_id;
        this.rtb_ttb_id = rtb_ttb_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof ResultadoTiebreakPK)
        {
            final ResultadoTiebreakPK otherResultadoTiebreakPK = (ResultadoTiebreakPK) other;
            final boolean areEqual =
                (otherResultadoTiebreakPK.rtb_jug_id.equals(rtb_jug_id)
                 && otherResultadoTiebreakPK.rtb_tor_id.equals(rtb_tor_id)
                 && otherResultadoTiebreakPK.rtb_ttb_id.equals(rtb_ttb_id));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }


    public void setRtb_jug_id(Long rtb_jug_id) {
        this.rtb_jug_id = rtb_jug_id;
    }

    public Long getRtb_jug_id() {
        return rtb_jug_id;
    }

    public void setRtb_tor_id(Long rtb_tor_id) {
        this.rtb_tor_id = rtb_tor_id;
    }

    public Long getRtb_tor_id() {
        return rtb_tor_id;
    }

    public void setRtb_ttb_id(Long rtb_ttb_id) {
        this.rtb_ttb_id = rtb_ttb_id;
    }

    public Long getRtb_ttb_id() {
        return rtb_ttb_id;
    }
}
