/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package production;


import production.*;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tenusha
 */

public class dbCon {
   static Connection con;
   void connect(){
       
       try {
           Class.forName("com.mysql.jdbc.Driver");
           con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/production?zeroDateTimeBehavior=convertToNull", "root", "");
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(dbCon.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SQLException ex) {
           Logger.getLogger(dbCon.class.getName()).log(Level.SEVERE, null, ex);
       }
       
   }
}


