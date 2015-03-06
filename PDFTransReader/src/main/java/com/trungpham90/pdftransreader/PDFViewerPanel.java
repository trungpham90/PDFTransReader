/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trungpham90.pdftransreader;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashSet;
import javax.swing.JPanel;
import org.apache.pdfbox.pdfviewer.PDFPagePanel;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Display one PDF page
 *
 * @author Trung Pham
 */
public class PDFViewerPanel extends JPanel implements MouseListener {

    PDFPagePanel panel;
    PDPage page;
    private int x1, y1, x2, y2;
    private HashSet<ViewerSelectionListener> listeners = new HashSet();

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

    public void addListener(ViewerSelectionListener lis) {
        listeners.add(lis);
    }

    public void removeListener(ViewerSelectionListener lis) {
        listeners.remove(lis);
    }

    public void notifyListeners() {
        for (ViewerSelectionListener lis : listeners) {
            int minX = Math.min(x1, x2);
            int minY = Math.min(y1, y2);
            int maxX = Math.max(x1, x2);
            int maxY = Math.max(y1, y2);
            lis.selectionTrigger(minX, minY, maxX, maxY);
        }
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


        notifyListeners();

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    /**
     * Inteface, allow this panel to trigger action when some actions are triggered
     */
    static interface ViewerSelectionListener {

        public void selectionTrigger(int x1, int y1, int x2, int y2);
    }
}
