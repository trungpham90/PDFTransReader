/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trungpham90.pdftransreader;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JPanel;
import org.apache.pdfbox.pdfviewer.PDFPagePanel;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Display one PDF page
 * @author Trung Pham
 */
public class PDFViewerPanel extends JPanel {

    PDFPagePanel panel;

    public PDFViewerPanel() throws IOException {
        super();
        this.setLayout(new BorderLayout());
        panel = new PDFPagePanel();
        this.add(panel, BorderLayout.CENTER);
    }

    public void setPage(PDPage page) {
        panel.setPage(page);
        panel.setPreferredSize(panel.getSize());
        revalidate();
        repaint();
    }
}
