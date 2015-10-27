/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Test;
import java.sql.*;
import java.sql.Timestamp;
import java.lang.Object.*;
import java.text.*;
/**
 *
 * @author Salle
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
//    
        
        
        
//        try {
//            // The newInstance() call is a work around for some
//            // broken Java implementations
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//        } catch (Exception ex) {
//            // handle the error
//        }
//        
//        Connection conn = null;
//            
//        try {
//            conn = 
//            DriverManager.getConnection("jdbc:mysql://localhost:3306/andsa121", "root", "");
//            // Do something with the Connection
//   
//        } 
//
//        catch (SQLException ex) {
//            // handle any errors
//            System.out.println("SQLException: " + ex.getMessage());
//            System.out.println("SQLState: " + ex.getSQLState());
//            System.out.println("VendorError: " + ex.getErrorCode());
//        }
//        String test ="Integra";
//         String sqlStr= "Select Date_Format(user.LatestView,'%y%m%d%H%i%s') AS LatestView, r.Date As 'LATEST DATE', r.UserName As 'LATEST USER', r.ReplyID As 'LATEST REPLY', Date_Format(r.Date,'%y%m%d%H%i%s') AS NewestView," +
//                " t.Date As 'First DATE', t.UserName As 'First USER', t.ReplyID As 'First REPLY', r.SubjectID, Count(s.ReplyID) As 'Hits', boardsubjectt.Title, boardsubjectt.Views " +
//                "From boardreplyy r Inner Join boardreplyy s On s.SubjectID = r.SubjectID Inner Join boardreplyy t On t.SubjectID = r.SubjectID Inner Join boardsubjectt On boardsubjectt.SubjectID = s.SubjectID " +
//                ", user Where user.UserName = '"+ test + "'" +
//                " AND r.Date In (Select Max(boardreplyy.Date) " +
//                " From boardreplyy " +
//                "Group By boardreplyy.SubjectID " +
//                "Order By boardreplyy.Date) And t.Date In (Select Min(boardreplyy.Date) " +
//                "From boardreplyy Group By boardreplyy.SubjectID " +
//                "Order By boardreplyy.Date) " +
//                "Group By " +
//                "boardsubjectt.Title, s.SubjectID " +
//                "Order By r.Date Desc";
//         Statement a = conn.createStatement(); 
//        String sqlStr2 = "SELECT UserName, Body, `Date`, MATCH (Body) AGAINST ('Detta') AS score FROM BoardReply WHERE Date BETWEEN (NOW()) AND (NOW()- INTERVAL 10 YEAR) AND UserName='Salle' AND MATCH (Body) AGAINST ('Detta' WITH QUERY EXPANSION)";
//        a.execute(sqlStr);
//        ResultSet rsa = a.getResultSet();
//      
//        while(rsa.next()){
//          
//         
//       
//          System.out.println(rsa.getInt("SubjectID") + "<br>");
//           System.out.println(rsa.getString("First USER")+ "<br>");
//           System.out.println(rsa.getString("Title")+ "<br>");
//           System.out.println("" + rsa.getString("First DATE")+ "<br>");
//           System.out.println("" + rsa.getString("LATEST DATE")+ "<br>");
//           System.out.println(rsa.getInt("Hits")+ "<br>");
//           System.out.println(rsa.getString("LATEST USER")+ "<br>");
//           System.out.println(rsa.getInt("Views")+ "<br>");
//           System.out.println(rsa.getInt("LATEST REPLY")+ "<br>");
//           System.out.println(rsa.getLong("NewestView")+ "<br>");
//           System.out.println(rsa.getLong("LatestView")+ "<br>");
//        System.out.println( "<br> <br>");
//        }
        
       
         
      
    
    }

}
