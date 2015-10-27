<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html"%>
<%@page pageEncoding="ISO-8859-1"%>
<%@ page import= "java.util.*, java.text.*" %>
<jsp:useBean id="login" scope="session" class="logik.user" />
<jsp:setProperty name="login" property="*" />

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="stylesheet" type="text/css" href="css/login.css" />
    <title>Inloggning</title>
</head>
<body>
<center>    
<div class="container">
<div class="header"><h1 class="header">
Inloggning
</h1></div>
<div class="text">
    <p><b>Användarnamn:</b></p>
</div>
<form id="LogInUser" name="LogInUser" method="post">
<div class="field">
    <p><input type ="text" name="UserName"></p>
    <div class="text">
        <p><b>Lösenord:</b></p>
    </div>
    <p><input type ="password" name="Password"></p>
    <p><input type ="submit" name="submit" value="Logga in"></p>
    <p>
    <% 
      if(request.getParameter("UserName") != null) {
               
               login.setUserName(request.getParameter("UserName"));
               login.setPassword(request.getParameter("Password"));
              
               if(request.getParameter("UserName").matches("[a-öA-Ö0-9_]{3,20}")
               && request.getParameter("Password").matches("[a-zA-Z0-9_]{3,20}")) {
                    
                        if(login.logIn()){
                            ArrayList Read = new ArrayList();
                            session.setMaxInactiveInterval(-1);
                            session.setAttribute("Email", "" + login.getEmail()); 
                            session.setAttribute("Password", "" + login.getPassword());
                            session.setAttribute("Username", "" + login.getUserName());
                            session.setAttribute("Address", "" + login.getAdress());
                            session.setAttribute("Name", "" + login.getFullName());
                            session.setAttribute("ReadPost", Read);
                            response.sendRedirect("messageBoard.jsp");
                            
                            }
                       
                        else{
                                out.println("<span class=\"red\">Fel inloggningsuppgifter</span>");
                        }
               }
               
               else {
               out.println("<span class=\"red\">Otilåtna tecken.</span>");    
               
               }
          }
           
      	   	   
    %>
    </p>
</div>
</form>
</div>
</center>
</body>
</html>

