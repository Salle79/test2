
package logik;
import databas.*;
import java.sql.*;

/** Funktion som kollar om en anv�ndare finns sen tidigare eller inte. Anropas 
 * direkt fr�n webben aktivt medan anv�ndaren fyller i formul�ret f�r ny anv�ndare. 
 */
public class AJAX {

    public boolean checkUser(String name) throws SQLException {
        Database db = new Database();
        return db.findUserByUsername(name);
    }
}
