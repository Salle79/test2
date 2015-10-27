package databas;
import logik.*;
import java.sql.*;
import java.util.ArrayList;

public class Search extends Database {

    /** Tar fram alla detaljer g�llande en viss anv�ndare.
     * 
     * @param user
     * @throws java.sql.SQLException
     */
    public void findUserByUserName(search user) throws SQLException {
         
        String sqlStr = "Select * FROM User WHERE" +
                     " UserName = ?";
     
         /**Skapar ett prepared statement s som inneh�ller queryn sqlStr
          * 
          */
         PreparedStatement s = conn.prepareStatement(sqlStr);
         s.setString(1, user.getUser());
         s.execute();
         ResultSet rs = s.getResultSet();
         /** Plockar ut v�rdena ur rs. 
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
    
    /**Fulltexts�k d�r relevansen �r med. 
     *  
     * @param search
     * @return
     * @throws java.sql.SQLException
     */
    public ArrayList FullTextSearchBody(fulltextSearch search)throws SQLException{
        String sqlSort="";
        String sqlAdd="";
        int countColumn=1;
        
        /** Om s�kningen g�ller en viss anv�ndare
         * 
         */
        if(search.getSearchUser()!=""){ sqlAdd = "AND r.UserName=?";} 
        /** Om valet att s�kningen ska sorteras i datumordning
         * 
         */
        if(search.getChoise()==2){sqlSort = " ORDER BY r.Date DESC";}
        /** Om valet att s�kningen ska sorteras i relevansordning
         * 
         */
        if(search.getChoise()==3){sqlSort = " ORDER BY s.Hits DESC";}
        
        /** S�tter ihop hela sql-queryn till en str�ng. Sqladd �r tom om det inte g�ller
         * en vissa anv�ndare. sqlsort inneh�ller str�ngen med sorteringsordningen i.
         * Match �r funktionen som j�mf�r om body inneh�ller s�kordet. 
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
        
        /** Fyller den f�rsta kolumnen i s med s�kordet/n. 
         * 
         */
        s.setString(countColumn++,search.getInclude());
        /** Fyller den andra kolumnen i s med minv�rdet p� "s�k poster som �r mellan..." 
         * 
         */
        s.setInt(countColumn++, search.getMin());
        /** Fyller den tredje kolumnen i s med maxv�rdet p� "s�k poster som �r mellan..." 
         * 
         */
        s.setInt(countColumn++, search.getMax());

        /** Fyller den fj�rde kolumnen i s med ett anv�ndarnamn i de fall det finns ett.  
         * 
         */
        if(search.getSearchUser()!=""){ s.setString(countColumn++,search.getSearchUser());}  
        /** Fyller den fj�rde (el. femte) kolumnen med s�kordet igen.
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
        /** Stoppar in s�kresultatet i en arraylist. 
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
         /** Skickar tillbaka s�kresultatet.
          * 
          */ 
         return newMessage;
    }
    
    /** Om n�gonting ska exkluderas fungerar inte relevans�kningen. 
     * D� anv�nds denna. Samma funktion som ovan. 
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