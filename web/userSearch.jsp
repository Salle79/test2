<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="search" scope="session" class="logik.search" />
<jsp:setProperty name="search" property="*" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; ISO-8859-1">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
        if(request.getParameter("Username")!=null) {
            search.setUser(request.getParameter("Username"));
            search.findUser();
           
            %>
            <%=search.getUser()%> var senast online:<%=search.getLatestView()%>, Adress:<%=search.getAdress()%>, Fullnamn:<%=search.getName()%>, Email: <%=search.getEmail() %>
            <%
        }
        else {
            %>
    <h1>JSP Page</h1>
    
    <form name="tst" method="POST" action="userSearch.jsp">
       Sök efter Användarnamn: <input type="text" name="Username" value="" size="10" /><br />
       
        <input type="submit" value="Sök" name="Search" />
    </form>
            <%
     }
    %>
    
    </body>
</html>
