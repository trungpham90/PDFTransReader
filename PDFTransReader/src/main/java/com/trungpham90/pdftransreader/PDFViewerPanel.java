/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trungpham90.pdftransreader;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import org.apache.pdfbox.pdfviewer.PDFPagePanel;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripperByArea;

/**
 * Display one PDF page
 *
 * @author Trung Pham
 */
public class PDFViewerPanel extends JPanel implements MouseListener {

    PDFPagePanel panel;
    PDPage page;
    private int x1, y1, x2, y2;

    public PDFViewerPanel() throws IOException {
        super();
        this.setLayout(new BorderLayout());
        panel = new PDFPagePanel();
        panel.addMouseListener(this);
        this.add(panel, BorderLayout.CENTER);
    }

    public void setPage(PDPage page) {
        this.page = page;
        panel.setPage(page);

        panel.setPreferredSize(panel.getSize());
        revalidate();
        repaint();
    }

    public String getSelectedString(int x1, int y1, int x2, int y2) {
        try {
            PDFTextStripperByArea area = new PDFTextStripperByArea();
            area.setSpacingTolerance(0.2f);
            Rectangle rect = new Rectangle(x1, y1, x2 - x1, Math.max(10, y2 - y1));
            area.addRegion("text", rect);
            area.extractRegions(page);

            return area.getTextForRegion("text");
        } catch (IOException ex) {
            Logger.getLogger(PDFViewerPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();


        String v = getSelectedString(x1, y1, x2, y2);
        System.out.println(v);
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
