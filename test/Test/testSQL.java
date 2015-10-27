/*
 * Main.java
 *
 * Created on den 18 juli 2008, 14:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Test;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Salle
 */
public class testSQL {
    
    /** Creates a new instance of Main */
    public testSQL() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
     Connection conn = null;
     String DBCONNSTR = "jdbc:mysql://localhost/test";
     String DBUSER = "root";
     String DBPASSWD = "spunk";
        
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(DBCONNSTR, DBUSER, DBPASSWD);
        }
        catch(Exception e) {
            e.getMessage();
        }
        
        
        Statement s = conn.createStatement();
        String sqlStr = "Select * FROM test";
        s.execute(sqlStr);
       ResultSet rs = s.getResultSet();
      
        while(rs.next()){
          System.out.println(rs.getString("Namn"));
        }
      
      
       
       
       
       
       
       System.out.println(rs.next());
        s.close();
        System.exit(1);
    }
    
}
