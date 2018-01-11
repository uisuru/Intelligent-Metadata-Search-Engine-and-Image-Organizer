/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLS;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Tag;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isuru
 */
public class MetaData_Extractor {

    public static void main(String[] args) throws ParseException {

        com.drew.metadata.Metadata metadata = new com.drew.metadata.Metadata();
        try {
            metadata = ImageMetadataReader.readMetadata(new File("C:\\Users\\Isuru\\Documents\\NetBeansProjects\\Panduka_Final\\src\\IMG\\bmp.bmp"));
            //metadata = ImageMetadataReader.readMetadata(new File("C:\\Users\\Isuru\\Documents\\NetBeansProjects\\Panduka_Final\\src\\IMG\\tif.tif"));
            //metadata = ImageMetadataReader.readMetadata(new File("C:\\Users\\Isuru\\Documents\\NetBeansProjects\\Panduka_Final\\src\\IMG\\gif.gif"));
            //metadata = ImageMetadataReader.readMetadata(new File("C:\\Users\\Isuru\\Documents\\NetBeansProjects\\Panduka_Final\\src\\IMG\\jpeg.jpeg"));
            //metadata = ImageMetadataReader.readMetadata(new File("C:\\\\Users\\\\Isuru\\\\Documents\\\\NetBeansProjects\\\\Panduka_Final\\\\src\\\\IMG\\\\png.png"));            
            //metadata = ImageMetadataReader.readMetadata(new File("C:\\Users\\Isuru\\Documents\\NetBeansProjects\\Panduka_Final\\src\\IMG\\jpg.jpg"));
        } catch (ImageProcessingException | IOException ex) {
            Logger.getLogger(MetaData_Extractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.format("[%s] - %s = %s \n",
                        directory.getName(), tag.getTagName(), tag.getDescription());
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
        }
    }
}
