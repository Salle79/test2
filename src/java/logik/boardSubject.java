
package logik;
import databas.*;
import java.sql.SQLException;
import java.util.*;
import java.text.*;
import java.lang.Object;

/** Alla variabler som behšvs.
 * 
 * @author Petrus
 */
public class boardSubject {
private int subjectID;
private String userName;
private String title;
private String body;
private int responses;
private String dateCreated;
private String latestPost;
private String latestUser;
private int latestPostID;
private int views;
private long LatestView;
private long NewestView;
private ArrayList Topics;
private ArrayList UnreadTopics;


/** Get och set fšr alla variabler.
 * 
 * @return
 */
    public int getSubjectID() { return this.subjectID;}
    public void setSubjectID(int subjectID) {this.subjectID = subjectID;}
    public String getUserName() {return this.userName;}
    public void setUserName(String userName) {this.userName = userName;}
    public String getTitle() {return this.title;}
    public void setTitle(String title) {this.title = title;}
    public String getBody() {return this.body;}
    public void setBody(String body) {this.body = body;}
    public String getDateCreated() {return this.dateCreated;}
    public void setDateCreated(String dateCreated) {this.dateCreated = dateCreated;}
    public int getResponses() {return this.responses;}
    public void setResponses(int responses) {this.responses = responses;}
    public String getLatestPost() {return latestPost;}
    public void setLatestPost(String latestPost) {this.latestPost = latestPost;}
    public String getLatestUser() {return latestUser;}
    public void setLatestUser(String latestUser) {this.latestUser = latestUser;}
    public int getViews() {return views;}
    public void setViews(int views) {this.views = views;}
    public int getLatestPostID() {return latestPostID;}
    public void setLatestPostID(int latestPostID) {this.latestPostID = latestPostID;}
    public long getLatestView() {return LatestView;}
    public void setLatestView(long getLatestView) { this.LatestView = getLatestView;}
    public long getNewestView() {return NewestView;}
    public void setNewestView(long NewestView) {this.NewestView = NewestView;}
    public ArrayList getTopics() {return Topics;}
    public void setTopics(ArrayList Topics) {this.Topics = Topics;}
    public ArrayList getUnreadTopics() { return UnreadTopics;}
    public void setUnreadTopics(ArrayList UnreadTopics) {this.UnreadTopics = UnreadTopics;}
    
    /** Postar ett nytt Šmne i forumet
     * 
     * @throws java.sql.SQLException
     */
    public void PostTopic () throws SQLException {
        Database db = new Database();
        db.insertBoardTopic(this);
    }
    
    /** Listar alla Šmnen i forumet
     * 
     * @throws java.sql.SQLException
     */
    public void ListTopics () throws SQLException {
       
        Database db = new Database();
        ArrayList mainArray = new ArrayList();
        ArrayList userList = new ArrayList();
        db.showBoardTopics(this);
        mainArray = this.getTopics();
        
        ArrayList unreadTopics = new ArrayList();
        for(int i=0; i < mainArray.size(); i++)
        { 
          /** LŠgger ihop allt i en gemensam array som dŠrmed innehŒller alla Šmnen och informationen om dessa.
           * 
           */
          String[] tempSQL = new String[12];
          boardSubject tempTopic = new boardSubject();
         
          tempTopic = (boardSubject)mainArray.get(i);
          tempSQL[0] = "" + tempTopic.getSubjectID();
          tempSQL[1] = tempTopic.getTitle();
          tempSQL[2] = tempTopic.getUserName();
          tempSQL[3] = tempTopic.getBody();
          tempSQL[4] = tempTopic.getDateCreated();
          tempSQL[5] = tempTopic.getLatestUser();
          tempSQL[6] = tempTopic.getLatestPost();
          tempSQL[7] = "" + (tempTopic.getResponses()-1);
          tempSQL[8] = "" + tempTopic.getViews();
          tempSQL[9] = "" + tempTopic.getLatestPostID();
          tempSQL[10]= ("" + tempTopic.getLatestView());
          tempSQL[11]= ("" + tempTopic.getNewestView()); 
           
          /** Specialare som skriver ut "ingen" dŠr det inte har sparats nŒgon 
           * anvŠndarnamn fšr den som har skrivit det senaste inlŠgget. Funktionen
           * med senaste inlŠggsskrivare fanns inte med frŒn bšrjan, dŠrav specialaren. 
           * 
           */
          if(tempSQL[5].compareTo("Ingen")!=0) {
                tempSQL[5] = "<a href =viewUser.jsp?UserID=" +tempSQL[5] + ">" + tempSQL[5] + "</a>";
          }  
          long UserTime = Long.parseLong(tempSQL[10]);
          long TimePosted = Long.parseLong( tempSQL[11]);
           
          if( UserTime < TimePosted){
                 unreadTopics.add(tempSQL[0]);
          }
          userList.add(tempSQL);
           
        } 
        this.setTopics(userList);
        this.setUnreadTopics(unreadTopics);
  }
    
    public String showText () throws SQLException{
        Database db = new Database();
        return db.findText(this);
    }
}
