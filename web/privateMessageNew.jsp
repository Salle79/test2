<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="message" scope="session" class="logik.privateMessage" />
<jsp:setProperty name="message" property="*" />
    <% 
    // Tar emot hur m�nga nya meddelanden anv�ndaren har. 
    int meddelande = message.NewMessages("" + session.getAttribute("Username"));
   
    //Skriver ut hur m�nga nya meddelanden anv�ndaren har. 
    if (meddelande >0) {
        out.print("Du har " + meddelande + " nya meddelanden"); 
    }
    // Om det �r en inpopul�r anv�ndare st�r det bara "inkorg". 
    else {
        out.print("Inkorg");
    }
    %>