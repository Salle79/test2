<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<%@ page import= "java.util.ArrayList" %>
<jsp:useBean id="usersOnline" scope="session" class="logik.usersOnline" />
<jsp:setProperty name="usersOnline" property="*" />
Användare som är online just nu:
<%
ArrayList OnlineUsers = usersOnline.UpdateOnlineStatus();
        for (int t=0; t < OnlineUsers.size(); t++){
            String[] topicData = (String[])OnlineUsers.get(t);
            out.print(" | <a href=viewUser.jsp?UserID=" + topicData[0] +">" + topicData[0] + "</a> ");
        }
%>| Totalt har det varit <%=OnlineUsers.size()%>  användare online de senaste 30 minuterna.   
    </body>
</html>
