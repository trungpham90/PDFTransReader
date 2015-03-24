/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader;

import com.pdfreader.dic.DicManager;
import com.pdfreader.dic.DicVO;
import com.pdfreader.dic.IDic;
import com.pdfreader.reader.PDFFileReader;
import com.pdfreader.reader.PDFWord;
import com.pdfreader.viewer.PDFDicDialog;
import com.pdfreader.viewer.PDFViewerPanel;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import org.apache.pdfbox.util.ExtensionFileFilter;

/**
 *
 * @author Trung Pham
 */
public class DisplayDicPanel extends javax.swing.JPanel {

    private PDFDicDialog dicDialog;
    private PDFFileReader reader;
    private PDFViewerPanel panel;
    private int page = 0;
    private int totalPage = 0;
    private IDic dic;

    /**
     * Creates new form DisplayDicPanel
     */
    public DisplayDicPanel(java.awt.Frame parent) {
        dicDialog = new PDFDicDialog(parent, false);
        initComponents();
        init();
    }

    private void init() {
        languageComboBox.setModel(new DefaultComboBoxModel(DicManager.Language.values()));
        languageComboBox.setSelectedItem(DicManager.Language.English);
        languageComboBox.revalidate();
        dic = DicManager.getDictionary(DicManager.Language.English);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        leftPanel = new javax.swing.JPanel();
        rightPanel = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        languageComboBox = new javax.swing.JComboBox();
        showToggleButton = new javax.swing.JToggleButton();
        openButton = new javax.swing.JButton();
        pageTextField = new javax.swing.JTextField();
        totalPageLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jSplitPane1.setDividerLocation(800);

        leftPanel.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setLeftComponent(leftPanel);

        javax.swing.GroupLayout rightPanelLayout = new javax.swing.GroupLayout(rightPanel);
        rightPanel.setLayout(rightPanelLayout);
        rightPanelLayout.setHorizontalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 156, Short.MAX_VALUE)
        );
        rightPanelLayout.setVerticalGroup(
            rightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 628, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(rightPanel);

        searchTextField.setText(" ");

        languageComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        languageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                languageComboBoxActionPerformed(evt);
            }
        });

        showToggleButton.setText("jToggleButton1");
        showToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showToggleButtonActionPerformed(evt);
            }
        });

        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        pageTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pageTextField.setText("0");
        pageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pageTextFieldActionPerformed(evt);
            }
        });

        totalPageLabel.setText("/ 0");

        jButton1.setText("<-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("->");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
            .addGroup(layout.createSequentialGroup()
                .addComponent(openButton)
                .addGap(46, 46, 46)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalPageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 301, Short.MAX_VALUE)
                .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showToggleButton)
                    .addComponent(openButton)
                    .addComponent(pageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalPageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void languageComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_languageComboBoxActionPerformed
        dic = DicManager.getDictionary(DicManager.Language.valueOf(languageComboBox.getSelectedItem().toString()));
    }//GEN-LAST:event_languageComboBoxActionPerformed

    private void showToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showToggleButtonActionPerformed
        if (showToggleButton.isSelected()) {
        } else {
        }
    }//GEN-LAST:event_showToggleButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed

        JFileChooser chooser = new JFileChooser();
        ExtensionFileFilter pdfFilter = new ExtensionFileFilter(new String[]{"PDF"}, "PDF Files");
        chooser.setFileFilter(pdfFilter);
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

                reader = new PDFFileReader(file);
                panel = new PDFViewerPanel();

                page = 0;
                totalPage = reader.getNumPages();
                totalPageLabel.setText("/ " + totalPage);
                pageTextField.setText("1");
                panel.addListener(new PDFViewerPanel.ViewerSelectionListener() {
                    @Override
                    public void selectionTrigger(int x1, int y1, int x2, int y2) {
                        try {
                            List<PDFWord> list = reader.getStringAt(page, x1, y1, x2, y2);
                            panel.setHighLight(list);
                            panel.repaint();
                        } catch (IOException ex) {
                        }
                    }

                    @Override
                    public void doubleClickTrigger(int x, int y) {
                        try {
                            PDFWord word = reader.getWordAt(page, x, y);
                            if (word != null) {
                                DicVO content = dic.getWordDefinition(word.getWord());
                                if (content != null) {
                                    dicDialog.setContent(content);

                                    dicDialog.setLocation(MouseInfo.getPointerInfo().getLocation());
                                    dicDialog.setVisible(true);
                                }
                                List<PDFWord> list = new ArrayList();
                                list.add(word);
                                panel.setHighLight(list);
                                panel.repaint();
                            }

                        } catch (IOException ex) {
                        }
                    }
                });
                panel.setPage(reader.getPage(page++));
                leftPanel.removeAll();
                JScrollPane scroll = new JScrollPane(panel);
                scroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);//Improve the performance significantly!

                leftPanel.add(scroll, BorderLayout.CENTER);
                leftPanel.revalidate();
                leftPanel.repaint();
                this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            } catch (IOException ex) {
            }

        }
    }//GEN-LAST:event_openButtonActionPerformed

    private void pageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pageTextFieldActionPerformed
    }//GEN-LAST:event_pageTextFieldActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (page > 0) {
            try {
                panel.setPage(reader.getPage(--page));
                pageTextField.setText("" + (page + 1));
            } catch (IOException ex) {
                Logger.getLogger(DisplayDicPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (page + 1 < totalPage) {
            try {
                panel.setPage(reader.getPage(++page));
                pageTextField.setText("" + (page + 1));
            } catch (IOException ex) {
                Logger.getLogger(DisplayDicPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JComboBox languageComboBox;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton openButton;
    private javax.swing.JTextField pageTextField;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JToggleButton showToggleButton;
    private javax.swing.JLabel totalPageLabel;
    // End of variables declaration//GEN-END:variables
}
