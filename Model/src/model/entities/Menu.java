package model.entities;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "Menu.findAll", query = "select o from Menu o") })
@Table(name = "\"menu\"")
public class Menu implements Serializable {
    @Column(name = "mnu_descripcion", nullable = false)
    private String mnu_descripcion;
    @Column(name = "mnu_display", nullable = false)
    private String mnu_display;
    @Id
    @Column(name = "mnu_id", nullable = false)
    private Long mnu_id;
    @Column(name = "mnu_padre", nullable = false)
    private Long mnu_padre;
    @Column(name = "mnu_url", nullable = false)
    private String mnu_url;

    public Menu() {
    }

    public Menu(String mnu_descripcion, String mnu_display, Long mnu_id, Long mnu_padre) {
        this.mnu_descripcion = mnu_descripcion;
        this.mnu_display = mnu_display;
        this.mnu_id = mnu_id;
        this.mnu_padre = mnu_padre;
    }

    public String getMnu_descripcion() {
        return mnu_descripcion;
    }

    public void setMnu_descripcion(String mnu_descripcion) {
        this.mnu_descripcion = mnu_descripcion;
    }

    public String getMnu_display() {
        return mnu_display;
    }

    public void setMnu_display(String mnu_display) {
        this.mnu_display = mnu_display;
    }

    public void setMnu_url(String mnu_url)
    {
        this.mnu_url = mnu_url;
    }

    public String getMnu_url()
    {
        return mnu_url;
    }

    public void setMnu_id(Long mnu_id) {
        this.mnu_id = mnu_id;
    }

    public Long getMnu_id() {
        return mnu_id;
    }

    public void setMnu_padre(Long mnu_padre) {
        this.mnu_padre = mnu_padre;
    }

    public Long getMnu_padre() {
        return mnu_padre;
    }
}
