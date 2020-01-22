/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick
 */
public class Database {
    static Database getInstance;
    Connection con;

    public Database() throws SQLException {
        try {
            Class.forName("org.postgres.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/travelguide", "postgres", "postgres");
    }
    
    public synchronized static Database getInstance() throws SQLException
    {
        if(getInstance == null)
        {
            getInstance = new Database();
        }
        return getInstance;
    }
    
    //New
    
}
