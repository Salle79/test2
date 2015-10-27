package logik;
import databas.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class boardReply {
/** Alla variabler som behšvs. 
 * 
 */
    private int replyID;
    private int subjectID;
    private String userName;
    private String body;
    private String dateCreated;
    
/** Get och set fšr alla variabler.
 * 
 * @return
 */
    public int getReplyID() {return this.replyID;}
    public void setReplyID(int replyID) {this.replyID = replyID;}
    public int getSubjectID() {return this.subjectID;}
    public void setSubjectID(int subjectID) {this.subjectID = subjectID;}
    public String getUserName() {return this.userName;}
    public void setUserName(String userName) {this.userName = userName;}
    public String getBody() {return this.body;}
    public void setBody(String body) {this.body = body;}
    public String getDateCreated() {return this.dateCreated;}
    public void setDateCreated(String dateCreated) {this.dateCreated = dateCreated;}
   
    /** LŠgger in ett forumsvar i databasen. 
     * 
     * @throws java.sql.SQLException
     */
    public void PostReply () throws SQLException {
        Database db = new Database();
        db.insertBoardReply(this);
    }
    
    /** Gšr en arraylista av alla svar som har skrivits till 
     * 
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList ListReplies () throws SQLException {
        Database db = new Database();
        ArrayList mainArray = new ArrayList();
        ArrayList userList = new ArrayList();
        mainArray = db.showBoardReplies(this);
      
        for(int t=0; t < mainArray.size(); t++)
        {
          /** LŠgger ihop arrayerna i en gemensam array som dŠrmed innehŒller hela listan av privatmeddelanden
           * 
           */
          String[] tempSQL = new String[5];
          boardReply tempTopic = new boardReply();
          tempTopic = (boardReply)mainArray.get(t);
          tempSQL[0] = "" + tempTopic.getSubjectID();
          tempSQL[1] ="" + tempTopic.getReplyID();
          tempSQL[2] = tempTopic.getUserName();
          tempSQL[3] = tempTopic.getBody();
          tempSQL[4] = tempTopic.getDateCreated();
          userList.add(tempSQL);
      }
        return userList;
    }

    public void increaseView (int ID) throws SQLException {
        Database db = new Database();
        db.increaseView(ID);
    }

}
