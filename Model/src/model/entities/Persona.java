package model.entities;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "\"personas\"")
public class Persona implements Serializable {
    @Column(name = "per_apellido")
    private String per_apellido;
    @Column(name = "per_domicilio_1")
    private String per_domicilio_1;
    @Column(name = "per_domicilio_2")
    private String per_domicilio_2;
    @Column(name = "per_email")
    private String per_email;
    @Temporal(TemporalType.DATE)
    @Column(name = "per_fecha_nac")
    private Date per_fecha_nac;
    @Id
    @Column(name = "per_id", nullable = false)
    private int per_id;
    @Column(name = "per_nombres")
    private String per_nombres;
    @Column(name = "per_tel_1")
    private String per_tel_1;
    @Column(name = "per_tel_2")
    private String per_tel_2;

    public Persona() {
    }

    public Persona(String per_apellido, String per_domicilio_1, String per_domicilio_2, String per_email,
                    Date per_fecha_nac, int per_id, String per_nombres, String per_tel_1, String per_tel_2) {
        this.per_apellido = per_apellido;
        this.per_domicilio_1 = per_domicilio_1;
        this.per_domicilio_2 = per_domicilio_2;
        this.per_email = per_email;
        this.per_fecha_nac = per_fecha_nac;
        this.per_id = per_id;
        this.per_nombres = per_nombres;
        this.per_tel_1 = per_tel_1;
        this.per_tel_2 = per_tel_2;
    }

    public String getApellidoNombre()
    {
        return per_apellido + (per_nombres != null? ", " + per_nombres: "");
    }

    public String getPer_apellido() {
        return per_apellido;
    }

    public void setPer_apellido(String per_apellido) {
        this.per_apellido = per_apellido;
    }

    public String getPer_domicilio_1() {
        return per_domicilio_1;
    }

    public void setPer_domicilio_1(String per_domicilio_1) {
        this.per_domicilio_1 = per_domicilio_1;
    }

    public String getPer_domicilio_2() {
        return per_domicilio_2;
    }

    public void setPer_domicilio_2(String per_domicilio_2) {
        this.per_domicilio_2 = per_domicilio_2;
    }

    public String getPer_email() {
        return per_email;
    }

    public void setPer_email(String per_email) {
        this.per_email = per_email;
    }

    public Date getPer_fecha_nac() {
        return per_fecha_nac;
    }

    public void setPer_fecha_nac(Date per_fecha_nac) {
        this.per_fecha_nac = per_fecha_nac;
    }

    public int getPer_id() {
        return per_id;
    }

    public void setPer_id(int per_id) {
        this.per_id = per_id;
    }

    public String getPer_nombres() {
        return per_nombres;
    }

    public void setPer_nombres(String per_nombres) {
        this.per_nombres = per_nombres;
    }

    public String getPer_tel_1() {
        return per_tel_1;
    }

    public void setPer_tel_1(String per_tel_1) {
        this.per_tel_1 = per_tel_1;
    }

    public String getPer_tel_2() {
        return per_tel_2;
    }

    public void setPer_tel_2(String per_tel_2) {
        this.per_tel_2 = per_tel_2;
    }
}
