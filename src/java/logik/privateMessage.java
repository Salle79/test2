
package logik;

import java.sql.*;
import databas.*;
import java.util.ArrayList;

/** Alla variabler som behövs
 */
public class privateMessage {
    private int ID;
    private String toUser;
    private String fromUser;
    private String title;
    private String body;
    private String Datum;
    private boolean Read;

    /** Get och set för variablerna
     * 
     * @return
     */
    public int getID () {return this.ID;}
    public void setID (int ID) {this.ID = ID;}
    public boolean getRead () {return this.Read;}
    public void setRead (boolean Read) {this.Read = Read;}
    public String getDatum() {return this.Datum;}
    public void setDatum(String Datum) {this.Datum = Datum;}
    public String getBody () {return this.body;}
    public void setBody (String Body) {this.body = Body;}
    public String getFromUser () {return this.fromUser;}
    public void setFromUser (String User) {this.fromUser = User;}
    public String getTitle () {return this.title;}
    public void setTitle (String Title) {this.title = Title;}
    public String getToUser () {return this.toUser;}
    public void setToUser (String User) {this.toUser = User;}
    
    /** Rutin för att skicka privatmeddelande.
     * 
     * @param user
     * @return
     * @throws java.sql.SQLException
     */
    public boolean SendMessage (user user) throws SQLException {
       boolean userExists = false;
        Database db = new Database();
        /** Kontrollerar om användaren existerar.
         * 
         */
        if(db.findUserByUsername(user)) {
            userExists = true;   
            db.insertPrivateMessage(this);
        }
        return userExists;
    }
    
    /** Hämtar ett meddelande och markerar det som läst. 
     * 
     * @throws java.sql.SQLException
     */
    public void ReadMessage () throws SQLException {
        /** Skapar en ny databas
         * 
         */
        Database db = new Database();
        /** Returnerar det som finns i meddelandet med aktuellt ID.
         * 
         */
        db.ViewPrivateMessage(this);
        if(this.getRead()!=true) {
            db.RemoveFlag(this); 
        }    
    }
    
    /** Visar alla ämnen på privatmeddelanden. 
     * 
     * @param Username
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList ListTopics (String Username) throws SQLException {
        Database db = new Database();
        ArrayList mainArray = new ArrayList();
          ArrayList userList = new ArrayList();
        mainArray = db.listAllPMTopics(Username);
      
        for(int t=0; t < mainArray.size(); t++)
        {
          /** Lägger ihop arrayerna i en gemensam array som därmed innehåller 
           * hela listan av privatmeddelanden.
           * 
           */
          String[] tempSQL = new String[4];
          privateMessage tempMessage = new privateMessage();
          tempMessage = (privateMessage)mainArray.get(t);
          tempSQL[0] = "" + tempMessage.getID();
          tempSQL[1] = tempMessage.getTitle();
          tempSQL[2] = tempMessage.getFromUser();
          tempSQL[3] = tempMessage.getDatum();
          
          userList.add(tempSQL);
      }
        return userList;  
    } 
    
    /** Returnerar hur många nya meddelanden en användare har. 
     * 
     * @param Username
     * @return
     * @throws java.sql.SQLException
     */
    public int NewMessages (String Username) throws SQLException {
        
        Database db = new Database();
        return db.newMesssages(Username);
    }

    
}
