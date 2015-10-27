<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="message" scope="session" class="logik.privateMessage" />
<jsp:setProperty name="message" property="*" />
    <% 
    // Tar emot hur många nya meddelanden användaren har. 
    int meddelande = message.NewMessages("" + session.getAttribute("Username"));
   
    //Skriver ut hur många nya meddelanden användaren har. 
    if (meddelande >0) {
        out.print("Du har " + meddelande + " nya meddelanden"); 
    }
    // Om det är en inpopulär användare står det bara "inkorg". 
    else {
        out.print("Inkorg");
    }
    %>