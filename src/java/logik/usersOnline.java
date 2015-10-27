

package logik;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;
import databas.*;
/**
 *
 
 */
public class usersOnline {
    /** Alla variabler som behšvs
     * 
     */
private int TimeSince;
private String Name;
private int TotalUsers;
private int UserID;
private ArrayList UsersOnline;

/** Get och set fšr alla variabler
 * 
 * @return
 */
    public int getTimeSince() { return TimeSince;}
    public void setTimeSince(int TimeSince) {this.TimeSince = TimeSince;}
    public String getName() {return Name;}
    public void setName(String Name) {this.Name = Name;}
    public int getTotalUsers() {return TotalUsers;}
    public void setTotalUsers(int TotalUsers) {this.TotalUsers = TotalUsers;}
    public int getUserID() {return UserID;}
    public void setUserID(int UserID) {this.UserID = UserID;}
    public ArrayList getUsersOnline() {return UsersOnline;}
    public void setUsersOnline(ArrayList UsersOnline) {this.UsersOnline = UsersOnline;}
    
    /** Gšr en arraylist šver alla som Šr online.
     * 
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList UpdateOnlineStatus() throws SQLException { 
        
        ArrayList mainArray = new ArrayList();
        ArrayList userList = new ArrayList();
        Database db = new Database();
        mainArray = db.UpdateOnlineUsers(this);
        
        for(int t=0; t < mainArray.size(); t++)
        {
          String[] tempSQL = new String[5];
          usersOnline tempTopic = new usersOnline();
          tempTopic = (usersOnline)mainArray.get(t);
          tempSQL[0] = "" + tempTopic.getName();
          userList.add(tempSQL);
      }
        return userList;    
    }    
}
