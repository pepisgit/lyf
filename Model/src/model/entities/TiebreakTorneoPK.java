package model.entities;

import java.io.Serializable;

public class TiebreakTorneoPK
    implements Serializable
{
    public Long tpt_tor_id;
    public Long tpt_ttb_id;

    public TiebreakTorneoPK()
    {
    }

    public TiebreakTorneoPK(Long tpt_tor_id, Long tpt_ttb_id)
    {
        this.tpt_tor_id = tpt_tor_id;
        this.tpt_ttb_id = tpt_ttb_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof TiebreakTorneoPK)
        {
            final TiebreakTorneoPK otherTiebreakTorneoPK = (TiebreakTorneoPK) other;
            final boolean areEqual =
                (otherTiebreakTorneoPK.tpt_tor_id.equals(tpt_tor_id) && otherTiebreakTorneoPK.tpt_ttb_id.equals(tpt_ttb_id));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    public void setTpt_tor_id(Long tpt_tor_id) {
        this.tpt_tor_id = tpt_tor_id;
    }

    public Long getTpt_tor_id() {
        return tpt_tor_id;
    }

    public void setTpt_ttb_id(Long tpt_ttb_id) {
        this.tpt_ttb_id = tpt_ttb_id;
    }

    public Long getTpt_ttb_id() {
        return tpt_ttb_id;
    }
}
