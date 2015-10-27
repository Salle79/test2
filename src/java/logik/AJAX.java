
package logik;
import databas.*;
import java.sql.*;

/** Funktion som kollar om en användare finns sen tidigare eller inte. Anropas 
 * direkt från webben aktivt medan användaren fyller i formuläret för ny användare. 
 */
public class AJAX {

    public boolean checkUser(String name) throws SQLException {
        Database db = new Database();
        return db.findUserByUsername(name);
    }
}
