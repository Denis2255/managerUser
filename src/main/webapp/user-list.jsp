<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ADMIN</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
          crossorigin="anonymous">
    <style type="text/css">
        a.q {
            background: -moz-linear-gradient(#1b641f, #EBFFFF);
            background: -webkit-gradient(linear, 0 0, 0 100%, from(#0ed600), to(#EBFFFF));
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#00BBD6', endColorstr='#EBFFFF');
            padding: 3px 7px;
            color: #333;
            -moz-border-radius: 5px;
            -webkit-border-radius: 5px;
            border-radius: 5px;
            border: 1px solid #111111;
        }
    </style>
</head>
<body>
<div class="row">
    <div class="container">
        <h3 class="text-center">List of Users</h3>
        <hr>

        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone number</th>
                <th>Language</th>
                <th>Country</th>
                <th>Role</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach var="user" items="${listUser}">

                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td><c:out value="${user.phoneNumber}"/></td>
                    <td><c:out value="${user.language}"/></td>
                    <td><c:out value="${user.country}"/></td>
                    <td><c:out value="${user.roleList}"/></td>
                    <td><a href="edit?id=<c:out value='${user.id}' />">Edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp; <a href="delete?id=<c:out value='${user.id}' />">Delete</a></td>
                </tr>
            </c:forEach>

            </tbody>

        </table>
        <div class="container text-left">

            <a href="<%=request.getContextPath()%>/new" class="btn btn-success">Add
                New User</a>
        </div>
    </div>
</div>
<center><br/><a class="q" href="<%=request.getContextPath()%>/logout" class="btn btn-success">Logout</a></center>
</body>
</html>