<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 4/3/2022
  Time: 5:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Customer Details</title>
</head>
<body>
<%--<p><a href="/customers">Back to customer list</a></p>--%>
<h1>Customer Information</h1>
<table>
    <tr>
        <td>Name: </td>
        <td>${customer.name}</td>
    </tr>
    <tr>
        <td>Email:</td>
        <td>${customer.email}</td>
    </tr>
    <tr>
        <td>Address:</td>
        <td>${customer.address}</td>
    </tr>
    <tr>
        <td colspan="2"><button><a href="/customers">Back to customer list</a></button></td>
    </tr>
</table>
</body>
</html>
