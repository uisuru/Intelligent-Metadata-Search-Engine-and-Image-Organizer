/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLS_Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JOptionPane;

public class MoveFile {

//    public static void main(String[] args) {
//     move_new("G:\\My Life\\Superstar Sirasa TV 19th June 2016.mp4", "C:\\");
//     }
    public static boolean move(String pathtoCopy, String pathTopast) {
        Path movefrom = FileSystems.getDefault().getPath(pathtoCopy);
        Path target = FileSystems.getDefault().getPath(pathTopast);
        if (crate_dir(pathTopast)) {
            try {
                Files.move(movefrom, target.resolve(movefrom.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                return true;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Move file to Selected path is prohibited. Please select another location",  "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Move file to Selected path is prohibited. Please select another location",  "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    public static boolean crate_dir(String Path) {
        Path path = Paths.get(Path);//"C:\\Directory2\\Sub2\\Sub-Sub2"
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
    }
}
