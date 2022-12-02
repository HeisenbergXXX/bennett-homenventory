<%--
  Created by IntelliJ IDEA.
  User: Bennett
  Date: 28-Oct-2022
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style><%@include file="/WEB-INF/A2Style.css" %></style>
<html>
<head>
    <title>Home nVentory Register Page</title>
</head>
    <body class="loginPage">
        <div class="container">
            <h1>HOME nVentory</h1>
            <img src="underline.png" alt="ul" height="35px">
            <form action="register" method="post">
                <input type="text" name="username" placeholder="Username" value="${username}" required>
                <input type="password" name="password" placeholder="Password" required>
                <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
                <input type="text" name="email" placeholder="Email" value="${email}" required>
                <input type="text" name="firstName" placeholder="First Name" value="${firstName}" required>
                <input type="text" name="lastName" placeholder="Last Name" value="${lastName}" required>
                <input type="submit" value="Register">
            </form>
            <p>Already have an account? <a href="login">Login</a></p>
            <c:if test="${errorMsg != null}">
                <p class="errorLogin">${errorMsg}</p>
            </c:if>
            <c:if test="${message != null}">
                <p class="msg">${message}</p>
            </c:if>
        </div>
    </body>
</html>
