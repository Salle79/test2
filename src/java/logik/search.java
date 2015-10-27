package logik;
import databas.*;
import java.sql.SQLException;

public class search {
    /** Alla variabler som behövs.
     * 
     */
private  String User;
private  String Name;
private  String Adress;
private  String LatestView;
private  String Picture;
private String Email;

/** Get och set för alla variabler. 
 * 
 * @return
 */
    public String getUser() {return User;}
    public void setUser(String User) {this.User = User;}
    public String getName() {return Name;}
    public void setName(String Name) {this.Name = Name;}
    public String getAdress() {return Adress;}
    public void setAdress(String Adress) {this.Adress = Adress;}
    public String getLatestView() {return LatestView;}
    public void setLatestView(String LatestView) { this.LatestView = LatestView;}
    public String getPicture() {return Picture; }
    public void setPicture(String Picture) {this.Picture = Picture;}   
    public String getEmail() {return Email;}
    public void setEmail(String Email) {this.Email = Email;}
    
    /** Söker efter en användare. 
     * 
     * @throws java.sql.SQLException
     */
    public void findUser() throws SQLException{
         
        Search db = new Search();
        db.findUserByUserName(this);
        }
}
