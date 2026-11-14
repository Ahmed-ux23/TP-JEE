package ma.app.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ma.app.model.ProduitDAO;

@WebListener
public class AppInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Crée une seule instance du DAO au démarrage et la stocke dans le ServletContext
        sce.getServletContext().setAttribute("produitDAO", new ProduitDAO());
    }
}