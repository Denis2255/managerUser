
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login</title>
  <style type="text/css">
    button.q {
      background: -moz-linear-gradient(#6bd600, #EBFFFF);
      background: -webkit-gradient(linear, 0 0, 0 100%, from(#0ed600), to(#EBFFFF));
      filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#00BBD6', endColorstr='#EBFFFF');
      padding: 3px 7px;
      color: #333;
      -moz-border-radius: 5px;

      -webkit-border-radius: 5px;
      border-radius: 5px;
      border: 1px solid #666;
    }
  </style>
</head>
<body>

<div class="form">

 <center><h1 class="text-center">Login page</h1>
   <hr>
   <br>
  <form method="post" action="">

    <input type="text" required placeholder="login" name="login"><br>
    <input type="password" required placeholder="password" name="password"><br>
    <p><button class="q">Login</button></p>
  </form>
 </center></div>
</body>
</html>
