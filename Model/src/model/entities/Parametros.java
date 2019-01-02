package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "\"parametros\"")
@IdClass(ParametrosPK.class)
public class Parametros
    implements Serializable
{
    @Id
    @Column(name = "par_codigo", nullable = false)
    private int par_codigo;
    @Id
    @Column(name = "par_tipo", nullable = false)
    private String par_tipo;
    @Column(name = "par_valor", nullable = false)
    private String par_valor;
    @Column(name = "par_valor_largo")
    private String par_valor_largo;

    public Parametros()
    {
    }

    public Parametros(int par_codigo, String par_tipo, String par_valor, String par_valor_largo)
    {
        this.par_codigo = par_codigo;
        this.par_tipo = par_tipo;
        this.par_valor = par_valor;
        this.par_valor_largo = par_valor_largo;
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

    public String getPar_valor()
    {
        return par_valor;
    }

    public void setPar_valor(String par_valor)
    {
        this.par_valor = par_valor;
    }

    public String getPar_valor_largo()
    {
        return par_valor_largo;
    }

    public void setPar_valor_largo(String par_valor_largo)
    {
        this.par_valor_largo = par_valor_largo;
    }
}
