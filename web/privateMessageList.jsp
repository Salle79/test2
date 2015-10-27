    <%-- 
    Document   : listPrivateMessages
    Created on : 2008-nov-17, 08:08:33
    Author     : Salle
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:include page= "session.jsp" />
<jsp:useBean id="privateMessage" scope="session" class="logik.privateMessage" />
<jsp:setProperty name="privateMessage" property="*" />
<%@ page import= "java.util.ArrayList" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   <html><head> <link rel="stylesheet" type="text/css" href="css/style.css" /></head>
    <body>
<div class="container">
<div class="header"><h1 class="header">Forum för bättre vetande</h1></div>
<div class="subheader"><h4 class="header"><jsp:include page= "header.jsp" /></h4></div>
<div class="content">
        <center>
            
        <h1>Inkorg</h1>
        <a href='privateMessagePost.jsp'>Skriv nytt personligt meddelande</a><br>
        <br><br>
        <table border="1" cellpadding="10">
            <tr bgcolor="3163A0">
                <td><b>Ämne: </b></td>
                <td><b>Från: </b></td>
                <td><b>Mottaget: </b></td>
                </tr>
        <%
        //Sessionen som behövs för att användaren ska kunna behålla sin inloggning.
        //Sessionen hämtar ämnena för en specifik användare.
        ArrayList meddelanden = privateMessage.ListTopics("" + session.getAttribute("Username"));  
            
            for (int t = 0; t < meddelanden.size(); t++)
            {
                String[] ha = (String[])meddelanden.get(t);
                out.println("<tr bgcolor='DBDBDB'><td><a href='privateMessageView.jsp?ID=" + ha[0] + "'> " + ha[1] + "</a></td><td><a href =viewUser.jsp?UserID=" +ha[2] + ">" + ha[2] +"</a></td><td>" + ha[3] + "</td></tr>"); 
            }
        if (meddelanden.isEmpty() == true)
            {
            out.println("<tr bgcolor='DBDBDB'><td colspan = 3>Inkorgen är tom...</td></tr>");
        }
        %>
</table>  <br>
<a href='index.jsp'>tillbaka till startsidan</a>
</center>
</div>
<div class="footer"><jsp:include page= "usersOnline.jsp" /></div>
</div>
</body>
</html>
