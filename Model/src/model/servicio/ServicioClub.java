package model.servicio;

import java.io.Serializable;

import java.math.BigDecimal;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import javax.persistence.Query;

import model.dto.JugadorDTO;

import model.dto.JugadorTorneoDTO;

import model.dto.PartidaDTO;
import model.dto.RondaDTO;

import model.entities.BasePgn;
import model.entities.GrauHistorial;
import model.entities.GrauRanking;
import model.entities.Jugador;
import model.entities.JugadorTorneo;
import model.entities.JugadorTorneoPK;
import model.entities.Menu;
import model.entities.Noticias;
import model.entities.Parametros;
import model.entities.Partida;
import model.entities.Persona;
import model.entities.PremioTorneo;
import model.entities.Roles;
import model.entities.Ronda;
import model.entities.RondaPK;
import model.entities.Torneo;
import model.entities.Usuarios;

import org.eclipse.persistence.jpa.JpaEntityManager;

public class ServicioClub implements Serializable
{
    private static ServicioClub servicio;
    
    public ServicioClub(){
    }
    
    public static ServicioClub getInstance()
    {
        if (servicio==null){
            servicio = new ServicioClub();
        }
        return servicio;
    }
    
    public void persist(Object entity) 
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();  
            em.persist(entity);
            t.commit();
          } finally {
            if (t.isActive()) t.rollback();
          }
        } finally {
          em.close();
        }
    }

    public void merge(Object entity)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();  
            em.merge(entity);
            t.commit();
          } finally {
            if (t.isActive()) t.rollback();
          }
        } finally {
          em.close();
        }
    }

    public void remove(Object entity)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        try {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();  
            em.remove(entity);
            t.commit();
          } finally {
            if (t.isActive()) t.rollback();
          }
        } finally {
          em.close();
        }
    }
    
    public List<Noticias> getUltimasNoticias()
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        String strQ = "SELECT * FROM noticias WHERE not_activa = 'S' ORDER BY not_id DESC";
        List<Noticias> res = null;
        
        try {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();  
            Query q = em.createNativeQuery (strQ, "noticias");
            res = q.getResultList();
            t.commit();
          } finally {
            if (t.isActive()) t.rollback();
          }
        } finally {
          em.close();
        }        
        return res;
    }
    
    public List<Noticias> getNoticiasPorUsuario(Long usuarioId)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String strQ = "SELECT n.*, usr.usr_nombre as NOMUSUARIO\n" + 
        "FROM noticias n, usuarios usr\n" + 
        "WHERE usr.usr_id = n.not_soc_alta\n" + 
        "  AND (not_soc_alta = ?usuario \n" + 
        "	   OR exists (SELECT 1 \n" + 
        "             FROM roles_usuarios r \n" + 
        "            WHERE r.rolusr_rol = 1 \n" + 
        "              AND r.rolusr_usr = ?usuario)\n" + 
        "      )\n" + 
        "ORDER BY not_fecha_alta DESC";
        
        List<Noticias> res = new ArrayList<Noticias>();
        
        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();  

            Query q = em.createNativeQuery (strQ, "noticiasUsuario");
            q.setParameter("usuario", usuarioId);
            
            List result =  q.getResultList();
            
            if (result != null && !result.isEmpty())
            {
                for (int i=0; i <result.size(); i++)
                {
                    Noticias not = new Noticias();
                    Object[] o = (Object[])result.get(i);
                    
                    not = (Noticias) o[0];
                    String nomUsuario = (String) o[1];
                    
                    not.setNomUsuario(nomUsuario);
                    res.add(not);
                }
            }
            t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally 
        {
           em.close();
        }
        
        return res;
    }
    
    public Noticias getNoticiaById(Long idNoticia)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String strQ = "SELECT * FROM noticias WHERE not_id = ?idnoticia";
        Noticias res = null;
        
        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();  
        
            Query q = em.createNativeQuery(strQ, "noticias");
            //q.setHint("toplink.pessimistic-lock", "Lock");
            q.setParameter("idnoticia", idNoticia);
            
            res = ((List<Noticias>)q.getResultList()).get(0);
            em.refresh(res);
        
            t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally
        {
            em.close();
        }        
        return res;
    }
    
    public Long getSequence (String nombreSecuencia)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String qSeq = "select " + nombreSecuencia + " from dual";
        Long result = null;
        
        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();     
            Query q = em.createNativeQuery(qSeq);
            result = Long.valueOf ((q.getSingleResult()).toString());
            t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally
        {
            em.close();
        }        
        return result;
    }
    
    public String getParametro (Long idParametro, int tipoValor) throws Exception
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String ret = null;
        String strQ = "select " + ((tipoValor==0)? "par_valor": "par_valor_largo") + 
                      "  from parametros where par_codigo = ?codigo";

        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();     
        
            Query q = em.createNativeQuery(strQ).setParameter("codigo", idParametro);
            ret = (String)q.getSingleResult();
            
            t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally
        {
            em.close();
        }
        return ret;
    }
    
    public List<Parametros> qParametrosPorTipo (String tipo) throws Exception
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        String strQ = "select * from parametros where par_tipo = ?pTipo";
        List<Parametros> ret = null;

        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();            
            
            Query q = em.createNativeQuery(strQ, Parametros.class);
            q.setParameter("pTipo", tipo);
            ret = q.getResultList();
            t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally
        {
            em.close();
        }
        
        return ret;
    }
    
    public void mergeNoticia (Long idNoticia,
                                String txtTitulo,
                                String txtResumen,
                                String txtContenido,
                                String fbComments,
                                String rutaImagen,
                                Long usuarioAlta,
                                Date fechaPublicacion,
                                boolean activa,
                                Long formato)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
              t.begin();
              Noticias n = (Noticias) em.find(Noticias.class, idNoticia);
              
              if (n != null)
              {
                  n.setNot_titulo(txtTitulo);
                  n.setNot_resumen(txtResumen);
                  n.setNot_contenido(txtContenido);
                  n.setNot_fb_comments(fbComments);
                  if (rutaImagen != null)
                      n.setNot_foto(rutaImagen);
                  if (fechaPublicacion != null)
                      n.setNot_fecha_pub(fechaPublicacion);
                  n.setNot_soc_alta(usuarioAlta);
                  n.setNot_activa(activa?"S":"N");
                  n.setNot_formato(formato);
                  
                  em.merge(n);
              }
              t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally
        {
            em.close();
        }
    }
    
    public void activacionNoticia (Long idNoticia, boolean activa) throws Exception
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
              t.begin();

              Noticias n = (Noticias) em.find(Noticias.class, idNoticia);
                
              if (n != null)
              {
                  n.setNot_activa(activa?"S":"N");
                  em.merge(n);
              }
              t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally
        {
            em.close();
        }              
    }
    
    public void persistNoticia (Long idNoticia,
                                String txtTitulo,
                                String txtResumen,
                                String txtContenido,
                                String fbComments,
                                String rutaImagen,
                                Long usuarioAlta,
                                Date fechaPublicacion,
                                boolean activa,
                                Long formato)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();

                Noticias n = new Noticias(txtContenido, 
                                          new Date(), 
                                          fechaPublicacion != null? fechaPublicacion: (new Date()), 
                                          idNoticia,
                                          txtResumen, 
                                          usuarioAlta, 
                                          rutaImagen, 
                                          activa?"S":"N", 
                                          txtTitulo, 
                                          formato,
                                          fbComments);
                
                em.persist(n);
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
    }
    
    public void removeNoticia (Long idNoticia) throws Exception
    {
        if (idNoticia != null)
        {
            EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
            EntityManager em = emf.createEntityManager();

            try 
            {
                EntityTransaction t = em.getTransaction();
                try
                {
                    t.begin();
                    Noticias n = em.find(Noticias.class, idNoticia);
                    if (n!=null)
                    {
                        em.remove(n);
                    }
                    t.commit();
                }
                finally
                {
                    if (t.isActive()) t.rollback();
                }
            }
            finally
            {
                em.close();
            }
        }
    }
    
    public Long persistPersona(Persona p, boolean nuevo) throws Exception
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Long ret = null;
        
        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
        
                if (nuevo)
                {
                    ret = this.getSequence("get_seq_personas()");
                    p.setPer_id(ret.intValue());
                }
                em.merge(p);
                
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return ret;
    }
    
    public Long persistJugador(Jugador j, boolean nuevo) throws Exception
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        Long ret = null;

        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
        
                if (nuevo)
                {
                    ret = this.getSequence("get_seq_jugadores()");
                    j.setJug_id(ret);
                }
                
                em.merge(j);
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return ret;
    }
    
    public Usuarios qUsuarioSistema (String usrNombre, String usrPass)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        Usuarios ret = null;
        String q =
            "SELECT us.*, s.*, r.*, p.*" +
            "  FROM usuarios us " +
            "  LEFT JOIN socios s ON us.usr_soc_id = s.soc_id" +
            "  LEFT JOIN personas p ON s.soc_per_id = p.per_id" +
            "  LEFT JOIN roles_usuarios r ON r.rolusr_usr = us.usr_id" +
            " WHERE us.usr_nombre = ?usuario" +
            "   AND us.usr_pass = ?pass";
        
        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
        
                Query qry = em.createNativeQuery(q, "UsuariosMapping");
                qry.setParameter("usuario", usrNombre);
                qry.setParameter("pass", usrPass);
                List result =  qry.getResultList();
                
                if (result != null && !result.isEmpty())
                {
                    ret = new Usuarios();
                    Object[] o = (Object[])result.get(0);
                    
                    ret.setUsr_id(Long.valueOf((Integer)o[0]));
                    
                    Persona p = new Persona();
                    p.setPer_nombres((String)o[15]);
                    
                    Roles r = new Roles();
                    r.setRol_id( Long.valueOf((Integer)o[12]) );
                    
                    ret.setPersona(p);
                    ret.setRol(r);
                }
                
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return ret;
    }
    
    public List<Menu> qMenuByRol (Long rolId)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String q = "SELECT m.* FROM menu m\n" + 
                   "INNER JOIN menu_rol mr ON m.mnu_id = mr.mnurol_mnu_id\n" + 
                   "WHERE mr.mnurol_rol_id = ?rol";
        List<Menu> ret = new ArrayList<Menu>();
        
        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                Query qry = em.createNativeQuery(q);
                qry.setParameter("rol", rolId);
                
                List result = qry.getResultList();
                
                for (int i=0; i<result.size();i++)
                {
                    Object[] o = (Object[])result.get(i);
                    Menu m = new Menu();
                    m.setMnu_id(Long.valueOf((Integer)o[0]));
                    m.setMnu_display((String)o[2]);
                    m.setMnu_descripcion((String)o[3]);
                    m.setMnu_url((String)o[4]);
                    
                    ret.add(m);
                }
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }

        return ret;
    }

    public List<BasePgn> getBasePgn()
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String strQ = "SELECT bpgn_id, bpgn_event, bpgn_date, \n" +
                      "       bpgn_round, bpgn_white, bpgn_black, \n" +
                      "       bpgn_result, bpgn_eco, bpgn_par_id \n" +
                      "FROM base_pgn ORDER BY bpgn_date DESC";
        List<BasePgn> res = new ArrayList<BasePgn>();        

        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();

                Query q = em.createNativeQuery (strQ);
                List result = q.getResultList();
        
        
                for (int i=0; i<result.size();i++)
                {
                    Object[] o = (Object[])result.get(i);
                    BasePgn pgn = new BasePgn();
        
                    pgn.setBpgn_id((Integer)o[0]);
                    pgn.setBpgn_event((String)o[1]);
                    pgn.setBpgn_date(o[2] != null? (Date)o[2]: null);
                    pgn.setBpgn_round(o[3] != null? (Integer)o[3]: null);
                    pgn.setBpgn_white((String)o[4]);
                    pgn.setBpgn_black((String)o[5]);
                    pgn.setBpgn_result((String)o[6]);
                    pgn.setBpgn_eco((String)o[7]);
                    pgn.setBpgn_par_id(o[8] != null? (Integer)o[8]: null);
                    
                    res.add(pgn);
                }
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return res;
    }
    
    public BasePgn qPartidaPgn (Integer idPartidaPgn)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String strQ = "SELECT * FROM base_pgn WHERE bpgn_id = ?idpgn";
        BasePgn ret = null;
        
        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                Query q = em.createNativeQuery(strQ, BasePgn.class);
                q.setParameter("idpgn", idPartidaPgn);
                List<BasePgn> lista = (List<BasePgn>)q.getResultList();
                ret = lista.get(0);
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return ret;
    }
    
    public List<String> getPartidasEventos()
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String strQ = "SELECT DISTINCT b.evento \n" + 
        "FROM (SELECT bpgn_event as evento FROM  base_pgn ORDER BY bpgn_date DESC) as b\n";
        List<String> ret = new ArrayList<String>();
        
        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                Query q = em.createNativeQuery (strQ);
                List lista = q.getResultList();
                
                if (lista != null && lista.size() > 0)
                {
                    for (int i=0; i < lista.size(); i++)
                    {
                        String valor = (String)lista.get(i);
                        ret.add(valor);
                    }
                }
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return ret;
    }
    
    public List<String> getPartidasBlancas()
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String strQ = "SELECT DISTINCT b.bpgn_white \n" + 
                      "FROM base_pgn b \n" + 
                      "ORDER BY 1";
        List<String> ret = new ArrayList<String>();
        
        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                Query q = em.createNativeQuery (strQ);
                List lista = q.getResultList();
                        
                if (lista != null && lista.size() > 0)
                {
                    for (int i=0; i < lista.size(); i++)
                    {
                        String valor = (String)lista.get(i);
                        ret.add(valor);
                    }
                }
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return ret;
    }
    
    public List<String> getPartidasNegras()
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String strQ = "SELECT DISTINCT b.bpgn_black \n" + 
                      "FROM base_pgn b \n" + 
                      "ORDER BY 1";
        List<String> ret = new ArrayList<String>();
        
        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                Query q = em.createNativeQuery (strQ);
                List lista = q.getResultList();
                
                if (lista != null && lista.size() > 0)
                {
                    for (int i=0; i < lista.size(); i++)
                    {
                        String valor = (String)lista.get(i);
                        ret.add(valor);
                    }
                }
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return ret;
    }    
    
    public Torneo qTorneoCompleto (Long torId)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        String strQ =   "SELECT t.*, \n" + 
                        "       p.par_valor as sistema,\n" + 
                        "       p2.par_valor as tipotorneo\n" + 
                        "  FROM torneos t, \n" + 
                        "       parametros p,\n" + 
                        "       parametros p2\n" + 
                        "WHERE p.par_tipo = 'SISTEMAS'\n" + 
                        "  AND p2.par_tipo = 'TIPOS_TORNEO'\n" + 
                        "  AND t.tor_sistema = p.par_codigo\n" + 
                        "  AND t.tor_tipo = p2.par_codigo\n" + 
                        "  AND tor_id = ?idtorneo";
        Torneo torneo = null;
        
        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();  
        
            Query q = em.createNativeQuery(strQ, "torneosMap");
            q.setParameter("idtorneo", torId);
            
            List v = q.getResultList();
            Object[] o = (Object []) v.get(0);
            
            torneo = (Torneo)o[0];
            torneo.setSistema((String) o[1]);
            torneo.setTipoTorneo((String) o[2]);
            
            // Cargo Entidades JugadorTorneo
            String qryJugadoresTorneo = "SELECT * FROM jugadores_torneos \n" +
                                        "WHERE jpt_tor_id = ?idTorneo";
            Query qJugTorneo = em.createNativeQuery(qryJugadoresTorneo, JugadorTorneo.class);
            qJugTorneo.setParameter("idTorneo", torId);
            List<JugadorTorneo> listaJT = (List<JugadorTorneo>)qJugTorneo.getResultList();
            torneo.setJugadoresTorneo(listaJT);            
            
            // Cargo Rondas
            String qryRondas = "SELECT * FROM rondas WHERE ron_tor_id = ?torid";
            Query qRondas = em.createNativeQuery(qryRondas, "rondaMap");
            qRondas.setParameter("torid", torId);
            
            List vRondas = qRondas.getResultList();
            List<Ronda> listaRondas = new ArrayList<Ronda>();
            
            if (vRondas != null && vRondas.size() > 0)
            {
                for (int i = 0; i < vRondas.size(); i++)
                {
                    Ronda ronda = (Ronda) vRondas.get(i);
                    listaRondas.add(ronda);
                }
                
                // Cargo Partidas
                
                String qryPartidas = "SELECT par.* \n" + 
                                     "  FROM partidas par\n" + 
                                     " WHERE par_tor_id = ?torid";
    
                Query qPartidas = em.createNativeQuery(qryPartidas, "partidaMap");
                qPartidas.setParameter("torid", torId);
                List vPartidas = qPartidas.getResultList();
                List<Partida> listaPartidas = new ArrayList<Partida>();
                
                if (vPartidas != null && vPartidas.size() > 0)
                {
                    for (int i = 0; i < vPartidas.size(); i++)
                    {
                      //Object[] oPartidas = (Object []) vPartidas.get(i);
                      Partida partida = (Partida) vPartidas.get(i);;
                      listaPartidas.add(partida);
                    }
                    
                    // Asignar partidas a rondas
                    for (Ronda rda: listaRondas)
                    {
                        List arrPartidas = new ArrayList<Partida>();
                        for (Partida par: listaPartidas)
                        {
                            if(par.getPar_ron_id().equals(rda.getRon_id()))
                            {
                                arrPartidas.add(par);
                            }
                        }
                        rda.setPartidas(arrPartidas);
                    }
                    
                }
                
                // Cargo Relacion de Jugadores - Partidas 
                String qryJugadores = "  SELECT j.*, p.per_apellido as APELLIDO, p.per_nombres as NOMBRE\n" + 
                              "    FROM jugadores_torneos jt,\n" + 
                              "         jugadores j,\n" + 
                              "         personas p\n" + 
                              "   WHERE jt.jpt_tor_id = ?idTorneo\n" + 
                              "     AND j.jug_id = jt.jpt_jug_id\n" + 
                              "     AND p.per_id = j.per_id";  
                
                Query qJugadores = em.createNativeQuery(qryJugadores, "jugadoresMapping2");
                qJugadores.setParameter("idTorneo", torId);
                
                List vJugadores = qJugadores.getResultList();
                List<Jugador> listaJugadores = new ArrayList<Jugador>();
                
                if (vJugadores != null && vJugadores.size() > 0)
                {
                    for (int i = 0; i < vJugadores.size(); i++)
                    {
                        Object[] oJugador = (Object []) vJugadores.get(i);
                        Jugador jugador = (Jugador)oJugador[0];
                        
                        jugador.setJugApellido((String)oJugador[1]);
                        jugador.setJugNombre((String)oJugador[2]);
                        listaJugadores.add(jugador);
                    }
                    
                    // Asignar jugadores a partidas
                    for (Ronda rda: listaRondas)
                    {
                        if (rda.getPartidas() != null && rda.getPartidas().size() > 0)
                        {
                            for (Partida par: rda.getPartidas())
                            {
                                for (Jugador jug: listaJugadores)
                                {
                                    if (jug.getJug_id().equals(par.getPar_jug_blancas()))
                                    {
                                        par.setJugadorBlancas(jug);
                                    }
                                    if (jug.getJug_id().equals(par.getPar_jug_negras()))
                                    {
                                        par.setJugadorNegras(jug);
                                    }
                                }
                            }
                        }
                    }
                }

                torneo.setRondas(listaRondas);
            }
            t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally
        {
            em.close();
        }        
        
        return torneo;
    }
    
    public List<Torneo> qTorneos (Map filtros)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        List<Torneo> ret = null;
        
        Long estado = filtros.get("estado") != null? (Long)filtros.get("estado"): null;
        
        String strQ =   "SELECT t.*, \n" + 
                        "       p.par_valor as sistema,\n" + 
                        "       p2.par_valor as tipotorneo\n" + 
                        "  FROM torneos t, \n" + 
                        "       parametros p,\n" + 
                        "       parametros p2\n" + 
                        "WHERE p.par_tipo = 'SISTEMAS'\n" + 
                        "  AND p2.par_tipo = 'TIPOS_TORNEO'\n" + 
                        "  AND t.tor_sistema = p.par_codigo\n" + 
                        "  AND t.tor_tipo = p2.par_codigo\n";

        if (estado != null)
            strQ += "  AND t.tor_estado = " + estado + "\n";

        strQ += "ORDER BY tor_fecha_inicio DESC";

        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();

                Query q = em.createNativeQuery (strQ, "torneosMap");
                
                List v = q.getResultList();
                ret = new ArrayList<Torneo>(v.size());
                
                for (int i = 0; i < v.size(); i++)
                {
                    Object[] o = (Object []) v.get(i);
                    Torneo trn = (Torneo) o[0];
                    trn.setSistema((String)o[1]);
                    trn.setTipoTorneo((String)o[2]);
                    ret.add(trn);
                }
                
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        
        return ret;
    }
    
    public List<JugadorTorneoDTO> getJugadoresTorneo(Long idTorneo)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        List <JugadorTorneoDTO> ret = null;
        
        String strQ = "  SELECT jt.*, p.per_apellido as APELLIDO, p.per_nombres as NOMBRE\n" + 
                      "    FROM jugadores_torneos jt,\n" + 
                      "         jugadores j,\n" + 
                      "         personas p\n" + 
                      "   WHERE jt.jpt_tor_id = ?idTorneo\n" + 
                      "     AND j.jug_id = jt.jpt_jug_id\n" + 
                      "     AND p.per_id = j.per_id\n" + 
                      "ORDER BY jt.jpt_posicion_final";

        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                Query qry = em.createNativeQuery(strQ, "jugadoresTorneoMapping");
                qry.setParameter("idTorneo", idTorneo);
        
                List v = qry.getResultList();
                ret = new ArrayList<JugadorTorneoDTO>();
                for (int i = 0; i < v.size(); i++)
                {
                    Object[] o = (Object []) v.get(i);
                    JugadorTorneo jug = (JugadorTorneo)o[0];
                    
                    JugadorTorneoDTO jugDTO = new JugadorTorneoDTO(jug);
                    jugDTO.setJugApellido((String)o[1]);
                    jugDTO.setJugNombre((String)o[2]);
                    
                    ret.add(jugDTO);
                }
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return ret;
    }
    
    public List<PremioTorneo> getPremiosTorneo(Long idTorneo)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        List<PremioTorneo> ret = null;

        String strQ = "SELECT pt.*, p.per_apellido as APELLIDO, p.per_nombres as NOMBRE\n" + 
                      "  FROM premios_torneos pt, \n" + 
                      "       jugadores j, \n" + 
                      "       personas p \n" + 
                      " WHERE j.jug_id = pt.pre_jug_id\n" + 
                      "   AND p.per_id = j.per_id\n" + 
                      "   AND pt.pre_tor_id = ?idTorneo \n" + 
                      " ORDER BY pt.pre_id";

        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                Query qry = em.createNativeQuery(strQ, "premiosTorneoMapping");
                qry.setParameter("idTorneo", idTorneo);
                
                
                List v = qry.getResultList();
                ret = new ArrayList<PremioTorneo>(v.size());
                
                for (int i = 0; i < v.size(); i++)
                {
                    Object[] o = (Object []) v.get(i);
                    PremioTorneo pt = (PremioTorneo) o[0];
                    pt.setApellido((String)o[1]);
                    pt.setNombre((String)o[2]);
                    ret.add(pt);
                }
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return ret;
    }
    
    public void persistPgnList (List<BasePgn> lista)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                if (lista.size() > 0)
                {
                    for (BasePgn b: lista){
                        BasePgn n = new BasePgn(b);
                        em.persist(n);
                    }
                }
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        return;
    }
    
    public Torneo persistTorneo(Torneo torneo, 
                                List <RondaDTO> listRondasDTO, 
                                List<JugadorDTO> jugadoresDTO)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        Torneo torneoRetorno = null;
        String ret = "OK";
        
        try
        {
            try 
            {
                EntityTransaction t = em.getTransaction();
                try
                {
                    t.begin();
            
                    // Guardar datos del Torneo
                    
                    if (torneo.getTor_id() == null)
                        torneo.setTor_id(this.getSequence("get_seq_torneos()"));
                        
                    em.merge(torneo);
                    
                    // Guarda lista de jugadores
                    
                    // Primero elimina los viejos
                    if (torneo.getJugadoresTorneo() != null && torneo.getJugadoresTorneo().size() > 0)
                    {
                        for (JugadorTorneo j: torneo.getJugadoresTorneo())
                        {
                            JugadorTorneo removeThis = em.find(JugadorTorneo.class, new JugadorTorneoPK(j.getJpt_jug_id(), j.getJpt_tor_id()) );
                            em.remove(removeThis);
                        }
                    }
                    em.flush();
                    
                    // Agrega los actuales
                    if (jugadoresDTO != null && jugadoresDTO.size() > 0)
                        for (JugadorDTO rem: jugadoresDTO)
                        {
                            if (!rem.getJugId().equals(0L))
                            {
                                JugadorTorneo jug = new JugadorTorneo(rem, torneo.getTor_id());
                                em.persist(jug);
                            }
                        }
                    
                    
                    // 3 - Rondas y Partidas

                    // Recorrer entidades de rondas (y partidas hijas) y quitar las que se hayan eliminado
                    if (torneo.getRondas() != null && torneo.getRondas().size() > 0)
                    {
                        for (Ronda r: torneo.getRondas())
                        {
                            if (!existeRonda(listRondasDTO, r.getRon_id()))
                            {
                                // Remover partidas
                                if (r.getPartidas() != null && r.getPartidas().size() > 0)
                                {
                                    for (Partida pr: r.getPartidas())
                                    {
                                        Partida premove = em.find(Partida.class, pr.getPar_id());
                                        em.remove(premove);
                                    }
                                }
                                
                                RondaPK rondaPK = new RondaPK(r.getRon_id(), r.getRon_tor_id());
                                Ronda rondaEliminar = em.find(Ronda.class, rondaPK);
                                em.remove(rondaEliminar);
                            }
                            else
                            {
                                // Remover partidas quitadas, y agregar nuevas
                                
                                // Quitar las partidas que se hayan quitado
                                if (r.getPartidas() != null && r.getPartidas().size() > 0)
                                {
                                    List aRemover = new ArrayList<Partida>();
                                    for (Partida partida: r.getPartidas())
                                    {
                                        if (!existePartida(listRondasDTO, r.getRon_id(), partida.getPar_id()))
                                        {
                                            aRemover.add(partida);
                                            Partida parEliminar = em.find(Partida.class, partida.getPar_id());
                                            em.remove(parEliminar);
                                        }
                                        else
                                        {
                                            // Actualizo la partida por si se cambiaron los datos (jugadores, resultado)
                                            em.merge(partida);
                                        }
                                    }
                                    if (aRemover.size() > 0) r.getPartidas().removeAll(aRemover);
                                }
                                // Agregar las nuevas partidas (id < 0)
                                if (listRondasDTO != null && listRondasDTO.size() > 0)
                                for (RondaDTO rdto: listRondasDTO)
                                {
                                    if (rdto.getRonda().getRon_id().equals(r.getRon_id())) // Me ubico en la ronda
                                    {
                                        if (rdto.getListaPartidas() != null && rdto.getListaPartidas().size() > 0)
                                        for (PartidaDTO p: rdto.getListaPartidas())
                                        {
                                            if (p.getPartida().getPar_id() < 0)
                                            {
                                                // Asignar ID
                                                //rdto.getRonda().setMaxParId(rdto.getRonda().getMaxParId() + 1L);
                                                p.getPartida().setPar_id(this.getSequence("get_seq_partidas()"));
                                                r.agregarPartida(p.getPartida());
                                                
                                                Partida parNueva = new Partida(p.getPartida());
                                                em.persist(parNueva);                                                
                                            }
                                        }
                                        
                                        break;
                                    }
                                }                                
                                
                            } // Fin existe ronda
                        }
                    }
                    
                    //   Agregar las nuevas (id < 0) (incluye todas las partidas)
                    if (listRondasDTO != null && listRondasDTO.size() > 0)
                    {
                        for (RondaDTO rDTO: listRondasDTO)
                        {
                            if (rDTO.getRonda().getRon_id() < 0)
                            {
                                Ronda rondaNueva = new Ronda(rDTO);
                                torneo.setMaxRonId(torneo.getMaxRonId() == null? 1L: torneo.getMaxRonId() + 1L);
                                rondaNueva.setRon_id(torneo.getMaxRonId());
                                rondaNueva.setRon_tor_id(torneo.getTor_id());
                                
                                torneo.agregarRonda(rondaNueva);
                                
                                // Agregar todas las partidas
                                if (rDTO.getListaPartidas() != null && rDTO.getListaPartidas().size() > 0)
                                    for (PartidaDTO partida: rDTO.getListaPartidas())
                                    {
                                        partida.getPartida().setPar_id(this.getSequence("get_seq_partidas()"));
                                        partida.getPartida().setPar_ron_id(rondaNueva.getRon_id());
                                        partida.getPartida().setPar_tor_id(torneo.getTor_id());
                                        Partida parNueva = new Partida(partida.getPartida());
                                        
                                        em.persist(parNueva);
                                    }
                            }
                        }
                    }
                    
                    // Renumerar rondas
                    if (torneo.getRondas() != null && torneo.getRondas().size() > 0)
                    {
                        long ron_num = 0;
                        
                        for (Ronda r: torneo.getRondas())
                        {
                            ron_num++;
                            r.setRon_numero(ron_num);
                            
                            em.merge(r);
                        }
                    }
                    
                    t.commit();
                    torneoRetorno = this.qTorneoCompleto(torneo.getTor_id());
                }
                finally
                {
                    if (t.isActive()) t.rollback();
                }
            }
            finally
            {
                em.close();
            }

        }
        catch (Exception e)
        {
            String msj = e.getMessage();
            e.printStackTrace();
            ret = "Error general al guardar datos. " + (msj!=null?msj:"");
        }
        
        return torneoRetorno;
    }
    
    private boolean existeRonda(List<RondaDTO> listaRondasDTO, Long ronId)
    {
        boolean ret = false;
        
        if (listaRondasDTO != null)
        for (RondaDTO r: listaRondasDTO)
        {
            if (r.getRonda().getRon_id().equals(ronId))
            {
                ret = true;
                break;
            }
        }
        
        return ret;
    }    
    
    public boolean existePartida(List<RondaDTO> listaRondasDTO, Long ronId, Long parId)
    {
        boolean ret = false;
        
        if (listaRondasDTO != null && listaRondasDTO.size() > 0)
        for (RondaDTO r: listaRondasDTO)
        {
            if (r.getRonda().getRon_id().equals(ronId))
            {
                if (r.getListaPartidas() != null && r.getListaPartidas().size() > 0)
                for (PartidaDTO p: r.getListaPartidas())
                {
                    if (p.getPartida().getPar_id().equals(parId))
                    {
                        ret = true;
                        break;
                    }
                }
            }
            if (ret) break;
        }
        
        return ret;
    }

    // TODO
    public JugadorDTO getJugadorDTO (Long jugId)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        JugadorDTO jdto = null;
        String strQ = "select jug.*, per.per_apellido as APELLIDO, per.per_nombres as NOMBRE\n" + 
                        "  from jugadores jug\n" + 
                        "  left join personas per on per.per_id = jug.per_id\n" + 
                        " where jug.jug_id = ?jugId";

        try
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                
                Query qry = em.createNativeQuery(strQ, "jugadoresMapping2");
                qry.setParameter("jugId", jugId);
                
                List lista = qry.getResultList();
                if (lista != null && lista.size() > 0)
                {
                    Object[] o = (Object []) lista.get(0);
                    
                    Jugador jug = (Jugador)o[0];
                    jdto = new JugadorDTO();
                    
                    jdto.setJugId(jug.getJug_id());
                    jdto.setPerId(jug.getPer_id());
                    jdto.setJugApellido((String)o[1]);
                    jdto.setJugNombre((String)o[2]);
                    jdto.setPuntaje(0D);
                    jdto.setClasifica(false);
                    jdto.setGrauActual(jug.getJug_elo_actual());
                        
                }
                t.commit();                
        
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        
        return jdto;
    }
    
    public JugadorDTO getJugadorBySoundex (String nombre)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        JugadorDTO ret = null;
        
        String strQ = "SELECT j.*, p.per_apellido as APELLIDO, p.per_nombres as NOMBRE\n" + 
                      "FROM jugadores j,\n" + 
                      "     personas p \n" + 
                      "WHERE j.per_id = p.per_id\n" + 
                      "  AND ((SOUNDEX(CONCAT(p.per_apellido, p.per_nombres)) LIKE SOUNDEX(?nombre))\n" + 
                      "    OR (SOUNDEX(CONCAT(p.per_nombres, p.per_apellido)) LIKE SOUNDEX(?nombre)))";
        
        try
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                
                Query qry = em.createNativeQuery(strQ, "jugadoresMapping2");
                qry.setParameter("nombre", nombre);
                
                List lista = qry.getResultList();
                if (lista != null && lista.size() > 0)
                {
                    List<JugadorDTO> arr = new ArrayList<JugadorDTO>(lista.size());
                    
                    for (int i=0; i<lista.size();i++)
                    {
                        Object[] o = (Object []) lista.get(i);
                        Jugador jug = (Jugador)o[0];
                        JugadorDTO jdto = new JugadorDTO();
                        
                        jdto.setJugId(jug.getJug_id());
                        jdto.setPerId(jug.getPer_id());
                        jdto.setJugApellido((String)o[1]);
                        jdto.setJugNombre((String)o[2]);
                        jdto.setPuntaje(0D);
                        jdto.setClasifica(false);
                        jdto.setGrauActual(jug.getJug_elo_actual());
                        
                        arr.add(jdto);
                    }
                    ret = arr.get(0);
                }
                t.commit();                
        
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        
        return ret;
    }
    
    public List <JugadorDTO> getJugadores (Map filtros)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        List<JugadorDTO> ret = null;
        
        Long torId = (Long)filtros.get("idTorneo");
        
        String strQ = "SELECT j.*, p.per_apellido as APELLIDO, p.per_nombres as NOMBRE, null as PUNTAJE, 'N' as CLASIFICA, null as POSICION \n" +
                      "FROM jugadores j \n " +
                      "LEFT JOIN personas p ON (j.per_id = p.per_id)\n " +
                      //"WHERE 1=1 \n" +
                      "ORDER BY p.per_apellido ASC, p.per_nombres ASC";
        
        if (torId!=null)
        {
            strQ  = "select jug.*, " +
                    "       per.per_apellido as APELLIDO, " +
                    "       per.per_nombres as NOMBRE, " +
                    "       jt.jpt_puntaje_final as PUNTAJE, " +
                    "       jt.jpt_clasifica as CLASIFICA, " +
                    "       jt.jpt_posicion_final as POSICION \n" + 
                    "  from jugadores jug\n" + 
                    "  left join personas per on per.per_id = jug.per_id\n" + 
                    " inner join jugadores_torneos jt on jug.jug_id = jt.jpt_jug_id\n" + 
                    " where jt.jpt_tor_id = ?torId " +
                    " order by jt.jpt_posicion_final";
        }

        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
        
                Query qry = em.createNativeQuery(strQ, "jugadoresMapping");
                qry.setParameter("torId", torId);
                
                List lista = qry.getResultList();
                if (lista != null && lista.size() > 0)
                {
                    ret = new ArrayList<JugadorDTO>(lista.size());
                    
                    for (int i=0; i<lista.size();i++)
                    {
                        Object[] o = (Object []) lista.get(i);
                        Jugador jug = (Jugador)o[0];
                        JugadorDTO jdto = new JugadorDTO();
                        
                        jdto.setJugId(jug.getJug_id());
                        jdto.setPerId(jug.getPer_id());
                        jdto.setJugApellido((String)o[1]);
                        jdto.setJugNombre((String)o[2]);
                        jdto.setGrauActual(jug.getJug_elo_actual());
                        jdto.setPuntaje(o[3] != null? ((Double)o[3]) : 0D);
                        jdto.setClasifica(o[4] != null && ((String)o[4]).equals("S"));
                        jdto.setPosicion(o[5] != null? ((Integer)o[5]) : null);
                        
                        ret.add(jdto);
                    }
                }
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }                
        return ret;
    }

    public List <JugadorDTO> getJugadoresRankingDTO ()
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        List<JugadorDTO> ret = null;
        
        String strQ = "SELECT j.*, p.per_apellido as APELLIDO, p.per_nombres as NOMBRE \n" + 
                      "FROM jugadores j\n" + 
                      "LEFT JOIN personas p ON (j.per_id = p.per_id)\n" + 
                      "WHERE j.jug_elo_actual > 0 AND j.jug_activo = 1 \n" + 
                      "ORDER BY j.jug_elo_actual DESC, p.per_apellido ASC, p.per_nombres ASC";

        try 
        {
            EntityTransaction t = em.getTransaction();
            try
            {
                t.begin();
                Query qry = em.createNativeQuery(strQ, "jugadoresMapping");
                List lista = qry.getResultList();
                
                if (lista != null && lista.size() > 0)
                {
                    ret = new ArrayList<JugadorDTO>(lista.size());
                    for (int i=0; i<lista.size();i++)
                    {
                        Object[] o = (Object []) lista.get(i);
                        Jugador jug = (Jugador)o[0];
  
                        Integer cat = 4;
                        String bgColor = "#D9E5FC;";
                        
                        if (jug.getJug_elo_actual() != null && jug.getJug_elo_actual() >= 2200L)
                        {
                            cat = 1;
                            bgColor = "#FCD9D9;";
                        }
                        else if ((jug.getJug_elo_actual() != null && jug.getJug_elo_actual() >= 2050L))
                        {
                            cat = 2;
                            bgColor = "#FCF1D9;";
                        }
                        else if ((jug.getJug_elo_actual() != null && jug.getJug_elo_actual() >= 1850L))
                        {
                            cat = 3;
                            bgColor = "#E8FCD9;";
                        }

                        JugadorDTO jugDTO = new JugadorDTO(jug);
                        jugDTO.setCategoria(cat);
                        jugDTO.setBackgroundColor(bgColor);
                        jugDTO.setJugApellido((String)o[1]);
                        jugDTO.setJugNombre((String)o[2]);                        
                        
                        ret.add(jugDTO);
                    }
                }
                t.commit();
            }
            finally
            {
                if (t.isActive()) t.rollback();
            }
        }
        finally
        {
            em.close();
        }
        
        return ret;
    }
    
    public List<GrauRanking> qGrauRanking(Map filtros)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        String strQ = "SELECT gr.*,\n" + 
                      "       (SELECT COUNT(1) FROM grau_torneos gt WHERE gt.gtr_rkg_id = gr.rkg_id) AS cant_torneos\n" + 
                      "  FROM grau_ranking gr\n" + 
                      " WHERE rkg_id >= 12\n" +
                      " ORDER BY rkg_id DESC";
        
        List<GrauRanking> res = null;
        
        /*
        Long pRnkId = (Long)filtros.get("pRnkId");
        Boolean pUltimo = (Boolean)filtros.get("pUltimo");
        
        if (pRnkId != null)
        {
            strQ += "   AND rkg_id = ?p_rnk_id \n";
        }
        if (pUltimo != null)
        {
            if (pUltimo.booleanValue())
                strQ += "   AND rnk_id = (SELECT MAX(rnk_id) FROM grau_ranking)";
        }
        */
        
        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();  
            
            Query q = em.createNativeQuery(strQ, "grauMapping");
            //q.setParameter("p_rnk_id", pRnkId);
            
            List lista = q.getResultList();
            if (lista != null && lista.size() > 0)
            {
                res = new ArrayList<GrauRanking>(lista.size());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                
                SimpleDateFormat sdf2 = new SimpleDateFormat("MMMM yyyy");
                
                
                for (int i=0; i<lista.size();i++)
                {
                    Object[] o = (Object []) lista.get(i);
                    GrauRanking reg = (GrauRanking)o[0];
                    
                    reg.setCantidadTorneos((Long)o[1]);
                    
                    String fecha = sdf.format(reg.getRkg_fecha());
                    
                    String strfecha = sdf2.format(reg.getRkg_fecha());
                    strfecha = strfecha.substring(0, 1).toUpperCase() + strfecha.substring(1); // mes en mayúscula
                    
                    reg.setArchivoActivos(fecha + "_activos.pdf");
                    reg.setArchivoGral(fecha + "_gral.pdf");
                    reg.setArchivoNuevos(fecha + "_nuevos.pdf");
                    reg.setArchivoVariacionesSumadas(fecha + "_variacionesSumadas.pdf");
                    reg.setStrFecha(strfecha);
                    
                    res.add(reg);
                }                
            }
        
            t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally
        {
            em.close();
        }        
        return res;
    }

    public List<GrauHistorial> qGrauHistorial (Long idJugador)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        List<GrauHistorial> res = null;
        
        String strQ = "   SELECT gr.rkg_id, \n" + 
                      "          gr.rkg_fecha, \n" + 
                      "          gh.his_puntaje, \n" + 
                      "          gh.his_variacion, \n" + 
                      "          gh.his_categoria, \n" + 
                      "          gh.his_categoria_anterior\n" + 
                      "     FROM grau_ranking gr \n" + 
                      "LEFT JOIN grau_historial gh ON (gr.rkg_id = gh.his_rkg_id AND his_jug_id = ?pJugId)\n" +
                      "    WHERE EXISTS (SELECT 1 FROM grau_historial WHERE his_jug_id = ?pJugId)";
        
        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();  
            
            Query q = em.createNativeQuery(strQ);
            q.setParameter("pJugId", idJugador);
            
            List lista = q.getResultList();
            
            if (lista != null && lista.size() > 0)
            {
                res = new ArrayList<GrauHistorial>(lista.size());
                boolean primerRanking = false;
                Integer puntajeAnterior = null;
                Integer categoriaAnterior = null;
                Boolean participo = null;
                
                for (int i = 0; i<lista.size(); i++)
                {
                    Object[] o = (Object[])lista.get(i);
                    
                    Long rkgId = o[0] != null? (Long)o[0]: null;
                    Date rkgFecha = o[1] != null? (Date)o[1]: null;
                    Integer hisPuntaje = o[2] != null? (Integer)o[2]: null;
                    Integer hisVariacion = o[3] != null? (Integer)o[3]: null;
                    Integer hisCategoria = o[4] != null? (Integer)o[4]: null;
                    Integer hisCatAnterior = o[5] != null? (Integer)o[5]: null;
                    
                    // Algoritmo que descarta los ranking en que no participo, hasta que encuentre uno que sí.
                    if (!primerRanking)
                    {
                        if (hisPuntaje == null)
                            continue;
                        else
                            primerRanking = true;
                    }
                    
                    participo = Boolean.valueOf(true);
                    if (hisPuntaje == null)
                    {
                        hisPuntaje = puntajeAnterior;
                        hisCategoria = categoriaAnterior;
                        hisCatAnterior = categoriaAnterior;
                        participo = Boolean.valueOf(false);
                    }
                    
                    // Carga los datos en la entidad
                    GrauHistorial gh = new GrauHistorial();
                    
                    gh.setHis_rkg_id(rkgId);
                    gh.setHis_jug_id(idJugador);
                    gh.setRkg_fecha(rkgFecha);
                    gh.setHis_puntaje(hisPuntaje.longValue());
                    gh.setHis_variacion(hisVariacion!=null? hisVariacion.longValue(): null);
                    gh.setHis_categoria(hisCategoria != null?hisCategoria.longValue(): null);
                    gh.setHis_categoria_anterior(hisCatAnterior != null? hisCatAnterior.longValue(): null);
                    gh.setParticipo(participo);
                    
                    res.add(gh);
                    
                    puntajeAnterior = hisPuntaje;
                    categoriaAnterior = hisCategoria;
                } // fin For
            }
        
            t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally
        {
            em.close();
        }        
        return res;
    }

    /*
     * Devuelve las personas que coinciden con los parámetros, aunque sea
     * de manera aproximada. Implementa el algoritmo SOUNDEX de MySQL.
     * 
     * Se utiliza para comparar los nombres que vienen en archivos de Swiss Perfect
     * contra los de la base de datos.
     */
    public List<Persona> qPersonasPorNombreyApellido(String apellido, String nombre)
    {
        EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        
        String strQ = "SELECT * \n" +
                      "  FROM personas as p\n" + 
                      " WHERE SOUNDEX(p.per_apellido) = SOUNDEX(?pApellido)\n" + 
                      "   AND (SOUNDEX(p.per_nombres) = SOUNDEX(?pNombre) OR\n" + 
                      "       SOUNDEX(SUBSTRING_INDEX(p.per_nombres,' ', 1)) = SOUNDEX(?pNombre))";
        
        List<Persona> ret = null;

        try 
        {
          EntityTransaction t = em.getTransaction();
          try
          {
            t.begin();            
            
            Query q = em.createNativeQuery(strQ, Persona.class);
            q.setParameter("pApellido", apellido);
            q.setParameter("pNombre", nombre);
            
            ret = q.getResultList();
            t.commit();
          }
          finally
          {
            if (t.isActive()) t.rollback();
          }
        }
        finally
        {
            em.close();
        }
        
        return ret;
    }
}
