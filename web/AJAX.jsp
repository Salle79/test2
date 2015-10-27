<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="ajax" scope="session" class="logik.AJAX" />
<jsp:setProperty name="ajax" property="*" />
<html>
    

<% 
if (request.getParameter("ID")!=null) {
  
    if(ajax.checkUser(request.getParameter("ID"))) {
        out.println("Användare med namnet " + request.getParameter("ID") + "  Finns redan, välj ett annat användarnamn.");   
    }
    else {
       out.println(""); 
    }
}
else {
    %> nu har du kommit fel <%
}

 %>
</html>