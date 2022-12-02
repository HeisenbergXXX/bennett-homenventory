<%--
  Created by IntelliJ IDEA.
  User: Bennett
  Date: 16-Oct.-22
  Time: 22:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style><%@include file="/WEB-INF/A2Style.css" %></style>
<script>function deactivate(){alert("By deactivating your account, you won't be able to log back in!")}</script>
<html>
<head>
    <title>Account page</title>
</head>
<body class="adminPage">

        <div class="dropdown">
            <h1>HOME nVentory</h1>
            <button type="button" class="dropbtn">
                Hi, ${user.firstName} ${user.lastName}
                <img src="menu.png" alt="menu" height="25px">
            </button>

            <div class="dropdown-content">
                <p class="msgMenu"><c:out value= "${user.isAdmin? 'Admin ': 'Regular '}"></c:out>User</p>
                <p class="msgMenu">Status: <c:out value= "${user.active? 'Active ': 'Inactive '}"></c:out></p>
                <a href="account">Manage account</a>
                <a href="inventory">Inventory page</a>
                <a href="admin">Admin page</a>
                <a href="login?logout" id="logoutL">Logout</a>
            </div>
        </div>
        <div class="container3">
        <h2>Manage Users  (Logged in as: ${user.username})</h2>
        <table class="mainT">
            <tr>
                <th>Username</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Status</th>
                <th>Role</th>
                <th></th>
            </tr>
                <tr>
                    <td id="un">${user.username}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.active ? 'Active' : 'Inactive'} </td>
                    <td>${user.isAdmin? 'Admin' : 'Regular User'}</td>
                    <td>
                        <form action="account" method="get" class="smallF">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="usernameS" value="${user.username}">
                            <input type="submit" value="Edit" class="small">
                        </form>
                    </td>
                </tr>
        </table>
        <c:if test="${actionMsg != null}">
            <p class="msg">${actionMsg}</p>
        </c:if>
        <c:if test="${actionError != null}">
            <p class="error">${actionError}</p>
        </c:if>
        <h2>
            <c:if test="${action == 'edit'}">Edit User</c:if>
        </h2>
        <c:if test="${action == 'edit'}">
        <form action="account" method="post">
            <table class="edit">
                <tr>
                    <td>Username</td>
                    <td><input type="text" name="username" value="${userE.username}" readonly></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="text" name="password" value="${userE.password}" required></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="text" name="email" value="${userE.email}" required></td>
                </tr>
                <tr>
                    <td>First Name</td>
                    <td><input type="text" name="firstName" value="${userE.firstName}" required></td>
                </tr>
                <tr>
                    <td>Last Name</td>
                    <td><input type="text" name="lastName" value="${userE.lastName}" required></td>
                </tr>
                <tr>
                    <td>Active</td>
                    <td>
                        <input type="checkbox" name="active" id="active" value="true" ${userE.active ? 'checked' : ''} onclick="deactivate()">
                    </td>
                </tr>
                <tr>
                    <td><input type="hidden" name="action" value="update"></td>
                    <td><input type="submit" value="Update"></td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <a href="account">Cancel</a>
                    </td>
                </tr>
            </table>
        </form>
        </c:if>
    </div>
</body>
</html>
