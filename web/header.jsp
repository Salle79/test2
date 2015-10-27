<a href='messageBoard.jsp'>Startsida</a>
<a href='viewUser.jsp?UserID=<%=session.getAttribute("Username")%>'>Profil</a>
<a href='userList.jsp'>Medlemslista</a>
<a href='privateMessageList.jsp'><jsp:include page="privateMessageNew.jsp" flush="true"/></a>  
<a href='messageBoardSearch.jsp'>Sök</a> 
<a href='session.jsp?Kill=true'>Logga ut [ <%=session.getAttribute("Username")%> ]</a>