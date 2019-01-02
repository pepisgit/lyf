package model.entities;

import java.io.Serializable;

public class ParametrosPK
    implements Serializable
{
    public int par_codigo;
    public String par_tipo;

    public ParametrosPK()
    {
    }

    public ParametrosPK(int par_codigo, String par_tipo)
    {
        this.par_codigo = par_codigo;
        this.par_tipo = par_tipo;
    }

    public boolean equals(Object other)
    {
        if (other instanceof ParametrosPK)
        {
            final ParametrosPK otherParametrosPK = (ParametrosPK) other;
            final boolean areEqual =
                (otherParametrosPK.par_codigo == par_codigo && otherParametrosPK.par_tipo.equals(par_tipo));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    public int getPar_codigo()
    {
        return par_codigo;
    }

    public void setPar_codigo(int par_codigo)
    {
        this.par_codigo = par_codigo;
    }

    public String getPar_tipo()
    {
        return par_tipo;
    }

    public void setPar_tipo(String par_tipo)
    {
        this.par_tipo = par_tipo;
    }
}
