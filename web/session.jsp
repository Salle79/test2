 <%     
      if (session.getAttribute("Username") == null) {
        
            %> <jsp:forward page="error.jsp" /><%
        }
     if (request.getParameter("Kill") != null) {
            session.invalidate();
            response.sendRedirect("index.jsp");
        }   
     
%>      
    
