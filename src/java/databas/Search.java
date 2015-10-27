package databas;
import logik.*;
import java.sql.*;
import java.util.ArrayList;

public class Search extends Database {

    /** Tar fram alla detaljer gällande en viss användare.
     * 
     * @param user
     * @throws java.sql.SQLException
     */
    public void findUserByUserName(search user) throws SQLException {
         
        String sqlStr = "Select * FROM User WHERE" +
                     " UserName = ?";
     
         /**Skapar ett prepared statement s som innehåller queryn sqlStr
          * 
          */
         PreparedStatement s = conn.prepareStatement(sqlStr);
         s.setString(1, user.getUser());
         s.execute();
         ResultSet rs = s.getResultSet();
         /** Plockar ut värdena ur rs. 
          * 
          */
         while(rs.next()) {
             user.setUser(rs.getString("UserName"));
             user.setName(rs.getString("Name"));
             user.setAdress(rs.getString("Address"));
             user.setLatestView(rs.getString("LatestView"));
             user.setPicture(rs.getString("Picture"));
             user.setEmail(rs.getString("Email"));
         }
         s.close();
    }
    
    /**Fulltextsök där relevansen är med. 
     *  
     * @param search
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList FullTextSearchBody(fulltextSearch search)throws SQLException{
        String sqlSort="";
        String sqlAdd="";
        int countColumn=1;
        
        /** Om sökningen gäller en viss användare
         * 
         */
        if(search.getSearchUser()!=""){ sqlAdd = "AND r.UserName=?";} 
        /** Om valet att sökningen ska sorteras i datumordning
         * 
         */
        if(search.getChoise()==2){sqlSort = " ORDER BY r.Date DESC";}
        /** Om valet att sökningen ska sorteras i relevansordning
         * 
         */
        if(search.getChoise()==3){sqlSort = " ORDER BY s.Hits DESC";}
        
        /** Sätter ihop hela sql-queryn till en sträng. Sqladd är tom om det inte gäller
         * en vissa användare. sqlsort innehåller strängen med sorteringsordningen i.
         * Match är funktionen som jämför om body innehåller sökordet. 
         */
        String sqlStr ="SELECT r.UserName, r.Body, r.`Date`,s.Title, RIGHT(FORMAT(MATCH (r.Body) " +
                "AGAINST (?)*100,2),5)  AS score FROM BoardReply r, BoardSubject s " +
                "WHERE  r.SubjectID = s.SubjectID AND r.Date BETWEEN (NOW()- INTERVAL ? DAY) " +
                "AND (NOW()- INTERVAL ? DAY) " + sqlAdd + "AND MATCH (r.Body) AGAINST (?) " 
                +sqlSort; 
        /** Skapar ett prepared statemet s av sqlsatsen. 
         * 
         */
        PreparedStatement s = conn.prepareStatement(sqlStr);
        
        /** Fyller den första kolumnen i s med sökordet/n. 
         * 
         */
        s.setString(countColumn++,search.getInclude());
        /** Fyller den andra kolumnen i s med minvärdet på "sök poster som är mellan..." 
         * 
         */
        s.setInt(countColumn++, search.getMin());
        /** Fyller den tredje kolumnen i s med maxvärdet på "sök poster som är mellan..." 
         * 
         */
        s.setInt(countColumn++, search.getMax());

        /** Fyller den fjärde kolumnen i s med ett användarnamn i de fall det finns ett.  
         * 
         */
        if(search.getSearchUser()!=""){ s.setString(countColumn++,search.getSearchUser());}  
        /** Fyller den fjärde (el. femte) kolumnen med sökordet igen.
         * 
         */  
        s.setString(countColumn++,search.getInclude());
        s.execute();

        if(search.getSearchUser()!=""){ s.setString(countColumn++,search.getSearchUser());}  
         s.execute();

         
        /** Sparar resultatet i rs. 
         * 
         */
        ResultSet rs = s.getResultSet();
        ArrayList newMessage = new ArrayList();
        /** Stoppar in sökresultatet i en arraylist. 
         * 
         */
        while(rs.next()) {
             fulltextSearch newSearch = new fulltextSearch();
             newSearch.setMessage(rs.getString("Body"));
            newSearch.setSearchUser(rs.getString("UserName"));
            newSearch.setRevelance(rs.getFloat("score"));
            newSearch.setLatestDate( "" + rs.getDate("Date"));
            newSearch.setTitle(rs.getString("Title"));
            newMessage.add(newSearch);
         }
         s.close(); 
         /** Skickar tillbaka sökresultatet.
          * 
          */ 
         return newMessage;
    }
    
    /** Om någonting ska exkluderas fungerar inte relevansökningen. 
     * Då används denna. Samma funktion som ovan. 
     * 
     * @param search
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList BooleanSearch (fulltextSearch search)throws SQLException {
        String sqlSort="";
        String sqlAdd="";
        int countColumn=1;
        if(search.getSearchUser()!=""){ sqlAdd = " AND r.UserName= ? ";}  
         if(search.getChoise()==2){sqlSort = " ORDER BY r.Date DESC";}
        if(search.getChoise()==3){sqlSort = " ORDER BY s.Hits DESC";}
        String sqlStr ="SELECT r.UserName, r.Body, r.`Date`,s.Title, r.Body FROM BoardReply r, BoardSubject s "+
        "WHERE r.SubjectID = s.SubjectID AND MATCH (r.Body) " +
        "AGAINST (? IN BOOLEAN MODE) "+
       " AND r.Date BETWEEN (NOW()- INTERVAL ? DAY) " + 
        "AND (NOW()- INTERVAL ? DAY)" + sqlAdd +"" +sqlSort; 
     
        PreparedStatement s = conn.prepareStatement(sqlStr);
        
        s.setString(countColumn++,"'+" + search.getInclude() + ", -" +search.getExclude());
        s.setInt(countColumn++, search.getMin());
        s.setInt(countColumn++, search.getMax());
        if(search.getSearchUser()!=""){ s.setString(countColumn++,search.getSearchUser());} 
         s.execute();
         
         ResultSet rs = s.getResultSet();
         ArrayList newMessage = new ArrayList();
       
         while(rs.next()) {
             fulltextSearch newSearch = new fulltextSearch();
             newSearch.setMessage(rs.getString("Body"));
            newSearch.setSearchUser(rs.getString("UserName"));
            newSearch.setLatestDate( "" + rs.getDate("Date"));
            newSearch.setTitle(rs.getString("Title"));
            newMessage.add(newSearch);
         }
         s.close(); 
         return newMessage;
    }   

}