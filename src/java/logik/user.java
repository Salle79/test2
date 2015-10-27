

package logik;

import java.sql.*;
import databas.*;
import java.util.ArrayList;

public class user {
    
    /** Variabel f�r anv�ndarniv�, anv�ndarnamn, l�senord, riktiga namn, epost och adress
     * 
     */
    private int userLevel = 1;
    private String userName;
    private String password;
    private String fullName;
    private String email; 
    private String adress;
    
    /** Skapar en ny instans av user
     * 
     */
    public user() {
    }
 
    /**
     * H�mta anv�ndarnamn p� aktuell anv�ndare.
     * @return 
     */
    public String getUserName() {
        return this.userName;
    }
    
    /**
     * S�tter anv�ndarnamn
     * @param userName Anv�ndarnamn
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * H�mtar l�senord
     * @return Returnerar l�senord
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Skapar l�senord
     * @param Password Anv�ndarens l�senord
     */
    public void setPassword(String Password) {
        this.password = Password;
    }
    
    /**
     * H�mta anv�ndarens riktiga namn.
     * @return Returnera anv�ndarens f�r- och efternamn
     */
    public String getFullName() {
        return this.fullName;
    }
    
    /**
     * L�gger in riktigt namn f�r anv�ndaren. 
     * @param fullName Anv�ndares riktiga namn
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    /**
     * H�mtar anv�ndarens beh�righetsniv�
     * @return Returnera anv�ndares beh�righetsniv�
     */
    public int getUserLevel() {
        return this.userLevel;
    }
    
    /**
     * Ger anv�ndaren en ny beh�righetsniv�
     * @param userLevel Anv�ndares beh�girhetsniv�
     */
    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
    
    /**
     * H�mtar anv�ndarens epostadress
     * @return Returnerar anv�ndarens email
     */
    public String getEmail () {
        return this.email;
    }
  
    /**
     * L�gger in en epostadress till anv�ndarens kontouppgifter
     * @param email Anv�ndares Email
     */
    public void setEmail(String email) {
        this.email = email;
    }
     /**
     * H�mtar anv�ndarens adress. 
     * @param adress Anv�ndares adress
     */
    public String getAdress() {
        return this.adress;
    }
    /**
     * L�gger in adress till anv�ndarens kontouppgifter
     * @param adress Anv�ndares adress
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }
    
    /**
     * Skapar nytt anv�ndarkonto
     * @throws java.sql.SQLException Kastar undantag om databasfunktionen inte fungerar korrekt
     */
    public boolean addUser() throws SQLException {
        boolean kontoSkapat =false;
        Database db = new Database();
        if(db.findUserByUsername(this)==false) {
            kontoSkapat = true;
            db.insertUser(this);
        }
        return kontoSkapat;
    }
    
    /** Kontrollerar om anv�ndaren finns. 
     * 
     * @return
     * @throws java.sql.SQLException
     */
    public boolean checkUser() throws SQLException {
        Database db = new Database();
        return db.findUserByUsername(this);
    }
    
    /**
     * Uppdatera anv�ndarkonto
     * @throws java.sql.SQLException Kastar undantag om databasfunktionen inte fungerar korrekt
     */
    public void updateUser() throws SQLException {
    
    }
    
    public void editUser() {
        
    }
    /**
     * Ta bort anv�ndarkonto
     * @throws java.sql.SQLException Kastar undantag om databasfunktionen inte fungerar korrekt
     */
    public void deleteUser() throws SQLException {
        
    }
    
    /**
     * Visa anv�ndare
     * @param userId Anv�ndarnamn
     * @throws java.sql.SQLException Kastar undantag om databasfunktionen inte fungerar korrekt
     */
    public void showUser(int userId) throws SQLException {
       
    }    
    
    /**
     * G�r en lista p� samtliga anv�ndare
     * @throws java.sql.SQLException Kastar undantag om databasfunktionen inte fungerar korrekt
     * @return Array en lista med anv�ndaruppgifter
     */
    public ArrayList listAllUsers() throws SQLException {
      Database db = new Database();
      ArrayList Users = db.listAllUsers(); 
      ArrayList userList = new ArrayList();
      
      for(int t=0; t < Users.size(); t++)
      {
          String[] tempSQL = new String[2];
          user tempUser = new user();
          tempUser = (user)Users.get(t);
          tempSQL[0] = tempUser.getUserName();
          tempSQL[1] = tempUser.getFullName();
          userList.add(tempSQL);
      }
      return userList;
    }    
    
    /**
     * Inloggning
     * @return Returnerar boolean sant eller falskt om inloggningen lyckats
     * @throws java.sql.SQLException Kastar undantag om databasfunktionen inte fungerar korrekt
     */
    public boolean logIn() throws SQLException {
        Database db = new Database();
        return db.findUserByPassword(this);
    }
    public void addViewTime(String Name) throws SQLException {
       Database db = new Database();
       db.addViewTime(Name);
    }
    public void FindUserByUserName() throws SQLException {
        Search db = new Search();
        db.findUserByPassword(this);
    }
    
    /**
     * Logga ut (existerar som referens, fyller ingen funktion 
     * sk�ts av Session-funktionen p� webbniv�)
     */
    public void logOut() {

    }    
}
