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
<html lang="en">
<head>
    <title>Inventory page</title>
</head>
    <body class="inventoryPage">

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
        <div class="container2">
        <c:if test="${message != null}">
            <p class="error">${message}</p>
        </c:if>
        <h2>Inventory List</h2>
        <table class="mainT">
            <tr>
                <th>Category</th>
                <th>Item</th>
                <th>Price</th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${items}" var="item">
                <tr>
                    <td>${item.category.categoryName}</td>
                    <td>${item.itemName}</td>
                    <td>$${item.price}</td>
                    <td style="width:5%">
                        <form action="inventory" method="post" class="smallF">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="itemID" value="${item.itemID}">
                            <input type="submit" name="action" value="Delete" class="small">
                        </form>
                    </td>
                    <td style="width:5%">
                        <form action="inventory" method="get" class="smallF">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="itemID" value="${item.itemID}">
                            <input type="submit" value="Edit" class="small">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${actionMsg != null}">
            <p class="msg">${actionMsg}</p>
        </c:if>
        <c:if test="${actionError != null}">
            <p class="error">${actionError}</p>
        </c:if>
        <h2>
            <c:if test="${action != 'edit'}">Add Item</c:if>
            <c:if test="${action == 'edit'}">Edit Item</c:if>
        </h2>
        <form action="inventory" method="post">
            <select name="category">
                <c:forEach items="${categories}" var="category">
                    <option value="${category.categoryID}" <c:if test="${item.getCategory() eq category}">selected</c:if>>
                            ${category.categoryName}
                    </option>
                </c:forEach>
            </select><br>
            <input type="text" name="itemName" value="${item.itemName}" placeholder="Item Name" pattern="^[a-zA-Z1-9].*" title="can not start with spaces" required/><br>
            <input type="number" name="itemPrice" value="${item.price}" placeholder="Price" min="0" required/><br>
            <c:if test="${action != 'edit'}">
                <input type="hidden" name="action" value="add">
                <input type="submit" value="Add"/><br>
            </c:if>
            <c:if test="${action == 'edit'}">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="itemID" value="${item.itemID}">
                <input type="hidden" name="oldCategory" value="${item.category.categoryID}">
                <input type="submit" value="Update"/><br>
            </c:if>
            <a href="inventory">Cancel</a>
        <c:if test="${inputError != null}">
            <p class="error">${inputError}</p>
        </c:if>
        </form>
    </div>

    </body>
</html>
