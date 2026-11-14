<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>Catalogue Produits</title>
</head>
<body>
    <h1>Catalogue de produits</h1>

    <%-- Affichage et suppression du message flash --%>
    <c:if test="${not empty sessionScope.flash}">
        <p style="color: green;"><c:out value="${sessionScope.flash}"/></p>
        <c:remove var="flash" scope="session"/>
    </c:if>

    <p><a href="${ctx}/produits?action=new">Ajouter un produit</a></p>

    <c:choose>
        <c:when test="${empty produits}">
            <p>Aucun produit disponible.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8" cellspacing="0">
                <thead>
                    <tr>
                        <th>Nom</th>
                        <th>Description</th>
                        <th>Prix</th>
                        <th>Stock</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${produits}">
                        <tr>
                            <td><c:out value="${p.nom}"/></td>
                            <td><c:out value="${p.description}"/></td>
                            <td>
                                <fmt:formatNumber value="${p.prix}" type="currency" currencySymbol="DH"/>
                            </td>
                            <td style="${p.stock == 0 ? 'color: red; font-weight: bold;' : ''}">
                                <c:out value="${p.stock}"/>
                            </td>
                            <td>
                                <a href="${ctx}/produits?action=edit&id=${p.id}">Éditer</a>
                                |
                                <form action="${ctx}/produits?action=delete" method="post" style="display:inline;" onsubmit="return confirm('Supprimer ce produit ?');">
                                    <input type="hidden" name="id" value="${p.id}">
                                    <button type="submit">Supprimer</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="${ctx}/dashboard">Retour au tableau de bord</a>
</body>
</html>