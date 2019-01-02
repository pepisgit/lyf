package model.servicio;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PersistenceAppListener implements ServletContextListener
{
    public void contextInitialized(ServletContextEvent evt) {
    }

    public void contextDestroyed(ServletContextEvent evt) {
    
      PersistenceManager.getInstance().closeEntityManagerFactory();
    }
}
