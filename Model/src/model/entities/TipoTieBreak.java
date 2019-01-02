package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "\"tipos_tie_break\"")
public class TipoTieBreak
    implements Serializable
{
    @Id
    @Column(name = "ttb_id", nullable = false)
    private Long ttb_id;
    @Column(name = "ttb_nombre", nullable = false)
    private String ttb_nombre;

    public TipoTieBreak()
    {
    }

    public TipoTieBreak(Long ttb_id, String ttb_nombre)
    {
        this.ttb_id = ttb_id;
        this.ttb_nombre = ttb_nombre;
    }

    public String getTtb_nombre()
    {
        return ttb_nombre;
    }

    public void setTtb_nombre(String ttb_nombre)
    {
        this.ttb_nombre = ttb_nombre;
    }

    public void setTtb_id(Long ttb_id) {
        this.ttb_id = ttb_id;
    }

    public Long getTtb_id() {
        return ttb_id;
    }
}
