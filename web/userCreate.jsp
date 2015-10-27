<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="user" scope="session" class="logik.user" />
<jsp:setProperty name="user" property="*" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <script type="text/javascript" src="scripts/AJAX.js"></script>  
        <title>Välkommen</title>
    </head>
<body>
<center>
<form name="myForm" id="myForm"  method="post">
<p><b>Användarnamn:</b></p>
    <p><input type ="text" onblur="ajaxFunction();" onfocus="res();" name="UserName" id="UserName"><span id="txtHint"></span></p>
    <p><b>Lösenord:</b></p>
    <p><input type ="password" id="Password" name="Password"> </p>
    <p><b>Namn:</b></p>
    <p><input type ="text" id="Name" name="Name"></p>
    <p><b>Email:</b></p>
    <p><input type ="text" id="Email" name="Email"></p>
    <p><b>Ort:</b></p>
    <p><input type ="text" id="Address" name="Address"></p>
    <p><input type ="submit" id="submit" value="Skapa ny användare"></p>
</form>
<%         
      if(request.getParameter("UserName") != null) {
      
               //Läser in användarnamn och lösenord för att skapa konto. 
               user.setUserName(request.getParameter("UserName"));
               user.setPassword(request.getParameter("Password"));
               user.setFullName(request.getParameter("Name"));
               user.setEmail(request.getParameter("Email"));
               user.setAdress(request.getParameter("Address"));
               //Kontrollerar otillåtna tecken för läsbara/handlingsbara användarnamn/lösenord.
               if(request.getParameter("UserName").matches("[a-öA-Ö0-9_]{3,20}")
                    && request.getParameter("Password").matches("[a-öA-Ö0-9_]{3,20}")) {   
                    if (user.addUser())//skapar kontot i databasen
                    {
                        out.println("Ny användare " + user.getUserName() +
                        " har lagts till <br>");
                    }
                    else{
                        out.println("Användare " + user.getUserName() + 
                        " existerar redan, välj ett nytt användarnamn");
                    }
                    
               }
               
               else {
                    //skriver ut felmeddelande om otillåtns tecken hittas
                   out.println("Otilåtna tecken.");    
               
               }
     }
      	   	   
    %>
    <a href='userLogin.jsp'>till inloggningen</a>
</center>
</body>
</html>


