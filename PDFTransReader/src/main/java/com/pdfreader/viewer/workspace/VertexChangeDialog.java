/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

import com.pdfreader.data.PDFReaderWorkSpace;
import com.pdfreader.viewer.workspace.ColorSectionPanel.HTMLColor;
import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Trung Pham
 */
public class VertexChangeDialog extends javax.swing.JDialog implements ColorSectionPanel.ColorSelectionListener {

    private static final int[] fontSize = {8, 9, 10, 11, 12, 14, 18, 24, 30, 36, 48, 60, 72, 96};
    private JPopupMenu colorMenu;

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

    private static enum HTMLTag {

        BOLD("<b>", "</b>"),
        ITALIC("<i>", "</i>"),
        UNDERLINE("<u>", "</u>"),
        FONT("<font>", "</font>");
        private String start, end;

        HTMLTag(String start, String end) {
            this.start = start;
            this.end = end;
        }

        public static HTMLTag getTag(String text) {
            for (HTMLTag tag : values()) {
                if (tag.start.equals(text) || tag.end.equals(text)) {
                    return tag;
                }
            }
            return null;
        }
    }
    private PDFReaderWorkSpace.PDFSentenceNode node;
    private int pageNum;

    public VertexChangeDialog(PDFReaderWorkSpace.PDFSentenceNode node, int pageNum) {
        super();
        if (node == null) {
            throw new NullPointerException("Node not found!");
        }
        this.pageNum = pageNum;
        this.node = node;
        initComponents();
        init();
    }

    private void init() {

        colorMenu = new JPopupMenu();


        ColorSectionPanel selection = new ColorSectionPanel(colorMenu);
        selection.addListener(this);
        colorMenu.add(selection);

        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font[] fonts = e.getAllFonts(); // Get the fonts
        DefaultComboBoxModel<String> fontModel = new DefaultComboBoxModel();
        for (Font f : fonts) {
            fontModel.addElement(f.getFontName());
        }
        fontComboBox.setModel(fontModel);
        DefaultComboBoxModel<Integer> sizeModel = new DefaultComboBoxModel();
        for (int i : fontSize) {
            sizeModel.addElement(i);
        }
        sizeComboBox.setModel(sizeModel);

        try {
            convertHtmlToTextStyle(node.getContent());
        } catch (BadLocationException ex) {
            Logger.getLogger(VertexChangeDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (int i = 0; i < pageNum; i++) {
            model.addElement(("" + (i + 1)).intern());
        }
        pageNumComboBox.setModel(model);
        pageNumComboBox.setSelectedItem(("" + node.getPage()).intern());
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
        okButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
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
                                .addGap(0, 421, Short.MAX_VALUE))
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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(jButton2))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        try {
            int num = Integer.parseInt(pageNumComboBox.getSelectedItem().toString());
            node.setPage(num - 1);
        } catch (NumberFormatException ex) {
            return;
        }
        try {
            node.setContent(getTextStyleToHTML());
        } catch (BadLocationException ex) {
            Logger.getLogger(VertexChangeDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }//GEN-LAST:event_okButtonActionPerformed

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
    private String wrapHtmlTag(String text) {
        return "<html>" + text + "</html>";
    }

    private void convertHtmlToTextStyle(String text) throws BadLocationException {

        Stack<String> stack = new Stack();
        Stack<Integer> pos = new Stack();
        StyledDocument doc = contentTextPane.getStyledDocument();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '<') {
                boolean neg = false;
                if (i + 1 < text.length() && text.charAt(i + 1) == '/') {
                    neg = true;
                }
                String cur = "";
                while (text.charAt(i) != '>') {
                    cur += text.charAt(i);
                    i++;
                }
                cur += text.charAt(i);
                if (!neg) {
                    stack.add(cur);
                    pos.add(contentTextPane.getStyledDocument().getLength());
                } else {
                    String tag = stack.pop();

                    //Check tag by using closing tag is more easy.
                    HTMLTag t = HTMLTag.getTag(cur);
                    int start = pos.pop();
                    if (t != null) {

                        SimpleAttributeSet att = new SimpleAttributeSet();
                        switch (t) {
                            case BOLD:
                                StyleConstants.setBold(att, true);
                                break;
                            case ITALIC:
                                StyleConstants.setItalic(att, true);
                                break;
                            case UNDERLINE:
                                StyleConstants.setUnderline(att, true);
                                break;
                            case FONT:
                                setFont(cur, att);
                                break;
                        }
                        doc.setCharacterAttributes(start, doc.getLength() - start, att, true);

                    }
                }
            } else {
                doc.insertString(doc.getLength(), "" + text.charAt(i), null);
            }
        }
    }

    private void setFont(String tag, MutableAttributeSet att) {

        String[] tmp = tag.split(" ");
        for (String value : tmp) {
            if (value.startsWith("face")) {
                String font = value.split("=")[1];
                font = font.substring(1, font.length() - 1);
                System.out.println(font);
                StyleConstants.setFontFamily(att, font);                
            }else if(value.startsWith("color")){
                String color = value.split("=")[1];
                color = color.substring(1, color.length() - 1);
                System.out.println(color);
                HTMLColor c = HTMLColor.getColor(color);
                StyleConstants.setForeground(att, new Color(c.getNumber()));
            }else if(value.startsWith("size")){
                String size = value.split("=")[1];
                int s = Integer.parseInt(size);
                StyleConstants.setFontSize(att, s*11/3);
            }
        }

    }

    private String getTextStyleToHTML() throws BadLocationException {
        StringBuilder builder = new StringBuilder();
        StyledDocument doc = contentTextPane.getStyledDocument();
        for (int i = 0; i < doc.getLength(); i++) {
            Element element = doc.getCharacterElement(i);
            StringBuilder tmp = new StringBuilder();
            AttributeSet set = element.getAttributes();
            builder.append(getTextStyleToHTML(doc.getText(i, 1), set));
        }
        return builder.toString();
    }

    private String getTextStyleToHTML(String text, AttributeSet set) {
        StringBuilder prefix = new StringBuilder();
        Stack<String> stack = new Stack();
        if (StyleConstants.isBold(set)) {
            prefix.append(HTMLTag.BOLD.start);
            stack.push(HTMLTag.BOLD.end);
        }
        if (StyleConstants.isItalic(set)) {
            prefix.append(HTMLTag.ITALIC.start);
            stack.push(HTMLTag.ITALIC.end);
        }
        if (StyleConstants.isUnderline(set)) {
            prefix.append(HTMLTag.UNDERLINE.start);
            stack.push(HTMLTag.UNDERLINE.end);
        }

        prefix.append(getOpenFontTag(set));
        stack.push(HTMLTag.FONT.end);



        prefix.append(text);
        while (!stack.isEmpty()) {
            prefix.append(stack.pop());
        }
        return prefix.toString();
    }

    private String getOpenFontTag(AttributeSet set) {
        String font = StyleConstants.getFontFamily(set);
        int size = StyleConstants.getFontSize(set) * 3 / 11;
        Color c = StyleConstants.getForeground(set);
        String color = ColorSectionPanel.HTMLColor.getColor(c.getRGB() ^ 0xff000000).getName();
        String result = "<font";
        if (font != null) {
            result += " face=\"" + font + "\"";
        }
        if (color != null) {
            result += " color=\"" + color + "\"";
        }
        result += " size=" + size;
        result += ">";
        System.out.println("RESULT " + result);
        return result;
    }

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
            java.util.logging.Logger.getLogger(VertexChangeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VertexChangeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VertexChangeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VertexChangeDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                VertexChangeDialog dialog = new VertexChangeDialog(null, 0);
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
    private javax.swing.JButton boldButton;
    private javax.swing.JButton colorButton;
    private javax.swing.JTextPane contentTextPane;
    private javax.swing.JComboBox fontComboBox;
    private javax.swing.JButton italicButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton okButton;
    private javax.swing.JComboBox pageNumComboBox;
    private javax.swing.JComboBox sizeComboBox;
    private javax.swing.JButton underlineButton;
    // End of variables declaration//GEN-END:variables
}
