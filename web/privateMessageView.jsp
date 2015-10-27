<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="privateMessage" scope="session" class="logik.privateMessage" />
<jsp:setProperty name="privateMessage" property="*" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html><head> <link rel="stylesheet" type="text/css" href="css/style.css" /></head>
<body>
<div class="container">
<div class="header"><h1 class="header">Forum för bättre vetande</h1></div>
<div class="subheader"><h4 class="header"><jsp:include page= "header.jsp" /></h4></div>
<div class="content">
    <center>
        <%
        // Kontrollerar om meddelandet har ett ID. 
        if(request.getParameter("ID") != null) {
            // Bygger ihop meddelandet. 
            privateMessage.setID(Integer.parseInt(request.getParameter("ID")));   
            privateMessage.ReadMessage();
            %>
            <% out.println("<H1>"+ privateMessage.getTitle() + "</H1>");%>
          <table border="1" cellpadding="10">
            <tr bgcolor="3163A0">
                <td><b>Från: </b></td>
                <td><b>Datum: </b></td>

                </tr>
            <% 
            // Skriver ut hela meddelandet och detaljerna i det. 
            out.println("<tr bgcolor='DBDBDB'><td>" + privateMessage.getFromUser()+ "</td><td>" + privateMessage.getDatum() + "</td><tr bgcolor='DBDBDB'><td colspan = 2> <Center>" + privateMessage.getBody() + "</Center></td></tr>");

        }
        
     else {
                    //skriver ut felmeddelande om otillåtns tecken hittas
                   out.println("Nu har du hamnat fel. Ange ett giltigt meddelande-ID!");   
     }
        
    %>
        
</div>
</table>
<br>
<div class="footer"><jsp:include page= "usersOnline.jsp" /></div>
</div>
</center>
</body>
</html>
