package model.entities;

import java.io.Serializable;

public class JugadorEquipoPK
    implements Serializable
{
    public Long jxe_equ_id;
    public Long jxe_jug_id;
    public Long jxe_tor_id;

    public JugadorEquipoPK()
    {
    }

    public JugadorEquipoPK(Long jxe_equ_id, Long jxe_jug_id, Long jxe_tor_id)
    {
        this.jxe_equ_id = jxe_equ_id;
        this.jxe_jug_id = jxe_jug_id;
        this.jxe_tor_id = jxe_tor_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof JugadorEquipoPK)
        {
            final JugadorEquipoPK otherJugadoresEquiposPK = (JugadorEquipoPK) other;
            final boolean areEqual =
                (otherJugadoresEquiposPK.jxe_equ_id.equals(jxe_equ_id) && otherJugadoresEquiposPK.jxe_jug_id.equals(jxe_jug_id) && otherJugadoresEquiposPK.jxe_tor_id.equals(jxe_tor_id));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }


    public void setJxe_equ_id(Long jxe_equ_id) {
        this.jxe_equ_id = jxe_equ_id;
    }

    public Long getJxe_equ_id() {
        return jxe_equ_id;
    }

    public void setJxe_jug_id(Long jxe_jug_id) {
        this.jxe_jug_id = jxe_jug_id;
    }

    public Long getJxe_jug_id() {
        return jxe_jug_id;
    }

    public void setJxe_tor_id(Long jxe_tor_id) {
        this.jxe_tor_id = jxe_tor_id;
    }

    public Long getJxe_tor_id() {
        return jxe_tor_id;
    }
}
