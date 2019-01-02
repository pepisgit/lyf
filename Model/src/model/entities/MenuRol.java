package model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery(name = "MenuRol.findAll", query = "select o from MenuRol o") })
@Table(name = "\"menu_rol\"")
public class MenuRol implements Serializable {
    @Id
    @Column(name = "mnurol_id", nullable = false)
    private Long mnurol_id;
    @Column(name = "mnurol_mnu_id", nullable = false)
    private Long mnurol_mnu_id;
    @Column(name = "mnurol_rol_id")
    private Long mnurol_rol_id;
    @Column(name = "mnurol_usr_id")
    private Long mnurol_usr_id;

    public MenuRol() {
    }

    public MenuRol(Long mnurol_id, Long mnurol_mnu_id, Long mnurol_rol_id, Long mnurol_usr_id) {
        this.mnurol_id = mnurol_id;
        this.mnurol_mnu_id = mnurol_mnu_id;
        this.mnurol_rol_id = mnurol_rol_id;
        this.mnurol_usr_id = mnurol_usr_id;
    }


    public void setMnurol_id(Long mnurol_id) {
        this.mnurol_id = mnurol_id;
    }

    public Long getMnurol_id() {
        return mnurol_id;
    }

    public void setMnurol_mnu_id(Long mnurol_mnu_id) {
        this.mnurol_mnu_id = mnurol_mnu_id;
    }

    public Long getMnurol_mnu_id() {
        return mnurol_mnu_id;
    }

    public void setMnurol_rol_id(Long mnurol_rol_id) {
        this.mnurol_rol_id = mnurol_rol_id;
    }

    public Long getMnurol_rol_id() {
        return mnurol_rol_id;
    }

    public void setMnurol_usr_id(Long mnurol_usr_id) {
        this.mnurol_usr_id = mnurol_usr_id;
    }

    public Long getMnurol_usr_id() {
        return mnurol_usr_id;
    }
}
