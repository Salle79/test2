

package logik;
import databas.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class fulltextSearch {
    /** Alla variabler som behövs. 
     * 
     */
    private String Include;
    private String Exclude;
    private int Min;
    private int Max;
    private int Choise;
    private String SearchUser;
    private String Message;
    private String Title;
    private float Revelance;
    private String LatestDate;
    private String MessageSize;
            
    /** Get och set för alla variabler.
     * 
     * @return
     */
    public String getInclude() {return Include;}
    public void setInclude(String Include) {this.Include = Include;}
    public String getExclude() {return Exclude;}
    public void setExclude(String Exclude) {this.Exclude = Exclude;}
    public int getMin() {return Min; }
    public void setMin(int Min) {this.Min = Min;}
    public int getMax() {return Max;}
    public void setMax(int Max) { this.Max = Max;}
    public int getChoise() {return Choise;}
    public void setChoise(int Choise) { this.Choise = Choise;}
    public String getSearchUser() {return SearchUser; }
    public void setSearchUser(String SearchUser) { this.SearchUser = SearchUser;}
    public String getMessage() {return Message;}
    public void setMessage(String Message) {this.Message = Message;}
    public String getTitle() {return Title;}
    public void setTitle(String Title) {this.Title = Title;}
    public float getRevelance() {return Revelance;}
    public void setRevelance(float Revelance) {this.Revelance = Revelance;}
    public String getLatestDate() {return LatestDate;}
    public void setLatestDate(String LatestDate) {this.LatestDate = LatestDate;}
    public String getMessageSize() { return MessageSize;}
    public void setMessageSize(String MessageSize) {this.MessageSize = MessageSize;}
    
     /** Skapar en ny array där sökresultaten kan sparas i de fall det är 
      * en fulltextsök med relevans. 
      * 
      * @return
      * @throws java.sql.SQLException
      */
    public ArrayList FullTextSearchBody() throws SQLException{
        Search db = new Search();
        ArrayList mainArray = new ArrayList();
        ArrayList newMessage = new ArrayList();
        mainArray = db.FullTextSearchBody(this);
      
        for(int t=0; t < mainArray.size(); t++)
        {
         
          String[] tempSQL = new String[5];
          fulltextSearch tempSearch = new fulltextSearch();
          tempSearch = (fulltextSearch)mainArray.get(t);
          tempSQL[0] = "" + tempSearch.getMessage();
          tempSQL[1] ="" + tempSearch.getLatestDate();
          tempSQL[2] = tempSearch.getSearchUser();
          tempSQL[3] = "" + tempSearch.getRevelance();
          tempSQL[4] = tempSearch.getTitle();
          newMessage.add(tempSQL);
      }
        return newMessage;
    }
           /** Skapar en ny array där sökreslutaten kan sparas i de fall
            * där det är en booleansök (utan relevans).
            * 
            * @return
            * @throws java.sql.SQLException
            */
    public ArrayList BooleanSearchBody() throws SQLException{
        Search db = new Search();
        ArrayList mainArray = new ArrayList();
        ArrayList newMessage = new ArrayList();
        mainArray = db.BooleanSearch(this);
      
        for(int t=0; t < mainArray.size(); t++)
        {

          String[] tempSQL = new String[5];
          fulltextSearch tempSearch = new fulltextSearch();
          tempSearch = (fulltextSearch)mainArray.get(t);
          tempSQL[0] = "" + tempSearch.getMessage();
          tempSQL[1] ="" + tempSearch.getLatestDate();
          tempSQL[2] = tempSearch.getSearchUser();
          tempSQL[3] = "";
          tempSQL[4] = tempSearch.getTitle();
          newMessage.add(tempSQL);
      }
        return newMessage;   
    }
    public void SearchUser () {
        
    }
}
