<%--
  Created by IntelliJ IDEA.
  User: Bennett
  Date: 16-Oct.-22
  Time: 22:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style><%@include file="/WEB-INF/A2Style.css" %></style>
<html>
<head>
    <title>Home nVentory Login Page</title>
</head>
    <body class="loginPage">
        <div class="container">
            <h1>HOME nVentory</h1>
            <img src="underline.png" alt="ul" height="35px">
            <form action="login" method="post">
               <input type="text" name="username" placeholder="Username" value="${username}"/>
                <br>
               <input type="password" name="password" placeholder="Password"/>
                <br>
               <input type="submit" value="Login"/>
            </form>
            <c:if test="${errorMsg != null}">
                <p class="errorLogin">${errorMsg}</p>
            </c:if>
            <c:if test="${message != null}">
                <p class="msg">${message}</p>
            </c:if>
            <p>Don't have an account? <a href="register">Register</a></p>
        </div>
    </body>
</html>
