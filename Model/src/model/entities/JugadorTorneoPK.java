package model.entities;

import java.io.Serializable;

public class JugadorTorneoPK
    implements Serializable
{
    public Long jpt_jug_id;
    public Long jpt_tor_id;

    public JugadorTorneoPK()
    {
    }

    public JugadorTorneoPK(Long jpt_jug_id, Long jpt_tor_id)
    {
        this.jpt_jug_id = jpt_jug_id;
        this.jpt_tor_id = jpt_tor_id;
    }

    public boolean equals(Object other)
    {
        if (other instanceof JugadorTorneoPK)
        {
            final JugadorTorneoPK otherJugadoreTorneoPK = (JugadorTorneoPK) other;
            final boolean areEqual =
                (otherJugadoreTorneoPK.jpt_jug_id.equals(jpt_jug_id) && otherJugadoreTorneoPK.jpt_tor_id.equals(jpt_tor_id));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }


    public void setJpt_jug_id(Long jpt_jug_id) {
        this.jpt_jug_id = jpt_jug_id;
    }

    public Long getJpt_jug_id() {
        return jpt_jug_id;
    }

    public void setJpt_tor_id(Long jpt_tor_id) {
        this.jpt_tor_id = jpt_tor_id;
    }

    public Long getJpt_tor_id() {
        return jpt_tor_id;
    }
}
