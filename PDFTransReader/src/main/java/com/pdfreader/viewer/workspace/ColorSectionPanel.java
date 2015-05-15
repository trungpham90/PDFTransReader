/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

import com.pdfreader.util.ColorIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Trung Pham
 */
public class ColorSectionPanel extends javax.swing.JPanel {

    /**
     * Creates new form ColorSection
     */
    HashSet<ColorSelectionListener> listeners = new HashSet();
    JPopupMenu parent;

    public ColorSectionPanel(JPopupMenu parent) {
        this.parent = parent;
        initComponents();
        init();
    }

    private void init() {
        colorPanel.setLayout(new BorderLayout(0, 0));
        textRadioButton.setSelected(true);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);       
        
        GridBagConstraints c = new GridBagConstraints();
        
        
        int x = 0;
        int y = 0;
        
        JButton[] buttons = new JButton[HTMLColor.values().length];
        int index = 0;
        for (final HTMLColor color : HTMLColor.values()) {
            buttons[index] = new JButton();
            ColorIcon icon = new ColorIcon(30, 30, new Color(color.number));
            buttons[index].setIcon(icon);           
            buttons[index].setMargin(new Insets(0, 0, 0, 0));
            buttons[index].setContentAreaFilled(false);
            buttons[index].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    notifyListeners(textRadioButton.isSelected(), color);
                    parent.setVisible(false);
                }
            });

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = x++;
            c.gridy = y;
            if( x == 3){
                x = 0;                
                y++;               
            }
            buttonPanel.add(buttons[index], c);
            index++;
        }
        colorPanel.add(buttonPanel , BorderLayout.CENTER);
    }

    public void addListener(ColorSelectionListener lis) {
        listeners.add(lis);
    }

    public void notifyListeners(boolean text, HTMLColor color) {
        for (ColorSelectionListener lis : listeners) {
            lis.colorSelection(text, color);
        }
    }

    public static enum HTMLColor {

        RED("Red", 0xFF0000),
        CYAN("Cyan", 0x00FFFF),
        BLUE("Blue", 0x0000FF),
        DARKBLUE("DarkBlue", 0x0000A0),
        LIGHTBLUE("LightBlue", 0xADD8E6),
        PURPLE("Purple", 0x800080),
        YELLOW("Yellow", 0xFFFF00),
        LIME("Lime", 0x00FF00),
        MAGENTA("Magenta", 0xFF00FF),
        WHITE("White", 0xFFFFFF),
        SILVER("Silver", 0xC0C0C0),
        GRAY("Gray", 0x808080),
        BLACK("Black", 0x000000),
        ORANGE("Orange", 0xFFA500),
        BROWN("Brown", 0xA52A2A),
        MAROON("Maroon", 0x800000),
        GREEN("Green", 0x008000),
        OLIVE("Olive", 0x808000);
        private String name;
        private int number;

        HTMLColor(String name, int number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public int getNumber() {
            return number;
        }

        public static HTMLColor getColor(int number) {
            for (HTMLColor c : values()) {               
                if (c.number == number) {
                    return c;
                }
            }
            return null;
        }

        public static HTMLColor getColor(String name) {
            for (HTMLColor c : values()) {                
                if (c.name.equals(name)) {
                    return c;
                }
            }
            return null;
        }
    }

    public static interface ColorSelectionListener {

        public void colorSelection(boolean text, HTMLColor color);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        textRadioButton = new javax.swing.JRadioButton();
        highlightRadioButton = new javax.swing.JRadioButton();
        colorPanel = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        textRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(textRadioButton);
        textRadioButton.setText("Text");
        textRadioButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        highlightRadioButton.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(highlightRadioButton);
        highlightRadioButton.setText("Hightlight");
        highlightRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highlightRadioButtonActionPerformed(evt);
            }
        });

        colorPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(textRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(highlightRadioButton))
            .addComponent(colorPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textRadioButton)
                    .addComponent(highlightRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void highlightRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highlightRadioButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_highlightRadioButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel colorPanel;
    private javax.swing.JRadioButton highlightRadioButton;
    private javax.swing.JRadioButton textRadioButton;
    // End of variables declaration//GEN-END:variables
}
