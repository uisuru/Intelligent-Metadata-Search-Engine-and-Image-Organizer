/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLS_Main;

import static CLS_Main.MetaData_Extractor.p;
import static GUI.Main.jCo_size_plu_min;
import static GUI.Main.jTe_size;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isuru
 */
public class image_data_from_database {

    public static ArrayList<String> get(String file_name, String min, String max, String date, String Height, String Width,
            String Range, String make) throws ParseException, SQLException {
        ArrayList<String> Array = new ArrayList<String>();
        try {
            Connection c = DB_Open.getConnection();
            Statement s = c.createStatement();

            String max_h, max_w, min_h, min_w;

            min_h = "" + (Integer.parseInt(Height) - (Integer.parseInt(Height) / 100) * Integer.parseInt(Range));
            max_h = "" + (Integer.parseInt(Height) + (Integer.parseInt(Height) / 100) * Integer.parseInt(Range));
            min_w = "" + (Integer.parseInt(Width) - (Integer.parseInt(Width) / 100) * Integer.parseInt(Range));
            max_w = "" + (Integer.parseInt(Width) + (Integer.parseInt(Width) / 100) * Integer.parseInt(Range));

            ResultSet rs = s.executeQuery("select path from data where file_name like '%" + file_name + "%' and file_size BETWEEN " + min + " AND " + max + " "
                    + "and Date_Time like '%" + date.replaceAll(":", " ") + "%' and Image_Height between " + min_h + " and " + max_h + " "
                    + "and Image_Width between " + min_w + " and " + max_w + " and make like '%" + make + "%'");
//            ResultSet rs = s.executeQuery("select path from data where file_name like '%" + file_name + "%'");
            System.out.println("select path from data where file_name like '%" + file_name + "%' and file_size BETWEEN " + min + " AND " + max + " "
                    + "and Date_Time like '%" + date.replaceAll(":", " ") + "%' and Image_Height between " + min_h + " and " + max_h + " "
                    + "and Image_Width between " + min_w + " and " + max_w + " and make like '%" + make + "%'");
            int p = 0;
            Vector v = new Vector();
            while(rs.next()) {
                Array.add(p, rs.getString("path"));
                v.add(Array.get(p));
                p++;
                //System.out.println(AR.get(i));
                //System.out.println(Array.get(p));
                System.out.println(rs.getString("path"));
            }
            System.out.println("dd");

//        for (int i = 0; i < AR.size(); i++) {
//            System.out.println(AR.get(i));
//        }
            GUI.Main.jLabel1.setText("Scan finish.... " + p + " Number of images found");
            GUI.File_Org.jLabel1.setText("Scan finish.... " + p + " Number of images found");
            p = 0;
            GUI.Main.jList1.setListData(v);
            GUI.File_Org.jList1.setListData(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Array;
    }
    public static ArrayList<String> get(String quary,boolean DB) throws ParseException, SQLException {
        ArrayList<String> Array = new ArrayList<String>();
        try {
            Connection c;
            if (DB) {
                c = DB_Big_Open.getConnection();
            } else {
                c = DB_Open.getConnection();
            }
            Statement s = c.createStatement();

            ResultSet rs = s.executeQuery(quary);
//            ResultSet rs = s.executeQuery("select path from data where file_name like '%" + file_name + "%'");
            int p = 0;
            Vector v = new Vector();
            while(rs.next()) {
                Array.add(p, rs.getString("path"));
                v.add(Array.get(p));
                p++;
                //System.out.println(AR.get(i));
                //System.out.println(Array.get(p));
                System.out.println(rs.getString("path"));
            }

//        for (int i = 0; i < AR.size(); i++) {
//            System.out.println(AR.get(i));
//        }
            GUI.Main.jLabel1.setText("Scan finish.... " + p + " Number of images found");
            GUI.File_Org.jLabel1.setText("Scan finish.... " + p + " Number of images found");
            p = 0;
            GUI.Main.jList1.setListData(v);
            GUI.File_Org.jList1.setListData(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Array;
    }
}
