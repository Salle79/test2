

package logik;

import java.sql.*;
import databas.*;
import java.util.ArrayList;

public class user {
    
    /** Variabel för användarnivå, användarnamn, lösenord, riktiga namn, epost och adress
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
     * Hämta användarnamn på aktuell användare.
     * @return 
     */
    public String getUserName() {
        return this.userName;
    }
    
    /**
     * Sätter användarnamn
     * @param userName Användarnamn
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    /**
     * Hämtar lösenord
     * @return Returnerar lösenord
     */
    public String getPassword() {
        return this.password;
    }
    
    /**
     * Skapar lösenord
     * @param Password Användarens lösenord
     */
    public void setPassword(String Password) {
        this.password = Password;
    }
    
    /**
     * Hämta användarens riktiga namn.
     * @return Returnera användarens för- och efternamn
     */
    public String getFullName() {
        return this.fullName;
    }
    
    /**
     * Lägger in riktigt namn för användaren. 
     * @param fullName Användares riktiga namn
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    /**
     * Hämtar användarens behörighetsnivå
     * @return Returnera användares behörighetsnivå
     */
    public int getUserLevel() {
        return this.userLevel;
    }
    
    /**
     * Ger användaren en ny behörighetsnivå
     * @param userLevel Användares behögirhetsnivå
     */
    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }
    
    /**
     * Hämtar användarens epostadress
     * @return Returnerar användarens email
     */
    public String getEmail () {
        return this.email;
    }
  
    /**
     * Lägger in en epostadress till användarens kontouppgifter
     * @param email Användares Email
     */
    public void setEmail(String email) {
        this.email = email;
    }
     /**
     * Hämtar användarens adress. 
     * @param adress Användares adress
     */
    public String getAdress() {
        return this.adress;
    }
    /**
     * Lägger in adress till användarens kontouppgifter
     * @param adress Användares adress
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }
    
    /**
     * Skapar nytt användarkonto
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
    
    /** Kontrollerar om användaren finns. 
     * 
     * @return
     * @throws java.sql.SQLException
     */
    public boolean checkUser() throws SQLException {
        Database db = new Database();
        return db.findUserByUsername(this);
    }
    
    /**
     * Uppdatera användarkonto
     * @throws java.sql.SQLException Kastar undantag om databasfunktionen inte fungerar korrekt
     */
    public void updateUser() throws SQLException {
    
    }
    
    public void editUser() {
        
    }
    /**
     * Ta bort användarkonto
     * @throws java.sql.SQLException Kastar undantag om databasfunktionen inte fungerar korrekt
     */
    public void deleteUser() throws SQLException {
        
    }
    
    /**
     * Visa användare
     * @param userId Anv‰ndarnamn
     * @throws java.sql.SQLException Kastar undantag om databasfunktionen inte fungerar korrekt
     */
    public void showUser(int userId) throws SQLException {
       
    }    
    
    /**
     * Gör en lista på samtliga användare
     * @throws java.sql.SQLException Kastar undantag om databasfunktionen inte fungerar korrekt
     * @return Array en lista med användaruppgifter
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
     * sköts av Session-funktionen på webbnivå)
     */
    public void logOut() {

    }    
}
