
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="replies" scope="session" class="logik.boardReply" />
<jsp:setProperty name="replies" property="*" />
<jsp:useBean id="topic" scope="session" class="logik.boardSubject" />
<jsp:setProperty name="topic" property="*" />
<jsp:useBean id="user" scope="session" class="logik.user" />
<jsp:setProperty name="user" property="*" />
<%@ page import= "java.util.ArrayList" %>

<%
if(request.getParameter("Body") != null) {
             
             //Anmärkning ID är ID't i Url'en som används för att veta vilken
             //topic som användaren valt att läsa.
             replies.setSubjectID(Integer.parseInt(request.getParameter("ID")));
             replies.setBody(request.getParameter("Body"));
             replies.setUserName("" + session.getAttribute("Username"));
             replies.PostReply();
             response.sendRedirect("messageBoard.jsp");  
         }
        else {
                   %>
               <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   <html><head> <link rel="stylesheet" type="text/css" href="css/style.css" /></head>
    <body>
<div class="container">
<div class="header"><h1 class="header">Forum för bättre vetande</h1></div>
<div class="subheader"><h4 class="header"><jsp:include page= "header.jsp" /></h4></div>
<div class="content">
        

               <%
               
               if(request.getParameter("ID") == null) {
                        %> Ett fel uppstod, var god och återvänd till sidan du var på innan. <%
                    }
                  else {
                        user.addViewTime("" + session.getAttribute("Username"));
                        replies.increaseView(Integer.parseInt(request.getParameter("ID")));
                        topic.setSubjectID(Integer.parseInt(request.getParameter("ID")));
                        replies.setSubjectID(Integer.parseInt(request.getParameter("ID")));
                        %>
                        <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <% out.println("<title>" + topic.showText() + "</title>");%>
    </head>
    <body>
        <center>
            <font face = "arial">
                <br>
        <p style="text-align: center;">
            
               <% out.println("<h1>" + topic.showText() + "</h1> <br /><br /> ");%>
        <table border="1" cellpadding="10" align="center">
            <tr bgcolor="3163A0">
                <td><b>Från: </b></td>
                <td><b>Mottaget: </b></td>
                </tr>
                        <%
                        ArrayList svar =  replies.ListReplies(); //Sessionen hämtar ämnena för en specifik användare. 
                        ArrayList newTopics = (ArrayList)session.getAttribute("ReadPost"); 
                       if(newTopics.contains(request.getParameter("ID"))) {
                          newTopics.remove(request.getParameter("ID"));
                       }
                        
                        if (svar.isEmpty())
                        {
                            out.println("<tr bgcolor='DBDBDB' cellpadding='0' ><td colspan = 2>Det finns inga svar på detta inlägg.");
                        }
                            int inlagg=0;
                        for (int i = 0; i < svar.size(); i++) 
                         {
                            inlagg++;
                            String[] topicData = (String[])svar.get(i);
                            out.println("<tr bgcolor='3163A0' cellpadding='0'><td>" + "#" +inlagg +" " + topicData[2] +  "</td><td><a name='post" + topicData[1] +"'>" + topicData[4] + "</a></tr><tr><td colspan = 2 bgcolor='DBDBDB' ></b>"+ topicData[3] +" </td></tr></DIV>");
                            // + topicData[0] + "<br> " 
                         }
                  }
         %>
     </table>   <br>
     <form id="MessageBoardReplies" name="MessageBoardReplies" method="post">
                  Skriv svar: <br />
                  <textarea name="Body" rows="5" cols="35"></textarea> <br />
                   <input type="submit" value="Skicka" name="sendButton" />
      </form>   
      <br> <a href="messageBoard.jsp">Tillbaka</a> 
  </center>
 </div>
<div class="footer"><jsp:include page= "usersOnline.jsp" /></div>
</div>
</body>
</html>
<%}%>
