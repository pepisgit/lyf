package model.entities;

import java.io.Serializable;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@SqlResultSetMappings
({@SqlResultSetMapping(name="noticias", 
  entities={ 
    @EntityResult(entityClass=Noticias.class)}),
 
  @SqlResultSetMapping(name="noticiasUsuario", 
  entities={ 
    @EntityResult(entityClass=Noticias.class)},
  columns = {
    @ColumnResult(name = "NOMUSUARIO")}
  )
})


@Entity
@NamedQueries( { @NamedQuery(name = "Noticias.findAll", query = "select o from Noticias o") })
@Table(name = "\"noticias\"")
public class Noticias implements Serializable {
    @Column(name = "not_contenido", nullable = false)
    private String not_contenido;
    @Temporal(TemporalType.DATE)
    @Column(name = "not_fecha_alta", nullable = false)
    private Date not_fecha_alta;
    @Temporal(TemporalType.DATE)
    @Column(name = "not_fecha_pub", nullable = false)
    private Date not_fecha_pub;
    @Id
    @Column(name = "not_id", nullable = false)
    private Long not_id;
    @Column(name = "not_resumen", nullable = false)
    private String not_resumen;
    @Column(name = "not_soc_alta", nullable = false)
    private Long not_soc_alta;
    @Column(name = "not_foto", nullable = false)
    private String not_foto;
    @Column(name = "not_activa", nullable = false)
    private String not_activa;  // "S" = Activa ; "N" = Borrador
    @Column(name = "not_titulo", nullable = false)
    private String not_titulo;
    @Column(name = "not_formato", nullable = true)
    private Long not_formato;    // 1=Noticia ; 2=Aviso del Club
    
    @Column
    private String not_fb_comments;
    
    @Transient
    private String nomUsuario;
    
    
    public Noticias() {
    }

    public Noticias(String not_contenido, Date not_fecha_alta, Date not_fecha_pub, Long not_id, String not_resumen,
                    Long not_soc_alta, String not_foto, String not_activa, String not_titulo, Long not_formato, String fbComments)
    {
        this.not_contenido = not_contenido;
        this.not_fecha_alta = not_fecha_alta;
        this.not_fecha_pub = not_fecha_pub;
        this.not_id = not_id;
        this.not_resumen = not_resumen;
        this.not_soc_alta = not_soc_alta;
        this.not_foto = not_foto;
        this.not_activa = not_activa;
        this.not_titulo = not_titulo;
        this.not_formato = not_formato;
        this.not_fb_comments = fbComments;
    }
    
    public String getFechaPublicacion() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if (this.getNot_fecha_pub() != null){
            return "<span style=\"color:#5D9BE3;font-size:12px;font-weight:bold;\">" + sdf.format(this.getNot_fecha_pub()) + "</span>";
        }
        else{
            return "";
        }
    }
    
    public String getNot_contenido() {
        return not_contenido;
    }

    public void setNot_contenido(String not_contenido) {
        this.not_contenido = not_contenido;
    }

    public Date getNot_fecha_alta() {
        return not_fecha_alta;
    }

    public void setNot_fecha_alta(Date not_fecha_alta) {
        this.not_fecha_alta = not_fecha_alta;
    }

    public Date getNot_fecha_pub() {
        return not_fecha_pub;
    }

    public void setNot_fecha_pub(Date not_fecha_pub) {
        this.not_fecha_pub = not_fecha_pub;
    }

    public String getNot_resumen() {
        return not_resumen;
    }

    public void setNot_resumen(String not_resumen) {
        this.not_resumen = not_resumen;
    }

    public void setNot_foto(String not_foto)
    {
        this.not_foto = not_foto;
    }

    public String getNot_foto()
    {
        return not_foto;
    }

    public void setNot_activa(String not_activa)
    {
        this.not_activa = not_activa;
    }

    public String getNot_activa()
    {
        return not_activa;
    }

    public void setNot_titulo(String not_titulo)
    {
        this.not_titulo = not_titulo;
    }

    public String getNot_titulo()
    {
        return not_titulo;
    }

    public void setNot_id(Long not_id) {
        this.not_id = not_id;
    }

    public Long getNot_id() {
        return not_id;
    }

    public void setNot_soc_alta(Long not_soc_alta) {
        this.not_soc_alta = not_soc_alta;
    }

    public Long getNot_soc_alta() {
        return not_soc_alta;
    }


    public void setNot_formato(Long not_formato)
    {
        this.not_formato = not_formato;
    }

    public Long getNot_formato()
    {
        return not_formato;
    }

    public void setNomUsuario(String nomUsuario)
    {
        this.nomUsuario = nomUsuario;
    }

    public String getNomUsuario()
    {
        return nomUsuario;
    }

    public void setNot_fb_comments(String not_fb_comments) {
        this.not_fb_comments = not_fb_comments;
    }

    public String getNot_fb_comments() {
        return not_fb_comments;
    }
}
