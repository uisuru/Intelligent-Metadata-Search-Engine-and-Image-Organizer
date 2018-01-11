/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLS_Main;

import GUI.File_Org;
import GUI.Main;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Isuru
 */
public class image_Finder {

    public static int finalTotal = 0, nummath = 0;
    private static ArrayList<String> resultFileList = new ArrayList<String>();
    private static Vector v = new Vector();
    public static class Finder
            extends SimpleFileVisitor<Path> {

        private final PathMatcher matcher;
        private int numMatches = 0;

        Finder(String pattern) {
            matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:" + pattern);
        }

        Finder() {
            matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:*.{jpg,png,jpeg,gif,tif,bmp}");
        }

        // Compares the glob pattern against
        // the file or directory name.
        void find(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                resultFileList.add(numMatches, file.toAbsolutePath().toString());
                System.out.println(resultFileList.get(numMatches));
                v.add(resultFileList.get(numMatches));
                numMatches++;
                nummath = numMatches;
                Main.jLabel1.setText("Scaning..." + nummath + " images currently found");
                File_Org.jLabel1.setText("Scaning..." + nummath + " images currently found");
                Main.jProgressBar1.setValue(nummath);
                File_Org.jProgressBar1.setValue(nummath);
                if (nummath < 1000) {
                    Main.jProgressBar1.setMaximum(nummath + 20);
                    File_Org.jProgressBar1.setValue(nummath+20);
                } else if (nummath < 10000) {
                    Main.jProgressBar1.setMaximum(nummath + 300);
                    File_Org.jProgressBar1.setValue(nummath+300);
                } else if (nummath < 100000) {
                    Main.jProgressBar1.setMaximum(nummath + 2000);
                    File_Org.jProgressBar1.setValue(nummath+2000);
                } else if (nummath < 1000000) {
                    Main.jProgressBar1.setMaximum(nummath + 20000);
                    File_Org.jProgressBar1.setValue(nummath+20000);
                }
                //System.out.println(file);
                //System.out.println(file.toAbsolutePath().toString());
            }
        }
        // Prints the total number of
        // matches to standard out.
        public void done() {
            System.out.println("Matched: "
                    + numMatches);
            Main.jList1.setListData(v);
            File_Org.jList1.setListData(v);
            finalTotal = numMatches;
            Main.jLabel1.setText("Scan finish.... " + image_Finder.finalTotal + " Number of images found");
            File_Org.jLabel1.setText("Scan finish.... " + image_Finder.finalTotal + " Number of images found");
            finalTotal = numMatches = 0;
            Main.jProgressBar1.setMaximum(100);
            Main.jProgressBar1.setValue(100);
            File_Org.jProgressBar1.setMaximum(100);
            File_Org.jProgressBar1.setValue(100);
        }
        // Invoke the pattern matching
        // method on each file.
        @Override
        public FileVisitResult visitFile(Path file,
                BasicFileAttributes attrs) {
            find(file);
            return CONTINUE;
        }
        // Invoke the pattern matching
        // method on each directory.
        @Override
        public FileVisitResult preVisitDirectory(Path dir,
                BasicFileAttributes attrs) {
            find(dir);
            return CONTINUE;
        }
        @Override
        public FileVisitResult visitFileFailed(Path file,
                IOException exc) {
            //            System.err.println(exc);
            return CONTINUE;
        }
    }
   public static ArrayList<String> get_all_imges_from_all_path() throws IOException {
        resultFileList.clear();//Removes all of the elements from this list. The list will be empty after this call returns.
        File[] paths;
        v.clear();
        paths = File.listRoots();

        //System.out.println(Arrays.toString(paths));
        for (File path : paths) {
            String str = path.toString();
            String slash = "\\";

            String s = new StringBuilder(str).append(slash).toString();
            //System.out.println(str);//C:\
            //System.out.println(s);//C:\\
            Path startingDir = Paths.get(s);

            // String pattern = "*.*";
            //Finder finder = new Finder(pattern);
            Finder finder = new Finder();
            Files.walkFileTree(startingDir, finder);
            finder.done();

        }
        return resultFileList;
    }

    public static ArrayList<String> get_selected_imges_from_all_path(String pattern) throws IOException {
        resultFileList.clear();//Removes all of the elements from this list. The list will be empty after this call returns.
        File[] paths;
        v.clear();
        paths = File.listRoots();

        //System.out.println(Arrays.toString(paths));
        for (File path : paths) {
            String str = path.toString();
            String slash = "\\";

            String s = new StringBuilder(str).append(slash).toString();
            //System.out.println(str);//C:\
            //System.out.println(s);//C:\\
            Path startingDir = Paths.get(s);

            Finder finder = new Finder(pattern);
            Files.walkFileTree(startingDir, finder);
            finder.done();

        }
        return resultFileList;
    }

    public static ArrayList<String> get_all_imges_from_selected_path(String path) throws IOException {
        resultFileList.clear();//Removes all of the elements from this list. The list will be empty after this call returns.
        v.clear();
        Path startingDir = Paths.get(path);

        Finder finder = new Finder();
        Files.walkFileTree(startingDir, finder);
        finder.done();
        return resultFileList;
    }

    public static ArrayList<String> get_selected_imges_from_selected_path(String pattern, String path) throws IOException {
        resultFileList.clear();//Removes all of the elements from this list. The list will be empty after this call returns.
        v.clear();
        Path startingDir = Paths.get(path);

        Finder finder = new Finder(pattern);
        Files.walkFileTree(startingDir, finder);
        finder.done();
        return resultFileList;
    }
}
