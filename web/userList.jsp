<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:include page= "session.jsp" />
<jsp:useBean id="listUsers" scope="session" class="logik.user" />
<jsp:setProperty name="listUsers" property="*" />
<%@ page import= "java.util.ArrayList" %>
<html><head> <link rel="stylesheet" type="text/css" href="css/style.css" /></head>
    <body>
<div class="container">
<div class="header"><h1 class="header">Forum för bättre vetande</h1></div>
<div class="subheader"><h4 class="header"><jsp:include page= "header.jsp" /></h4></div>
<div class="content">

        <center>
             
            <table border="1" cellpadding="10">
            <tr bgcolor="3163A0">
            <td>Användare:<td>Fullständigt namn:</td></tr>
        <%
            ArrayList lista = listUsers.listAllUsers();
            int inrease =1;
            for (int t = 0; t < lista.size(); t++)
            {
                String[] ha = (String[])lista.get(t);
                out.println("<tr bgcolor='DBDBDB'><td>" + inrease++ +"# <a href =viewUser.jsp?UserID=" +ha[0] + ">" + ha[0] +"</a></td>");
                out.println("<td>" + ha[1] + "</td></tr>");
            }
            %>
            
        </table>
        <a href='index.jsp'>tillbaka</a>
        </center>
   </div>
<div class="footer"><jsp:include page= "usersOnline.jsp" /></div>
</div>
</html>
