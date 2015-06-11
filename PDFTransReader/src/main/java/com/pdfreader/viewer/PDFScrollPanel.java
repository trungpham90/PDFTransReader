/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer;

import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author Trung Pham
 */
public class PDFScrollPanel extends JPanel {
    private JPanel mainPanel;
    
    int page;
    public PDFScrollPanel(int page){
        this.page = page;
    }
    
    private void init(){
        setLayout(new BorderLayout());
        mainPanel = new JPanel();
        
    }
            
}
