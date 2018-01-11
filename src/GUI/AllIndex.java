/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import CLS_Main.image_Finder;
import CLS_Main.image_data_to_database;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.quartz.core.jmx.QuartzSchedulerMBean;

/**
 *
 * @author Isuru
 */
public class AllIndex extends javax.swing.JFrame {

    /**
     * Creates new form AllIndex
     */
    public AllIndex() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Index All My Computer Images");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALL" }));
        jPanel1.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 80, 30));

        jLabel1.setText("Select which driver to index:-");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 30));

        jButton1.setText("Refresh List of Drivers");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, -1, 30));

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton2.setText("Index");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 380, 70));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 170));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        File[] f = File.listRoots();
        ArrayList<String> path_Arr_List = new ArrayList();
        path_Arr_List.add("ALL");
        for (File f1 : f) {
            if (f1.canRead()) {
                path_Arr_List.add(f1.getPath());
            }
        }
        String[] paths = new String[path_Arr_List.size()];
        for (int i = 0; i < path_Arr_List.size(); i++) {
            paths[i] = path_Arr_List.get(i);
        }
        jComboBox1.setModel(new DefaultComboBoxModel(paths));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int con = JOptionPane.showConfirmDialog(rootPane, "Are you sure? \nThis prosess takes to much time..", "All Index", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (con == 0) {
            ArrayList<String> all_imges_from_all_path = null;
            if (jComboBox1.getSelectedItem().equals("ALL") || jComboBox1.getSelectedItem().equals("")) {
                try {
                    all_imges_from_all_path = image_Finder.get_all_imges_from_all_path();
                } catch (IOException ex) {
                    Logger.getLogger(AllIndex.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    all_imges_from_all_path = image_Finder.get_all_imges_from_selected_path(jComboBox1.getSelectedItem().toString());
                } catch (IOException ex) {
                    Logger.getLogger(AllIndex.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                if(image_data_to_database.set_big_DB(all_imges_from_all_path)){
                JOptionPane.showMessageDialog(rootPane, "Indexing is completed", "Index", JOptionPane.INFORMATION_MESSAGE);
                }else{
                JOptionPane.showMessageDialog(rootPane, "Indexing is faults", "Index", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (ParseException | SQLException ex) {
                Logger.getLogger(AllIndex.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AllIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AllIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AllIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AllIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AllIndex().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
