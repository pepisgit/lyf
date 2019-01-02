package model.entities;

import java.io.Serializable;

public class GrauHistorialPK
    implements Serializable
{
    public Long his_jug_id;
    public Long his_rkg_id;

    public GrauHistorialPK()
    {
    }

    public GrauHistorialPK(Long his_jug_id, Long his_rkg_id)
    {
        this.his_jug_id = his_jug_id;
        this.his_rkg_id = his_rkg_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof GrauHistorialPK)
        {
            final GrauHistorialPK otherGrauHistorialPK = (GrauHistorialPK) other;
            final boolean areEqual =
                (otherGrauHistorialPK.his_jug_id == his_jug_id && otherGrauHistorialPK.his_rkg_id == his_rkg_id);
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    public void setHis_jug_id(Long his_jug_id) {
        this.his_jug_id = his_jug_id;
    }

    public Long getHis_jug_id() {
        return his_jug_id;
    }

    public void setHis_rkg_id(Long his_rkg_id) {
        this.his_rkg_id = his_rkg_id;
    }

    public Long getHis_rkg_id() {
        return his_rkg_id;
    }
}
