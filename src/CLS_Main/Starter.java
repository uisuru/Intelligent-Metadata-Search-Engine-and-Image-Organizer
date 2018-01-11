/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLS_Main;

import GUI.Login;
import GUI.Login_first;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isuru
 */
public class Starter {

    public static void main(String[] args) {
        try {
            Connection c = DB_Big_Open.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("select * from user");
            if (rs.next()) {
                new Login().setVisible(true);
            } else {
                new Login_first().setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
