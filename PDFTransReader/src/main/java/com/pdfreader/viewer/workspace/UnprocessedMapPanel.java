/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

import com.pdfreader.data.PDFReaderWorkSpace;
import com.pdfreader.util.HTMLHelper;
import com.pdfreader.util.HTMLHelper.HTMLColor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Trung Pham
 */
public class UnprocessedMapPanel extends javax.swing.JPanel implements ColorSectionPanel.ColorSelectionListener {

    @Override
    public void colorSelection(boolean text, HTMLColor color) {
        int start = contentTextPane.getSelectionStart();
        int end = contentTextPane.getSelectionEnd();
        // System.out.println("SELECTION " + contentTextPane.getText().substring(start, end + 1));    
        StyledDocument doc = contentTextPane.getStyledDocument();
        for (int i = start; i < end; i++) {
            Element e = doc.getCharacterElement(i);

            SimpleAttributeSet att = new SimpleAttributeSet(e.getAttributes());
            if (!text) {
                StyleConstants.setBackground(att, new Color(color.getNumber()));
            } else {
                StyleConstants.setForeground(att, new Color(color.getNumber()));
            }

            doc.setCharacterAttributes(i, 1, att, true);
        }

    }

    public static interface UnprocessedMapPanelListener {

        public void addToWorkSpace(PDFReaderWorkSpace.PDFUnprocessText txt);

        public void removeFromWorkSpace(PDFReaderWorkSpace.PDFUnprocessText txt);
    }
    private HashSet<UnprocessedMapPanelListener> lis = new HashSet();
    private JPopupMenu colorMenu;
    /**
     * Creates new form UnprocessedMapPanel
     */
    ArrayList<PDFReaderWorkSpace.PDFUnprocessText> unprocessText = new ArrayList();
    private int currentItem = 0;

    public UnprocessedMapPanel() {
        initComponents();
        init();
    }

    private void init() {
        editText(false);
        colorMenu = new JPopupMenu();
        ColorSectionPanel selection = new ColorSectionPanel(colorMenu);
        selection.addListener(this);
        colorMenu.add(selection);

    }

    public void addListener(UnprocessedMapPanelListener l) {
        lis.add(l);
    }

    public void addUnprocessedText(PDFReaderWorkSpace.PDFUnprocessText text) {
        if (!unprocessText.contains(text)) {
            unprocessText.add(text);
            totalUnprocessLabel.setText("/" + unprocessText.size());
        }
        if (unprocessText.size() == 1) {
            loadUnprocessText(currentItem);
        }
    }

    public void removeUnprocssedText(PDFReaderWorkSpace.PDFUnprocessText text) throws BadLocationException {
        int index = unprocessText.indexOf(text);
        if (index >= 0) {
            unprocessText.remove(index);
            if (!unprocessText.isEmpty()) {
                if (currentItem < unprocessText.size()) {
                    loadUnprocessText(currentItem);
                } else {
                    if (currentItem > 0) {
                        currentItem--;
                        loadUnprocessText(currentItem);
                    } else {
                        loadUnprocessText(currentItem);
                    }
                }
            } else {
                clearEditScreen();
            }
        }
    }

    private void clearEditScreen() throws BadLocationException {
        contentTextPane.getStyledDocument().remove(0, contentTextPane.getStyledDocument().getLength());
        currentTextField.setText("0");
        totalUnprocessLabel.setText("/0");
    }

    public void editText(boolean val) {
        contentTextPane.setEnabled(val);
        boldButton.setEnabled(val);
        italicButton.setEnabled(val);
        underlineButton.setEnabled(val);
        colorButton.setEnabled(val);
        fontComboBox.setEnabled(val);
        sizeComboBox.setEnabled(val);
        nextButton.setEnabled(!val);
        backButton.setEnabled(!val);
        addButton.setEnabled(!val);
        removeButton.setEnabled(!val);
        currentTextField.setEnabled(!val);
    }

    private void loadUnprocessText(int index) {
        if (index >= unprocessText.size() || index < 0) {
            throw new NullPointerException("Invalid index when loading unprocess text!");
        }
        currentItem = index;
        load(unprocessText.get(currentItem));
        currentTextField.setText("" + (1 + currentItem));
        totalUnprocessLabel.setText("/" + unprocessText.size());
    }

    private void load(PDFReaderWorkSpace.PDFUnprocessText textNode) {
        contentTextPane.setText("");
        try {
            HTMLHelper.convertHtmlToTextStyle(textNode.getContent(), contentTextPane.getStyledDocument());
        } catch (BadLocationException ex) {
            Logger.getLogger(UnprocessedMapPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        currentTextField = new javax.swing.JTextField();
        totalUnprocessLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pageNumComboBox = new javax.swing.JComboBox();
        boldButton = new javax.swing.JButton();
        italicButton = new javax.swing.JButton();
        underlineButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        contentTextPane = new javax.swing.JTextPane();
        fontComboBox = new javax.swing.JComboBox();
        sizeComboBox = new javax.swing.JComboBox();
        colorButton = new javax.swing.JButton();
        editToggleButton = new javax.swing.JToggleButton();

        setBackground(new java.awt.Color(255, 255, 255));

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        removeButton.setText("Remove");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        backButton.setText("<-");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        nextButton.setText("->");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        currentTextField.setText("  ");
        currentTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                currentTextFieldActionPerformed(evt);
            }
        });

        totalUnprocessLabel.setText("/ 0");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Content:");

        jLabel2.setText("Page:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Edit");

        pageNumComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        boldButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        boldButton.setText("B");
        boldButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boldButtonActionPerformed(evt);
            }
        });

        italicButton.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        italicButton.setText("I");
        italicButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                italicButtonActionPerformed(evt);
            }
        });

        underlineButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        underlineButton.setText("U");
        underlineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                underlineButtonActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(contentTextPane);

        fontComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        fontComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontComboBoxActionPerformed(evt);
            }
        });

        sizeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        sizeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sizeComboBoxActionPerformed(evt);
            }
        });

        colorButton.setText("A");
        colorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pageNumComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(boldButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(italicButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(underlineButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fontComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(colorButton)
                                .addGap(0, 544, Short.MAX_VALUE))
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boldButton)
                    .addComponent(italicButton)
                    .addComponent(underlineButton)
                    .addComponent(fontComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pageNumComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(50, 50, 50))
        );

        editToggleButton.setText("Edit");
        editToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editToggleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nextButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(currentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalUnprocessLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 588, Short.MAX_VALUE)
                .addComponent(editToggleButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(removeButton)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(removeButton)
                    .addComponent(nextButton)
                    .addComponent(backButton)
                    .addComponent(currentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalUnprocessLabel)
                    .addComponent(editToggleButton))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 65, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void currentTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_currentTextFieldActionPerformed
    }//GEN-LAST:event_currentTextFieldActionPerformed

    private void boldButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boldButtonActionPerformed
        int start = contentTextPane.getSelectionStart();
        int end = contentTextPane.getSelectionEnd();
        // System.out.println("SELECTION " + contentTextPane.getText().substring(start, end + 1));
        boolean notBold = false;
        StyledDocument doc = contentTextPane.getStyledDocument();
        for (int i = start; i < end; i++) {
            Element e = doc.getCharacterElement(i);
            if (!StyleConstants.isBold(e.getAttributes())) {
                notBold = true;
                break;
            }
        }
        if (notBold) {
            for (int i = start; i < end; i++) {
                Element e = doc.getCharacterElement(i);

                SimpleAttributeSet att = new SimpleAttributeSet(e.getAttributes());
                StyleConstants.setBold(att, true);
                doc.setCharacterAttributes(i, 1, att, true);
            }
        } else {
            for (int i = start; i < end; i++) {
                Element e = doc.getCharacterElement(i);
                SimpleAttributeSet att = new SimpleAttributeSet(e.getAttributes());
                StyleConstants.setBold(att, false);
                doc.setCharacterAttributes(i, 1, att, true);
            }
        }
    }//GEN-LAST:event_boldButtonActionPerformed

    private void italicButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_italicButtonActionPerformed
        int start = contentTextPane.getSelectionStart();
        int end = contentTextPane.getSelectionEnd();
        boolean notItalic = false;
        StyledDocument doc = contentTextPane.getStyledDocument();
        for (int i = start; i < end; i++) {
            Element e = doc.getCharacterElement(i);
            if (!StyleConstants.isItalic(e.getAttributes())) {
                notItalic = true;
                break;
            }
        }
        if (notItalic) {
            for (int i = start; i < end; i++) {
                Element e = doc.getCharacterElement(i);

                SimpleAttributeSet att = new SimpleAttributeSet(e.getAttributes());
                StyleConstants.setItalic(att, true);
                doc.setCharacterAttributes(i, 1, att, true);
            }
        } else {
            for (int i = start; i < end; i++) {
                Element e = doc.getCharacterElement(i);
                SimpleAttributeSet att = new SimpleAttributeSet(e.getAttributes());
                StyleConstants.setItalic(att, false);
                doc.setCharacterAttributes(i, 1, att, true);
            }
        }
    }//GEN-LAST:event_italicButtonActionPerformed

    private void underlineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_underlineButtonActionPerformed
        int start = contentTextPane.getSelectionStart();
        int end = contentTextPane.getSelectionEnd();
        // System.out.println("SELECTION " + contentTextPane.getText().substring(start, end + 1));
        boolean notUnderline = false;
        StyledDocument doc = contentTextPane.getStyledDocument();
        for (int i = start; i < end; i++) {
            Element e = doc.getCharacterElement(i);
            if (!StyleConstants.isUnderline(e.getAttributes())) {
                notUnderline = true;
                break;
            }
        }
        if (notUnderline) {
            for (int i = start; i < end; i++) {
                Element e = doc.getCharacterElement(i);

                SimpleAttributeSet att = new SimpleAttributeSet(e.getAttributes());
                StyleConstants.setUnderline(att, true);
                doc.setCharacterAttributes(i, 1, att, true);
            }
        } else {
            for (int i = start; i < end; i++) {
                Element e = doc.getCharacterElement(i);
                SimpleAttributeSet att = new SimpleAttributeSet(e.getAttributes());
                StyleConstants.setUnderline(att, false);
                doc.setCharacterAttributes(i, 1, att, true);
            }
        }
    }//GEN-LAST:event_underlineButtonActionPerformed

    private void fontComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontComboBoxActionPerformed
        int start = contentTextPane.getSelectionStart();
        int end = contentTextPane.getSelectionEnd();
        String font = fontComboBox.getSelectedItem().toString();
        StyledDocument doc = contentTextPane.getStyledDocument();
        for (int i = start; i < end; i++) {
            Element e = doc.getCharacterElement(i);
            SimpleAttributeSet att = new SimpleAttributeSet(e.getAttributes());
            StyleConstants.setFontFamily(att, font);
            doc.setCharacterAttributes(i, 1, att, true);
        }
    }//GEN-LAST:event_fontComboBoxActionPerformed

    private void sizeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeComboBoxActionPerformed
        int start = contentTextPane.getSelectionStart();
        int end = contentTextPane.getSelectionEnd();
        int size = Integer.parseInt(sizeComboBox.getSelectedItem().toString());
        StyledDocument doc = contentTextPane.getStyledDocument();
        for (int i = start; i < end; i++) {
            Element e = doc.getCharacterElement(i);
            SimpleAttributeSet att = new SimpleAttributeSet(e.getAttributes());
            StyleConstants.setFontSize(att, size);
            doc.setCharacterAttributes(i, 1, att, true);
        }

    }//GEN-LAST:event_sizeComboBoxActionPerformed

    private void colorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorButtonActionPerformed

        JButton button = (JButton) evt.getSource();
        colorMenu.show(button, 0, button.getHeight());

    }//GEN-LAST:event_colorButtonActionPerformed

    private void editToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editToggleButtonActionPerformed
        if (editToggleButton.isSelected()) {
            editText(true);
        } else {
            editText(false);
        }

    }//GEN-LAST:event_editToggleButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        if (currentItem + 1 < unprocessText.size()) {
            currentItem++;
            loadUnprocessText(currentItem);
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed

        if (currentItem - 1 >= 0) {
            currentItem--;
            loadUnprocessText(currentItem);
        }


    }//GEN-LAST:event_backButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed

        for (UnprocessedMapPanelListener l : lis) {
            l.addToWorkSpace(unprocessText.get(currentItem));
        }

    }//GEN-LAST:event_addButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed

        for (UnprocessedMapPanelListener l : lis) {
            l.removeFromWorkSpace(unprocessText.get(currentItem));
        }

    }//GEN-LAST:event_removeButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton backButton;
    private javax.swing.JButton boldButton;
    private javax.swing.JButton colorButton;
    private javax.swing.JTextPane contentTextPane;
    private javax.swing.JTextField currentTextField;
    private javax.swing.JToggleButton editToggleButton;
    private javax.swing.JComboBox fontComboBox;
    private javax.swing.JButton italicButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton nextButton;
    private javax.swing.JComboBox pageNumComboBox;
    private javax.swing.JButton removeButton;
    private javax.swing.JComboBox sizeComboBox;
    private javax.swing.JLabel totalUnprocessLabel;
    private javax.swing.JButton underlineButton;
    // End of variables declaration//GEN-END:variables
}
