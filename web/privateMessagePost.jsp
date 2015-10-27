<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="privateMessage" scope="session" class="logik.privateMessage" />
<jsp:setProperty name="privateMessage" property="*" />
<jsp:useBean id="user" scope="session" class="logik.user" />
<jsp:setProperty name="user" property="*" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html><head> <link rel="stylesheet" type="text/css" href="css/style.css" /></head>
<body>
<div class="container">
<div class="header"><h1 class="header">Forum för bättre vetande</h1></div>
<div class="subheader"><h4 class="header"><jsp:include page= "header.jsp" /></h4></div>
<div class="content">
        <center>
    <form id="PrivateMessage" name="LogInUser" method="post">
        <H1>Nytt personligt meddelande </H1><br>
        <h3>Till användare: </h3><input type="text" name="toUserText" value="" size="20" /><br>
   
    <h3>Ämne: </h3><input type="text" name="subjectText" size="20" /><br>
    <h3>Meddelande:</h3><textarea name="bodyText" rows="8" cols="50"></textarea><br>
<input type="submit" value="Skicka" name="sendButton" />
</form>
<% 

      if(request.getParameter("toUserText") != null) {
               //Läser in användarnamnet på mottagande person. 
               //Sessionen som användare loggade in med skall stå här.
               privateMessage.setFromUser("" + session.getAttribute("Username"));
               privateMessage.setToUser(request.getParameter("toUserText")); 
               privateMessage.setTitle(request.getParameter("subjectText")); 
               privateMessage.setBody(request.getParameter("bodyText")); 
               user.setUserName(request.getParameter("toUserText"));
               //Kontrollerar otillåtna tecken för läsbara/handlingsbara användarnamn/lösenord.
               if(request.getParameter("toUserText").matches("[a-öA-Ö0-9_]{3,20}")
                    && request.getParameter("subjectText").matches("[a-öA-Ö0-9_]{3,30}"))          
               {   
                       
                   if (privateMessage.SendMessage(user))//Kollar om mottagaren finns. 
                    {
                       out.println("Meddelandet är skickat...."); 
                    }
                    else{
                        out.println("skickar inte, användare kunde inte hittas.");
                    } 
               }
               
               else {
                    //skriver ut felmeddelande om otillåtns tecken hittas
                   out.println("Otilåtna tecken.");    
               
               }
     }
%>
<a href='privateMessageList.jsp'>tillbaka till inkorgen</a>
</center>
   </div>
<div class="footer"><jsp:include page= "usersOnline.jsp" /></div>
</div>

</body>
</html>
