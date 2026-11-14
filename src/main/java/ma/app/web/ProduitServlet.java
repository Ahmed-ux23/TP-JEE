package ma.app.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.app.model.Produit;
import ma.app.model.ProduitDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    private ProduitDAO dao;

    @Override
    public void init() {
        
        this.dao = (ProduitDAO) getServletContext().getAttribute("produitDAO");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null || action.isEmpty()) {
            
            req.setAttribute("produits", dao.findAll());
            req.getRequestDispatcher("/WEB-INF/views/produits/list.jsp").forward(req, resp);
        } else if ("new".equals(action)) {
            
            req.setAttribute("produit", new Produit());
            req.getRequestDispatcher("/WEB-INF/views/produits/form.jsp").forward(req, resp);
        } else if ("edit".equals(action)) {
            
            try {
                Long id = Long.parseLong(req.getParameter("id"));
                Produit p = dao.findById(id);
                if (p == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Produit introuvable");
                    return;
                }
                req.setAttribute("produit", p);
                req.getRequestDispatcher("/WEB-INF/views/produits/form.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "ID invalide");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

if ("delete".equals(action)) {
            try {
                Long id = Long.parseLong(req.getParameter("id"));
                dao.delete(id);
                req.getSession().setAttribute("flash", "Produit supprimé avec succès.");
            } catch (Exception ignored) {}
            resp.sendRedirect(req.getContextPath() + "/produits");
            return;
        }

String idStr = req.getParameter("id");
        String nom = req.getParameter("nom");
        String description = req.getParameter("description");
        String prixStr = req.getParameter("prix");
        String stockStr = req.getParameter("stock");

        Map<String, String> erreurs = new HashMap<>();

if (nom == null || nom.trim().length() < 2 || nom.trim().length() > 50) {
            erreurs.put("nom", "Le nom doit contenir entre 2 et 50 caractères.");
        }

if (description != null && description.length() > 255) {
            erreurs.put("description", "La description ne doit pas dépasser 255 caractères.");
        }

BigDecimal prix = null;
        try {
            prix = new BigDecimal(prixStr);
            if (prix.compareTo(BigDecimal.ZERO) <= 0) {
                erreurs.put("prix", "Le prix doit être strictement positif.");
            }
        } catch (Exception e) {
            erreurs.put("prix", "Le prix doit être un nombre valide.");
        }

int stock = 0;
        if (stockStr != null && !stockStr.trim().isEmpty()) {
            try {
                stock = Integer.parseInt(stockStr);
                if (stock < 0) {
                    erreurs.put("stock", "Le stock doit être supérieur ou égal à 0.");
                }
            } catch (NumberFormatException e) {
                erreurs.put("stock", "Le stock doit être un nombre entier.");
            }
        }

        Long id = (idStr != null && !idStr.isEmpty()) ? Long.parseLong(idStr) : null;
        Produit p = new Produit(id, nom, description, prix, stock);

        if (!erreurs.isEmpty()) {
            
            req.setAttribute("erreurs", erreurs);
            req.setAttribute("produit", p);
            req.getRequestDispatcher("/WEB-INF/views/produits/form.jsp").forward(req, resp);
        } else {
            
            dao.save(p);
            String msg = (id == null) ? "Produit " + nom + " ajouté !" : "Produit " + nom + " modifié !";
            req.getSession().setAttribute("flash", msg);
            resp.sendRedirect(req.getContextPath() + "/produits");
        }
    }
}
