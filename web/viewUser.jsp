<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="Search" scope="session" class="logik.search" />
<jsp:setProperty name="Search" property="*" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   <html><head> <link rel="stylesheet" type="text/css" href="css/style.css" /></head>
<body>
<div class="container">
<div class="header"><h1 class="header">Forum för bättre vetande</h1></div>
<div class="subheader"><h4 class="header"><jsp:include page= "header.jsp" /></h4></div>
<div class="content">
    <table border="1" cellpadding="10" align="center" bgcolor='DBDBDB'>
<%
   if(request.getParameter("UserID")!= null) {
   Search.setUser(request.getParameter("UserID"));    
   Search.findUser();
       %>
       <tr><td>Användarnamn: </td><td><%=Search.getUser()%></tr>
       <tr><td>Fullständigt namn: </td><td><%=Search.getName()%></td></tr>
       <tr><td>Adress: </td><td><%= Search.getAdress()%></td></tr><br />
       <tr><td>E-post </td><td><%=Search.getEmail() %></td></tr><br />
       <tr><td>Senast inloggad: </td><td><%=Search.getLatestView()%></td></tr>
       <tr><td>Avatar: </td><td><%=Search.getPicture() %></td></tr>
       </table>
       <%
}
else {
       out.println("Nu har du nog kommit fel");
}
%>
    
   </div>
<div class="footer"><jsp:include page= "usersOnline.jsp" /></div>
</div>

</body>
</html>
