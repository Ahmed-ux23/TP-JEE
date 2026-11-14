<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion</title>
</head>
<body>
    <h1>Connexion</h1>
    <c:if test="${not empty erreur}">
        <p style="color: red;"><c:out value="${erreur}"/></p>
    </c:if>
    <form method="post" action="${ctx}/login">
        <label>Nom d'utilisateur :</label><br>
        <input type="text" name="username" value="<c:out value='${username}'/>" required><br><br>
        <label>Mot de passe :</label><br>
        <input type="password" name="password" required><br><br>
        <button type="submit">Se connecter</button>
    </form>
</body>
</html>
