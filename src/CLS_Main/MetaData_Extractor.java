/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLS_Main;

import static CLS_Main.image_Finder.nummath;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isuru
 */
public class MetaData_Extractor {

    private static ArrayList<String> AR = new ArrayList<String>();
     static int p = 0;
    private static Vector v = new Vector();

    public static ArrayList<String> metaData_from_basic_functions(ArrayList<String> ARRAY, String namepart, boolean case_sen, String datee, int max, int min) throws SQLException {
        //Connection c = DB_Open.getConnection();
        //Statement s = c.createStatement();
        //s.executeUpdate("delete * from data");
        v.clear();
        for (int i = 0; i < ARRAY.size(); i++) {
            com.drew.metadata.Metadata metadata = new com.drew.metadata.Metadata();
            try {
                metadata = ImageMetadataReader.readMetadata(new File("" + ARRAY.get(i)));
                //System.out.println(ARRAY.get(i));
            } catch (ImageProcessingException | IOException ex) {
                Logger.getLogger(MetaData_Extractor.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean isOk = true;
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    //                    System.out.format("[%s] - %s = %s \n",//[GPS] - GPS Altitude Ref = Below sea level
                    //                            directory.getName(), tag.getTagName(), tag.getDescription());
                    switch (tag.getTagName()) {
                        case "File Name":
                            isOk = false;
                            if (!namepart.equals("")) {
                                if (case_sen) {
                                    isOk = tag.getDescription().contains(namepart);
                                }else{
                                    isOk = tag.getDescription().toLowerCase().contains(namepart.toLowerCase());
                                }
                            }
                            break;
                        case "File Size":
                            if ((max + min) / 2 != 0) {
                                isOk = isOk && (max > Integer.parseInt(tag.getDescription().replaceAll("bytes", "").replaceAll(" ", ""))) && (min < Integer.parseInt(tag.getDescription().replaceAll("bytes", "").replaceAll(" ", "")));
                            }
                            break;
                        case "File Modified Date":
                            if (!datee.equals("")) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd yyyy");
                                isOk = isOk && datee.equals(simpleDateFormat.format(tag.getDescription()));
                            }
                            break;
                    }
                    //System.out.println(tag);
                    //String tag0 = tag.getTagName().replaceAll(" ", "_").replaceAll("/", "_").replaceAll("-", "_");
                    //System.out.println(tag0 + " = "+ tag.getDescription());
                    //s.executeUpdate("insert into data("+tag0+") VALUES ('"+tag.getDescription()+"')");
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }
            }
            if (isOk) {
                AR.add(p, ARRAY.get(i));
                v.add(ARRAY.get(i));
                p++;
                //System.out.println(AR.get(i));
                System.out.println(ARRAY.get(i));
            }
            GUI.Main.jLabel1.setText("Scaning..." + i + " images currently found");
        }
//        for (int i = 0; i < AR.size(); i++) {
//            System.out.println(AR.get(i));
//        }
        GUI.Main.jLabel1.setText("Scan finish.... " + p + " Number of images found");
        p = 0;
        GUI.Main.jList1.setListData(v);
        return AR;
    }
    /*
     public static ArrayList<String> metaData_from_basic_functions(ArrayList<String> ARRAY, String namepart) {
     for (int i = 0; i < ARRAY.size(); i++) {
     com.drew.metadata.Metadata metadata = new com.drew.metadata.Metadata();
     try {
     metadata = ImageMetadataReader.readMetadata(new File("" + ARRAY.get(i)));
     System.out.println(ARRAY.get(i));
     } catch (ImageProcessingException | IOException ex) {
     Logger.getLogger(MetaData_Extractor.class.getName()).log(Level.SEVERE, null, ex);
     }
     for (Directory directory : metadata.getDirectories()) {
     for (Tag tag : directory.getTags()) {
     boolean isOk = false;
     System.out.format("[%s] - %s = %s \n",//[GPS] - GPS Altitude Ref = Below sea level
     directory.getName(), tag.getTagName(), tag.getDescription());
     switch (tag.getTagName()) {
     case "File Name":
     boolean contains = tag.getDescription().contains(namepart);
                            
     break;

     }
     if (isOk) {
     AR.add(ARRAY.get(i));
     }
     }
     if (directory.hasErrors()) {
     for (String error : directory.getErrors()) {
     System.err.format("ERROR: %s", error);
     }
     }
     }
     //System.out.println(i);
     }
     return AR;
     }*/
}
