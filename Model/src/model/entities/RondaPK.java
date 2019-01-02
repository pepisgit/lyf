package model.entities;

import java.io.Serializable;

public class RondaPK
    implements Serializable
{
    public Long ron_id;
    public Long ron_tor_id;

    public RondaPK()
    {
    }

    public RondaPK(Long ron_id, Long ron_tor_id)
    {
        this.ron_id = ron_id;
        this.ron_tor_id = ron_tor_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof RondaPK)
        {
            final RondaPK otherRondasPK = (RondaPK) other;
            final boolean areEqual = (otherRondasPK.ron_id.equals(ron_id)
                                      && otherRondasPK.ron_tor_id.equals(ron_tor_id));
            return areEqual;
        }
        return false;
    }

    public int  hashCode()
    {
        return super.hashCode();
    }


    public void setRon_id(Long ron_id) {
        this.ron_id = ron_id;
    }

    public Long getRon_id() {
        return ron_id;
    }

    public void setRon_tor_id(Long ron_tor_id) {
        this.ron_tor_id = ron_tor_id;
    }

    public Long getRon_tor_id() {
        return ron_tor_id;
    }
}
