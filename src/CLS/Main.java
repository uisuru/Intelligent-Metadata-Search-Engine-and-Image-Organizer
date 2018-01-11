/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CLS;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.filechooser.FileSystemView;

public class Main {

    public static void main(String[] args) {
        File[] f = File.listRoots();
        ArrayList<String> path = new ArrayList();
        path.add("ALL");
        for (File f1 : f) {
            if (f1.canRead()) {
                path.add(f1.getPath());
            }
        }
        for (String path1 : path) {
            System.out.println(path1);
        }
    }
}
