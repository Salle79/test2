<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:include page= "session.jsp" />
<jsp:useBean id="boardSubject" scope="session" class="logik.boardSubject" />
<jsp:setProperty name="boardSubject" property="*" />
<%@ page import= "java.util.*, java.text.*" %>
<%
if(request.getParameter("Topic") != null) {
             boardSubject.setBody(request.getParameter("Body"));
             boardSubject.setTitle(request.getParameter("Topic"));
             boardSubject.setUserName("" + session.getAttribute("Username"));
             boardSubject.PostTopic();
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
        <center>
            <font face = "arial">
            <h1>Forum för bättre vetande</h1>
         <h2>Skriv nytt inlägg</h2>
     <form id="MessageBoard" name="MessageBoard" method="post">
        Titel: <br />
        <input type="text" name="Topic" value="" size="15" /> <br />
        Text: <br />
        <textarea name="Body" rows="5" cols="35"></textarea> <br />
            <input type="submit" value="Skicka" name="sendButton" />
       </form>
        <table border="1" cellpadding="10">
            <tr bgcolor="3163A0">
                <td><b>Ämne: </b></td>
                <td><b>Författare: </b></td>
                <td><b>Svar: </b></td>
                <td><b>Visningar: </b></td>
                <td><b>Senaste inlägg: </b></td>
                
                </tr>
                <%
             //Sessionen hämtar alla ämnen. 
             boardSubject.setUserName("" + session.getAttribute("Username"));
             boardSubject.ListTopics();
             ArrayList Topics = boardSubject.getTopics();
             ArrayList newTopics= new ArrayList();
             ArrayList unreadPost = new ArrayList();
             ArrayList te = new ArrayList();
             te= boardSubject.getUnreadTopics();
             unreadPost = (ArrayList)session.getAttribute("ReadPost");
                    for (int x = 0; x < te.size(); x++) { 
                        if(!unreadPost.contains(te.get(x))){
                        unreadPost.add(te.get(x));
                        }
                    }
                    session.setAttribute("ReadPost", unreadPost);
                    for (int i = 0; i < Topics.size(); i++){ 
                    String[] topicData = (String[])Topics.get(i);
                    newTopics = (ArrayList)session.getAttribute("ReadPost");
                    if(newTopics.contains(topicData[0])){
                           topicData[10]="*Nytt*"; 
                          
                    } 
                    else {
                        topicData[10]=""; 
                    }
                    out.println("<tr bgcolor='DBDBDB'><td>" + topicData[10] + " <a href='messageBoardReplies.jsp?ID=" + topicData[0] + "'> " + topicData[1] + "</a> </td><td><a href='viewUser.jsp?UserID=" +topicData[2] + "'>" + topicData[2] +"</a></td><td>" + topicData[7] + "</td><td>" +topicData[8]+ "</td><td>" + topicData[6] + "<br>" + topicData[5] + " <a href=messageBoardReplies.jsp?ID=" + topicData[0] +"#post" +topicData[9] +"> --></a></td></tr>" ); 
            }    
        }
         %>
     </table>
     </center>
    </div>
<div class="footer"><jsp:include page= "usersOnline.jsp" /></div>
</div>
    </body>
</html>
