/*
 * Database.java
 *
 */
package databas;

import java.sql.*;
import java.util.ArrayList;
import logik.*;
/**
 * All kontakt mellan MySQL och forumet behandlas i denna klass.
 *Klasserna i logik paketet är de  enda klasser som får skicka informatin till databasklasserna
 * @param instanser Instansobjekt ifrån de logiska klasserna är det argument som krävs i
 *flesta metoder
 *@return Returnerar information i form av strängar i de metoder som har denna funktion. 
 */
public class Database {
    
     /** Variabel för JDBC connectorn till MySQL*/
    final static String DBCONNSTR = "jdbc:mysql://localhost/andsa121";
    /** Användarnamnet för MySQL*/
    final static String DBUSER = "root";
    /** Lösenordet för MySQL*/
    final static String DBPASSWD = "spunk";
    /** Initierar ett nytt Connection objekt*/
 
    Connection conn;
    
    /**
     * Kontruktor Initierar kontakt med MySQL varje gång en ny instans körs
     */
    public Database() {
        this.connect();
    }
    
    /** Ansluter till databasen
     * 
     */
    public void connect() {
        conn = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(DBCONNSTR, DBUSER, DBPASSWD);
        }
        catch(Exception e) {
            e.getMessage();
        }
    }
    
    /** Stänger ner kopplingen till databasen
     * 
     */
    public void close() {
        try {
            if(conn != null) {
                conn.close();
            }
        }
        catch(Exception e) {
            e.getMessage();
        }
    }
     
    /**
      *Sparar en ny användare i databasen
      *@param user Instans av user objekt från user.java
      *@throws SQLException
      */
    public void insertUser(user user) throws SQLException {
      
        /** setString lägger in användarens uppgifter i databasen. 
         * ? -tecknen innehåller de värden som användaren har knappat in i 
         * webbformuläret. 
         * MD5-funktionen krypterar lösenordet så att det inte sparas i klartext.
         * 
         */
        
        String sqlStr = "INSERT INTO User (UserName, Name, Email, Address, Password)" +
                        " VALUES (?,?,?,?,(MD5(?)))"; 
        PreparedStatement s = conn.prepareStatement(sqlStr);
        s.setString(1, user.getUserName());
        s.setString(2, user.getFullName());
        s.setString(3, user.getEmail());
        s.setString(4, user.getAdress());
        s.setString(5, user.getPassword());
        s.executeUpdate();
        s.close();
    }
    
    /**
     * Kontrollerar att användaren finns när någon försöker logga in. 
     * 
      *@param user Instans av user-objekt från user.java
      *@return returnerar booolean newUser till user.java.
      *@throws SQLException
      */
    public boolean findUserByPassword (user user) throws SQLException {
        
     boolean newUser = true;
     String sqlStr = "Select * FROM User WHERE" +
                     " UserName = ? AND Password= MD5(?) ";
     PreparedStatement s = conn.prepareStatement(sqlStr);
     s.setString(1, user.getUserName());
     s.setString(2, user.getPassword());
     s.execute();
     ResultSet rs = s.getResultSet();
      
     if (rs.next()){
         user.setAdress(rs.getString("Address"));
         user.setEmail(rs.getString("Email"));
         user.setFullName(rs.getString("Name"));
      }
      else {newUser = false;}  
      s.close();
      return newUser;
    }
    
    /**
      *Kontrollerar att användaren finns när någon försöker logga in. (när användaren
     * trycker på "skapa ny användare") 
      *@param user Instans av user-objekt från user.java
      *@return returnerar booolean newUser till user.java.
      *@throws SQLException
      */
    public boolean findUserByUsername (user user) throws SQLException {
      boolean UserNotFound = false;
      
      //Statement s = conn.createStatement(); 
       String sqlStr = "Select * FROM User" +
                      " WHERE UserName = ?";
       
      PreparedStatement s = conn.prepareStatement(sqlStr);
      s.setString(1,user.getUserName());
      s.execute();
      ResultSet rs = s.getResultSet();
      
      if (rs.next()){
          UserNotFound = true;
      }
      s.close();
      return UserNotFound;
    }
    /** Liknande metod som den förra, men den här tar bara in en string med
     * användarnamnet. Används exempelvis vid automatkontrollen av användarnamn
     * när någon vill skapa en ny användare. (inte när användaren trycker på knappen
     * utan när användaren har skrivit in ett användarnamn. 
     * @param user
     * @return
     * @throws java.sql.SQLException
     */
    public boolean findUserByUsername (String user) throws SQLException {
      boolean userFound = false;
      //Statement s = conn.createStatement(); 
       String sqlStr = "Select * FROM User" +
                      " WHERE UserName = ?";
      PreparedStatement s = conn.prepareStatement(sqlStr);
      s.setString(1, user);
      s.execute();
      ResultSet rs = s.getResultSet();
      if (rs.next()){  
          userFound = true;
      }
      s.close();
      return userFound;
    }

    /**
      *Gör en arrayList av alla användarnamn som finns på forumet samt 
     * deras riktiga namn. 
      *@param user Instans utav user objekt ifrÂn user.java
      *@return returnerar booolean newUser till user.java.
      *@throws SQLException
      */
    public ArrayList listAllUsers () throws SQLException {
        
        ArrayList newUser = new ArrayList();
        
        String sqlStr = "Select * FROM User ORDER BY UserName";
        PreparedStatement s = conn.prepareStatement(sqlStr); 
        s.execute();
        ResultSet rs = s.getResultSet();
      
        while(rs.next()){
          user User = new user();
          User.setUserName(rs.getString("UserName"));
          User.setFullName(rs.getString("Name"));
          newUser.add(User);
        }
        return newUser;
    }

    /**
      *Listar alla privatmeddelanden för en viss användare. 
      *@param user Instans utav user objekt ifrÂn user.java
      *@throws SQLException
      */    
     public ArrayList listAllPMTopics(String userName) throws SQLException {
        
        ArrayList newPM = new ArrayList();
        
        /** Plockar ur alla privatmeddelanden ur tabellen där användarnamet stämmer överens med användaren.
         * 
         */
        String sqlStr = "Select Title, MessID, FromUserName, Date_Format(`Date`,'%Y-%m-%d %H:%i') AS VisualDate FROM PrivateMessage " +
                        "WHERE `ToUserName`= ? ORDER BY `Date` DESC";
        /** Kör strängen.
         * 
         */
        PreparedStatement s = conn.prepareStatement(sqlStr); 
        s.setString(1, userName);
        s.execute();
        
        ResultSet rs = s.getResultSet();
      
        /** Skapar en lista av resultatet rs. Listan innehåller titel och meddelandeID
         * 
         */
        while(rs.next()){
          privateMessage message = new privateMessage();
          message.setTitle(rs.getString("Title"));
          message.setID(rs.getInt("MessID"));
          message.setFromUser(rs.getString("FromUserName"));
          message.setDatum("" + rs.getString("VisualDate"));
          
          newPM.add(message);
        }
        /** Returnerar listan tillbaka till privateMessage.java
         * 
         */
        return newPM;
    }

    public void editUser (user user) throws SQLException {
    }

    /**
      *Raderar en existerande användare från databasen. Används inte. 
      *@param user Instans utav user objekt ifrÂn user.java
      *@throws SQLException
      */   
    public void deleteUser (user user) throws SQLException {
    }
    
    /** Skapar ett nytt privatemessage i databasen. 
     * 
     * @param message
     * @throws java.sql.SQLException
     */
    public void insertPrivateMessage(privateMessage message) throws SQLException {
        /**Sätter namn på varje kolumn i tabellen.
         * 
         */ 
        String sqlStr = "INSERT INTO PrivateMessage " +
                "(`FromUserName`, `ToUserName`, `Title`, `Body`, `Date`, `Read`)" +
                 " VALUES (?, ?, ?, ?, NOW(), False)"; 
        
        PreparedStatement s = conn.prepareStatement(sqlStr);
        /** Lägger in variablerna 1, 2, 3, 4 i kolumnerna i tabellen PrivateMessage
         * 
         */
        s.setString(1, message.getFromUser());
        s.setString(2, message.getToUser());
        s.setString(3, message.getTitle());
        s.setString(4, message.getBody());
        s.executeUpdate();
        s.close();  
    }
       /** Hämtar ut ett visst meddelande från databasen.
        * 
        * @param message
        * @throws java.sql.SQLException
        */
    public void ViewPrivateMessage(privateMessage message) throws SQLException {
      
      /** Söker rätt på meddelandet som har ett visst ID.
       * 
       */
       
       String sqlStr = "Select `Title`, `Body`, `FromUserName`, `ToUserName`, Date_Format(`Date`,'%Y-%m-%d %H:%i') AS Date, `Read` " +
                      "FROM PrivateMessage " +
                      "WHERE `MessID` = ?";
      
      PreparedStatement s = conn.prepareStatement(sqlStr);
      s.setInt(1, message.getID());
      s.execute();
      ResultSet rs = s.getResultSet();

      /** Bygger ihop meddelandet som ska visas.
       * 
       */      
      if (rs.next()){
         message.setTitle(rs.getString("Title"));
         message.setBody(rs.getString("Body"));
         message.setFromUser(rs.getString("FromUserName"));
         message.setToUser(rs.getString("ToUserName"));
         message.setDatum(rs.getString("Date"));
         message.setRead(rs.getBoolean("Read"));
      } 
      s.close();
      }
    
    /** Markerar ett meddelande som läst. 
     * 
     * @param message
     * @throws java.sql.SQLException
     */
    public void RemoveFlag(privateMessage message) throws SQLException {
        
       String simpleProc = "{call RemoveFlag(?)}";
       
       CallableStatement cs = conn.prepareCall(simpleProc);
       cs.setInt(1, message.getID());
        cs.execute();
      cs.close();  
    }
  /** Räknar hur många olästa meddelanden en viss användare har. 
   * 
   * @param Username
   * @return
   * @throws java.sql.SQLException
   */
    public int newMesssages (String Username) throws SQLException {
     
      
      /** Räknar antalet olästa meddelanden i tabellen för den inloggade användaren.
       * 
       */ 
       String sqlStr = "Select COUNT(*) FROM PrivateMessage " +
                      "WHERE `Read` = False AND ToUserName = ?";
      
      PreparedStatement s = conn.prepareStatement(sqlStr); 
      s.setString(1, Username);
      s.execute();
      /** Lagrar resultatet i rs. 
       * 
       */
      ResultSet rs = s.getResultSet();
      int antalNyaMeddelanden;
      /** Gör om värdet till en vanlig integer. 
       * 
       */
      if (rs.next()){
          antalNyaMeddelanden = Integer.parseInt(rs.getObject(1) +"" );
      } 
      /** Om det inte finns någonting i resultatet rs. 
       * 
       */
      else { 
          antalNyaMeddelanden = 0;
      } 
      s.close();
      return antalNyaMeddelanden;   
    }
    
    /** Skapar ett nytt ämne i forumet. 
     * 
     * @param Subject
     * @throws java.sql.SQLException
     */
    public void insertBoardTopic(boardSubject Subject) throws SQLException {
        /** Sätter namn på varje kolumn i tabellen. 
         * 
         */
        String sqlStr = "INSERT INTO BoardSubject " +
                        "(`Title`,`Views`) " +
                        "VALUES (?,0)"; 
        String sqlStr2 = "INSERT INTO BoardReply " +
                        "(`SubjectID`, `UserName`, `Body`, `Date`)" +
                        " VALUES (LAST_INSERT_ID(), ?, ?, NOW())";
        
        PreparedStatement s = conn.prepareStatement(sqlStr);
        /** Lägger in variablerna 1, 2, 3, 4 i kolumnerna i tabellen boardSubject
         * 
         */
        s.setString(1, Subject.getTitle());
        s.executeUpdate();
        s.close();  
         
        s = conn.prepareStatement(sqlStr2); 
        s.setString(1, Subject.getUserName());
        s.setString(2, Subject.getBody());
        s.executeUpdate();
        s.close();  
    }

    /** Skapar ett nytt svar på ett ämne i forumet. 
     * 
     * @param Reply
     * @throws java.sql.SQLException
     */
    public void insertBoardReply(boardReply Reply) throws SQLException {
        /** Sätter namn på varje kolumn i tabellen. 
         * 
         */
        String sqlStr = "INSERT INTO BoardReply " +
                     "(`SubjectID`, `UserName`, `Body`, `Date`)" +
                       " VALUES (?, ?, ?, NOW())"; 
       
        
       PreparedStatement s = conn.prepareStatement(sqlStr);
        /** Lägger in variablerna 1, 2, 3, 4 i kolumnerna i tabellen boardReply
         * 
         */
     
      s.setInt(1, Reply.getSubjectID());
       s.setString(2, Reply.getUserName());
       s.setString(3, Reply.getBody());
        s.executeUpdate();
        s.close();
    }
    
    /** Listar alla ämnen i forumet. 
     * 
     * @param message
     * @throws java.sql.SQLException
     */
    public void showBoardTopics (boardSubject message) throws SQLException {
        
        ArrayList boardTopics = new ArrayList();
        
        String sqlStr= "Select Date_Format(user.LatestView,'%y%m%d%H%i%s') AS LatestView, Date_Format(r.Date, '%Y-%m-%d %H:%i') As 'LATEST DATE', r.UserName As 'LATEST USER', r.ReplyID As 'LATEST REPLY', Date_Format(r.Date,'%y%m%d%H%i%s') AS NewestView," +
                " Date_Format(t.Date, '%Y-%m-%d %H:%i') As 'First DATE', t.UserName As 'First USER', t.ReplyID As 'First REPLY', r.SubjectID, Count(s.ReplyID) As 'Hits', boardsubject.Title, boardsubject.Views " +
                "From boardreply r Inner Join boardreply s On s.SubjectID = r.SubjectID Inner Join boardreply t On t.SubjectID = r.SubjectID Inner Join boardsubject On boardsubject.SubjectID = s.SubjectID " +
                ", user Where user.UserName = ? " +
                " AND r.Date In (Select Max(boardreply.Date) " +
                " From boardreply " +
                "Group By boardreply.SubjectID " +
                "Order By boardreply.Date) And t.Date In (Select Min(boardreply.Date) " +
                "From boardreply Group By boardreply.SubjectID " +
                "Order By boardreply.Date) " +
                "Group By " +
                "boardsubject.Title, s.SubjectID " +
                "Order By r.Date Desc";
        PreparedStatement s = conn.prepareStatement(sqlStr);
       
        s.setString(1, message.getUserName());
        s.execute();
        ResultSet rs = s.getResultSet();
       while(rs.next()){ 
         boardSubject newmessage = new boardSubject();
          newmessage.setSubjectID(rs.getInt("SubjectID"));
          newmessage.setUserName(rs.getString("First USER"));
          newmessage.setTitle(rs.getString("Title"));
          newmessage.setDateCreated("" + rs.getString("First DATE"));
          newmessage.setLatestPost("" + rs.getString("LATEST DATE"));
          newmessage.setResponses(rs.getInt("Hits"));
          newmessage.setLatestUser(rs.getString("LATEST USER"));
          newmessage.setViews(rs.getInt("Views"));
          newmessage.setLatestPostID(rs.getInt("LATEST REPLY"));
          newmessage.setNewestView(rs.getLong("NewestView"));
          newmessage.setLatestView(rs.getLong("LatestView"));
          boardTopics.add(newmessage);  
        /** Returnerar listan.
         * 
         */
    }
      s.close(); 
       message.setTopics(boardTopics);
    }
    
    /** Visar alla svar till ett visst ämne. 
     * 
     * @param ID
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList showBoardReplies (boardReply ID) throws SQLException {
        /** Söker rätt på alla svar till ämnet med aktuellt ID.
         * 
         */
        ArrayList boardTopics = new ArrayList();
      
        String sqlStr = "Select SubjectID, ReplyID, Body, UserName, Date_Format(Date,'%Y-%m-%d %H:%i') AS VisualDate FROM BoardReply " +
                      "WHERE SubjectID = ? " +
                      "ORDER BY `Date` LIMIT 1, 1000000";
      PreparedStatement s = conn.prepareStatement(sqlStr); 
      s.setInt(1, ID.getSubjectID());
      
      s.execute();
      ResultSet rs = s.getResultSet();

      /** Bygger ihop svaren som ska visas.    
       * 
       */  
      while(rs.next()){
          boardReply message = new boardReply();
          message.setSubjectID(rs.getInt("SubjectID"));
          message.setReplyID(rs.getInt("ReplyID"));
          message.setBody(rs.getString("Body"));
          message.setUserName(rs.getString("UserName"));
          message.setDateCreated(rs.getString("VisualDate"));
          boardTopics.add(message);
        }
        
      s.close();   
      
      return boardTopics;   
    }
    
    public String findText(boardSubject ID) throws SQLException {
        
        /** Söker rätt på meddelandet som har ett visst ID.
         * 
         */
      
      String sqlStr = "Select * FROM boardreply " +
                       "WHERE SubjectID = ? Order BY boardreply.Date Limit 1";
      PreparedStatement s = conn.prepareStatement(sqlStr); 
      
      s.setInt(1, ID.getSubjectID());
      s.execute();
      ResultSet rs = s.getResultSet();

      /** Bygger ihop meddelandet som ska visas. 
       * 
       */     
      String message = new String();
      while(rs.next()){
          message = (rs.getString("Body"));
        }
        /** Returnerar listan tillbaka till privateMessage.java
         * 
         */
      s.close();  
      return message;   
    }
    
    /** Visar hur många gånger ett visst ämne i forumet har visats. 
     * 
     * @param ID
     * @throws java.sql.SQLException
     */
    public void increaseView (int ID) throws SQLException {
        
       String simpleProc = "{call CounterHit(?)}";
        CallableStatement cs = conn.prepareCall(simpleProc);
        cs.setInt(1, ID);
        cs.execute();
      cs.close();    
    }
    
    /** Uppdaterar attributet latestview i tabellen user. Detta för att
     * senare kunna användas när man vill veta vilka som har varit inne 
     * och läst på forumet den senaste halvtimmen. (se nästa).  
     * @param Name
     * @throws java.sql.SQLException
     */
    public void addViewTime (String Name) throws SQLException {
         
       String simpleProc = "{call ViewTime(?)}";
        
       CallableStatement cs = conn.prepareCall(simpleProc);
       cs.setString(1, Name); 
       cs.execute();
      cs.close();   
    }
    
    /** Visar vilka som har varit inne på forumet de senaste halvtimmen. 
     * 
     * @param Online
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList UpdateOnlineUsers(usersOnline Online) throws SQLException{
         
      String sqlStr = "SELECT * FROM User " +
                      "WHERE (NOW()-`LatestView`) <1800";
      PreparedStatement s = conn.prepareStatement(sqlStr); 
      s.execute();
      ResultSet rs = s.getResultSet();
      ArrayList tempUser = new ArrayList();
      
      while (rs.next()){
          usersOnline test = new usersOnline();
          test.setName(rs.getString("UserName"));
          tempUser.add(test);
      }
      s.close();
      
      /** Returnerar listan tillbaka till privateMessage.java 
       * 
       */
      return tempUser;
    }
}
