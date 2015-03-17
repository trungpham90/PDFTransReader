/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer;

import com.pdfreader.dic.DicVO;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Scanner;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit;

/**
 *
 * @author Trung Pham
 */
public class PDFDicDialog extends javax.swing.JDialog {

    /**
     * Creates new form PDFDicDialog
     */
    public PDFDicDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setUndecorated(true);
        initComponents();
        init();
    }

    private void init() {
        dicPane.setContentType("text/html");
        addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {
            }

            public void windowLostFocus(WindowEvent e) {
                setVisible(false);
            }
        });
    }

    public void setContent(DicVO content) {
        StringBuilder builder = new StringBuilder();
        Scanner scanner = new Scanner(content.getContent());
        wordLabel.setText(content.getWord());
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            builder.append(line).append("<br>");

        }
        dicPane.setText(builder.toString());
        dicPane.validate();
        dicPane.repaint();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMinimum());
                scrollPane.getHorizontalScrollBar().setValue(scrollPane.getHorizontalScrollBar().getMinimum());
            }
        });

    }

    public void clear() {
        dicPane.removeAll();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        dicPane = new javax.swing.JTextPane();
        wordLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        dicPane.setBackground(new java.awt.Color(204, 204, 255));
        scrollPane.setViewportView(dicPane);

        wordLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        wordLabel.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(wordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(wordLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(PDFDicDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PDFDicDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PDFDicDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PDFDicDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PDFDicDialog dialog = new PDFDicDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane dicPane;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JLabel wordLabel;
    // End of variables declaration//GEN-END:variables
}
