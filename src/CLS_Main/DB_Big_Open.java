package CLS_Main;

import java.sql.*;

public class DB_Big_Open {

    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                conn = DriverManager.getConnection("jdbc:ucanaccess://DB_All.accdb;jackcessOpener=CLS_Main.CryptCodecOpener", "user", "");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return conn;
    }
}
