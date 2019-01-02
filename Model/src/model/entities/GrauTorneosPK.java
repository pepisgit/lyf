package model.entities;

import java.io.Serializable;

public class GrauTorneosPK
    implements Serializable
{
    public int gtr_rkg_id;
    public int gtr_tor_id;

    public GrauTorneosPK()
    {
    }

    public GrauTorneosPK(int gtr_rkg_id, int gtr_tor_id)
    {
        this.gtr_rkg_id = gtr_rkg_id;
        this.gtr_tor_id = gtr_tor_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof GrauTorneosPK)
        {
            final GrauTorneosPK otherGrauTorneosPK = (GrauTorneosPK) other;
            final boolean areEqual =
                (otherGrauTorneosPK.gtr_rkg_id == gtr_rkg_id && otherGrauTorneosPK.gtr_tor_id == gtr_tor_id);
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    public int getGtr_rkg_id()
    {
        return gtr_rkg_id;
    }

    public void setGtr_rkg_id(int gtr_rkg_id)
    {
        this.gtr_rkg_id = gtr_rkg_id;
    }

    public int getGtr_tor_id()
    {
        return gtr_tor_id;
    }

    public void setGtr_tor_id(int gtr_tor_id)
    {
        this.gtr_tor_id = gtr_tor_id;
    }
}
