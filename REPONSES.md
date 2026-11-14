# Réponses aux Questions Théoriques - TP3 JEE / MVC

## Question 1 : Forward vs Redirect lors du Login
* **En cas d'échec (Forward)** : On utilise un forward vers la vue login.jsp pour préserver l'état de la requête HTTP et transmettre l'attribut d'erreur tout en conservant le nom d'utilisateur saisi.
* **En cas de succès (Redirect)** : On utilise un sendRedirect vers /dashboard pour forcer le navigateur du client à faire une nouvelle requête HTTP GET (Pattern PRG), évitant la re-soumission des identifiants avec F5.

## Question 2 : Traitement après ajout de produit (F5 & Pattern PRG)
* **(a) Si doPost fait un forward vers la liste** : Un appui sur F5 re-soumet la requête POST, ce qui insère le produit une deuxième fois en doublon.
* **(b) Si doPost fait un redirect vers la liste** : La dernière requête effectuée est un GET /produits. Un appui sur F5 rafraîchit uniquement la liste sans ajouter de produit.
* **Nom du pattern** : Il s'agit du pattern PRG (Post / Redirect / Get).

## Question 3 : Injection XSS et Échappement des Données
* **Avec ${produit.nom}** : Le navigateur exécute le code JavaScript <script>alert('XSS')</script>, ce qui crée une faille XSS.
* **Avec <c:out value=""/>** : Les caractères spéciaux HTML sont automatiquement convertis en entités HTML, affichant le texte brut sans l'exécuter.
* **Conclusion** : Il faut toujours utiliser <c:out> ou fn:escapeXml pour afficher les données saisies par un utilisateur.

## Question 4 : Filtre d'Authentification (AuthFilter)
* **Avantages du filtre** : Il centralise le contrôle d'accès en un seul endroit au lieu de répéter la vérification dans chaque Servlet.
* **Deux autres usages classiques** :
  1. Gestion globale de l'encodage des caractères (ex: request.setCharacterEncoding("UTF-8")).
  2. Journalisation (logging) et mesure du temps d'exécution des requêtes.
