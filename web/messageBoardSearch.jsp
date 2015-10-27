<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:include page= "session.jsp" />
<jsp:useBean id="fullSearch" scope="session" class="logik.fulltextSearch" />
<jsp:setProperty name="fullSearch" property="*" />
<%@ page import= "java.util.ArrayList" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
   <html><head> <link rel="stylesheet" type="text/css" href="css/style.css" /></head>
    <body>
        <center>
        <div class="container">
            <div class="header"><h1 class="header">Forum för bättre vetande</h1></div>
            <div class="subheader"><h4 class="header"><jsp:include page= "header.jsp" /></h4></div>
            <div class="content"><h1>Sök</h1>
            <%
            if(request.getParameter("include")!= null) {
                fullSearch.setMax(Integer.parseInt(request.getParameter("max")));
                fullSearch.setMin(Integer.parseInt(request.getParameter("min")));
                fullSearch.setInclude(request.getParameter("include"));
                fullSearch.setSearchUser(request.getParameter("User"));
                fullSearch.setChoise(Integer.parseInt( "" + request.getParameter("searchChoise")));
                ArrayList result = new ArrayList();
            if(request.getParameter("exclude")!="") {
                result = fullSearch.BooleanSearchBody();  
            }
            else {
                    result = fullSearch.FullTextSearchBody();
            }
            for (int i = 0; i < result.size(); i++) 
            {
                String[] searchData = (String[])result.get(i);
                out.print(" titel: " + searchData[4] + "<br>");
                out.print("meddelande: " + searchData[0]+ "<br>");
                out.print(" datum: " + searchData[1]+ "<br>");
                out.print(" av användare: " + searchData[2]+ "<br>");
            if( searchData[3]!="") { out.print(" relevans: "  + searchData[3] + " %");}
        
                out.println("<br /><br /><br />");
    }
%>
            </div>
            <div class="footer"><jsp:include page= "usersOnline.jsp" /></div>
            </div>
            </center>
    </body>
</html>
<%
}
else {    

%>
    
<form name="search" action="messageBoardSearch.jsp" method="POST">
    Sök efter dessa ord:
    <input type="text" name="include" value="" size="10" />  
    
    av användare: <input type="text" name="User" value="" size="10" /> <BR /><br />
    
    exkludera följande ord i sökningen: <input type="text" name="exclude" value="" size="10" /><br /><br /> 
    Visa enbart poster som är mellan <input type="text" name="max" value="0" size="10" /> 
    och <input type="text" name="min" value="999" size="10" /> dagar gamla.<BR /><br />
    <select name="searchChoise" size="1">
        <option value="1">Sotera efter revelans</option>
        <option value ="2">Sortera efter senast uppdaterad-datum</option>
        <option value ="3">Sortera efter ämne</option>
    </select>
    <BR /><br />
    <input type="submit" value="Sök" name="Submit" />
    </form>
    </div>
    <div class="footer"><jsp:include page= "usersOnline.jsp" /></div>
    </div>
    </body>
</html>
<%}%>
