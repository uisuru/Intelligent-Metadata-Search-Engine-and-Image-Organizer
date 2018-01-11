/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLS_Main;

import static CLS_Main.MetaData_Extractor.p;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
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
public class image_data_to_database {

    public static void set(ArrayList<String> ARRAY) throws ParseException, SQLException {
        Connection c = DB_Open.getConnection();
        Statement s = c.createStatement();
        s.executeUpdate("delete * from data");
        for (int i = 0; i < ARRAY.size(); i++) {
            com.drew.metadata.Metadata metadata = new com.drew.metadata.Metadata();
            try {
                metadata = ImageMetadataReader.readMetadata(new File("" + ARRAY.get(i)));
                //System.out.println(ARRAY.get(i));
                s.executeUpdate("insert into data(ID) values('" + i + "')");
                s.executeUpdate("update data set path = '" + ARRAY.get(i) + "' where ID = '" + i + "'");
            } catch (ImageProcessingException | IOException ex) {
                //Logger.getLogger(MetaData_Extractor.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("this file got error(File format is not supported) = " + ARRAY.get(i));
            }
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if (!directory.getName().equals("Exif Thumbnail")) {
                        //                    System.out.format("[%s] - %s = %s \n",//[GPS] - GPS Altitude Ref = Below sea level
                        //                            directory.getName(), tag.getTagName(), tag.getDescription());
                        switch (tag.getTagName()) {
                            case "File Name":
                                s.executeUpdate("update data set File_Name = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "File Size":
                                s.executeUpdate("update data set File_Size = '" + tag.getDescription().replaceAll(" bytes", "") + "' where ID = '" + i + "'");
                                break;
                            case "File Modified Date":
                                String FMD = tag.getDescription(),
                                 year,
                                 month = "01",
                                 date;
                                year = FMD.replaceAll(".* ", "");
                                String dd[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                                for (int j = 0; j < dd.length; j++) {
                                    if (FMD.contains(dd[j])) {
                                        if (++j > 9) {
                                            month = "" + j;
                                        } else {
                                            month = "0" + j;
                                        }
                                    }
                                }
                                if (("" + FMD.charAt(9)).equals(" ")) {
                                    date = "0" + FMD.charAt(8);
                                } else {
                                    date = "" + FMD.charAt(8) + FMD.charAt(9);
                                }
                                //System.out.println(year + " " + month + " " + date );
                                s.executeUpdate("update data set File_Modified_Date = '" + year + " " + month + " " + date + "' where ID = '" + i + "'");
                                break;
                            case "Date/Time":
                                s.executeUpdate("update data set Date_Time = '" + tag.getDescription().replaceAll(":", " ") + "' where ID = '" + i + "'");
                                break;
//                            case "Date/Time Digitized":
//                                s.executeUpdate("update data set Date_Time = '" + tag.getDescription().replaceAll(":", " ") + "' where ID = '" + i + "'");
//                                break;
//                            case "Date/Time Original":
//                                s.executeUpdate("update data set Date_Time = '" + tag.getDescription().replaceAll(":", " ") + "' where ID = '" + i + "'");
//                                break;
                            case "Image Height":
                                s.executeUpdate("update data set Image_Height = '" + tag.getDescription().replaceAll(" pixels", "") + "' where ID = '" + i + "'");
                                break;
                            case "Image Width":
                                s.executeUpdate("update data set Image_Width = '" + tag.getDescription().replaceAll(" pixels", "") + "' where ID = '" + i + "'");
                                break;
                            case "Make":
                                s.executeUpdate("update data set Make = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Compression Type":
                                s.executeUpdate("update data set Compression_Type = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Flash":
                                s.executeUpdate("update data set Flash = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Bits Per Sample":
                                s.executeUpdate("update data set Bits_Per_Sample = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Bits per Pixel":
                                s.executeUpdate("update data set Bits_per_Pixel = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Has Global Color Table ":
                                s.executeUpdate("update data set Has_Global_Color_Table = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Is Color Table Sorted ":
                                s.executeUpdate("update data set Is_Color_Table_Sorted = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Signature":
                                s.executeUpdate("update data set Signature = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Copyright":
                                s.executeUpdate("update data set Copyright = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Software":
                                s.executeUpdate("update data set Software = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Compression":
                                s.executeUpdate("update data set Compression = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "GPS Longitude":
                                String sLn = tag.getDescription(),
                                 s1,
                                 s2,
                                 s3;
                                sLn = sLn.replaceAll(" ", "");
                                s1 = sLn.replaceAll(".*'", "").replaceAll("\"", "");
                                s2 = sLn.replaceAll(".*°", "").replaceAll("'.*", "");
                                s3 = sLn.replaceAll("°.*", "");
                                double d1 = (Double.parseDouble(s1) / 60) / 60;
                                double d2 = Double.parseDouble(s2) / 60;
                                double d3 = Double.parseDouble(s3) + d1 + d2;
                                s.executeUpdate("update data set GPS_Longitude = '" + d3 + "' where ID = '" + i + "'");
                                break;
                            case "GPS Latitude":
                                String sLd = tag.getDescription(),
                                 s4,
                                 s5,
                                 s6;
                                sLd = sLd.replaceAll(" ", "");
                                s4 = sLd.replaceAll(".*'", "").replaceAll("\"", "");
                                s5 = sLd.replaceAll(".*°", "").replaceAll("'.*", "");
                                s6 = sLd.replaceAll("°.*", "");
                                double d4 = (Double.parseDouble(s4) / 60) / 60;
                                double d5 = Double.parseDouble(s5) / 60;
                                double d6 = Double.parseDouble(s6) + d4 + d5;
                                s.executeUpdate("update data set GPS_Latitude = '" + d6 + "' where ID = '" + i + "'");
                                break;
                        }
                    }
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }
            }

            GUI.Main.jLabel1.setText("Updating Database..." + i + " images currently found");
        }
    }
    public static boolean set_big_DB(ArrayList<String> ARRAY) throws ParseException, SQLException {
        Connection c = DB_Big_Open.getConnection();
        Statement s = c.createStatement();
        s.executeUpdate("delete * from data");
        for (int i = 0; i < ARRAY.size(); i++) {
            com.drew.metadata.Metadata metadata = new com.drew.metadata.Metadata();
            try {
                metadata = ImageMetadataReader.readMetadata(new File("" + ARRAY.get(i)));
                //System.out.println(ARRAY.get(i));
                s.executeUpdate("insert into data(ID) values('" + i + "')");
                s.executeUpdate("update data set path = '" + ARRAY.get(i) + "' where ID = '" + i + "'");
            } catch (ImageProcessingException | IOException ex) {
                //Logger.getLogger(MetaData_Extractor.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("this file got error(File format is not supported) = " + ARRAY.get(i));
            }
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if (!directory.getName().equals("Exif Thumbnail")) {
                        //                    System.out.format("[%s] - %s = %s \n",//[GPS] - GPS Altitude Ref = Below sea level
                        //                            directory.getName(), tag.getTagName(), tag.getDescription());
                        switch (tag.getTagName()) {
                            case "File Name":
                                s.executeUpdate("update data set File_Name = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "File Size":
                                s.executeUpdate("update data set File_Size = '" + tag.getDescription().replaceAll(" bytes", "") + "' where ID = '" + i + "'");
                                break;
                            case "File Modified Date":
                                String FMD = tag.getDescription(),
                                 year,
                                 month = "01",
                                 date;
                                year = FMD.replaceAll(".* ", "");
                                String dd[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                                for (int j = 0; j < dd.length; j++) {
                                    if (FMD.contains(dd[j])) {
                                        if (++j > 9) {
                                            month = "" + j;
                                        } else {
                                            month = "0" + j;
                                        }
                                    }
                                }
                                if (("" + FMD.charAt(9)).equals(" ")) {
                                    date = "0" + FMD.charAt(8);
                                } else {
                                    date = "" + FMD.charAt(8) + FMD.charAt(9);
                                }
                                //System.out.println(year + " " + month + " " + date );
                                s.executeUpdate("update data set File_Modified_Date = '" + year + " " + month + " " + date + "' where ID = '" + i + "'");
                                break;
                            case "Date/Time":
                                s.executeUpdate("update data set Date_Time = '" + tag.getDescription().replaceAll(":", " ") + "' where ID = '" + i + "'");
                                break;
//                            case "Date/Time Digitized":
//                                s.executeUpdate("update data set Date_Time = '" + tag.getDescription().replaceAll(":", " ") + "' where ID = '" + i + "'");
//                                break;
//                            case "Date/Time Original":
//                                s.executeUpdate("update data set Date_Time = '" + tag.getDescription().replaceAll(":", " ") + "' where ID = '" + i + "'");
//                                break;
                            case "Image Height":
                                s.executeUpdate("update data set Image_Height = '" + tag.getDescription().replaceAll(" pixels", "") + "' where ID = '" + i + "'");
                                break;
                            case "Image Width":
                                s.executeUpdate("update data set Image_Width = '" + tag.getDescription().replaceAll(" pixels", "") + "' where ID = '" + i + "'");
                                break;
                            case "Make":
                                s.executeUpdate("update data set Make = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Compression Type":
                                s.executeUpdate("update data set Compression_Type = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Flash":
                                s.executeUpdate("update data set Flash = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Bits Per Sample":
                                s.executeUpdate("update data set Bits_Per_Sample = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Bits per Pixel":
                                s.executeUpdate("update data set Bits_per_Pixel = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Has Global Color Table ":
                                s.executeUpdate("update data set Has_Global_Color_Table = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Is Color Table Sorted ":
                                s.executeUpdate("update data set Is_Color_Table_Sorted = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Signature":
                                s.executeUpdate("update data set Signature = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Copyright":
                                s.executeUpdate("update data set Copyright = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Software":
                                s.executeUpdate("update data set Software = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "Compression":
                                s.executeUpdate("update data set Compression = '" + tag.getDescription() + "' where ID = '" + i + "'");
                                break;
                            case "GPS Longitude":
                                String sLn = tag.getDescription(),
                                 s1,
                                 s2,
                                 s3;
                                sLn = sLn.replaceAll(" ", "");
                                s1 = sLn.replaceAll(".*'", "").replaceAll("\"", "");
                                s2 = sLn.replaceAll(".*°", "").replaceAll("'.*", "");
                                s3 = sLn.replaceAll("°.*", "");
                                double d1 = (Double.parseDouble(s1) / 60) / 60;
                                double d2 = Double.parseDouble(s2) / 60;
                                double d3 = Double.parseDouble(s3) + d1 + d2;
                                s.executeUpdate("update data set GPS_Longitude = '" + d3 + "' where ID = '" + i + "'");
                                break;
                            case "GPS Latitude":
                                String sLd = tag.getDescription(),
                                 s4,
                                 s5,
                                 s6;
                                sLd = sLd.replaceAll(" ", "");
                                s4 = sLd.replaceAll(".*'", "").replaceAll("\"", "");
                                s5 = sLd.replaceAll(".*°", "").replaceAll("'.*", "");
                                s6 = sLd.replaceAll("°.*", "");
                                double d4 = (Double.parseDouble(s4) / 60) / 60;
                                double d5 = Double.parseDouble(s5) / 60;
                                double d6 = Double.parseDouble(s6) + d4 + d5;
                                s.executeUpdate("update data set GPS_Latitude = '" + d6 + "' where ID = '" + i + "'");
                                break;
                        }
                    }
                }
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.format("ERROR: %s", error);
                    }
                }
            }

            GUI.Main.jLabel1.setText("Updating Database..." + i + " images currently found");
        }
        return true;
    }
}
