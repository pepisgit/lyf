package model.entities;

import java.io.Serializable;

public class EquipoPK
    implements Serializable
{
    public Long equ_id;
    public Long equ_tor_id;

    public EquipoPK()
    {
    }

    public EquipoPK(Long equ_id, Long equ_tor_id)
    {
        this.equ_id = equ_id;
        this.equ_tor_id = equ_tor_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof EquipoPK)
        {
            final EquipoPK otherEquipoPK = (EquipoPK) other;
            final boolean areEqual = (otherEquipoPK.equ_id.equals(equ_id) && otherEquipoPK.equ_tor_id.equals(equ_tor_id));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }


    public void setEqu_id(Long equ_id) {
        this.equ_id = equ_id;
    }

    public Long getEqu_id() {
        return equ_id;
    }

    public void setEqu_tor_id(Long equ_tor_id) {
        this.equ_tor_id = equ_tor_id;
    }

    public Long getEqu_tor_id() {
        return equ_tor_id;
    }
}
