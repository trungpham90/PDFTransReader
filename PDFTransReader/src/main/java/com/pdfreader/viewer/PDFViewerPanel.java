/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer;

import com.pdfreader.reader.PDFWord;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import javax.swing.JPanel;
import org.apache.pdfbox.pdfviewer.PDFPagePanel;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * Display one PDF page
 *
 * @author Trung Pham
 */
public class PDFViewerPanel extends JPanel implements MouseListener, MouseMotionListener {

    PDFPanel panel;
    PDPage page;
    private int x1, y1, x2, y2;
    private HashSet<ViewerSelectionListener> listeners = new HashSet();

    public PDFViewerPanel() throws IOException {
        super();
        this.setLayout(new BorderLayout());
        panel = new PDFPanel();
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
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

    public void notifySelectionListeners() {

        for (ViewerSelectionListener lis : listeners) {
            int minX = Math.min(x1, x2);
            int minY = Math.min(y1, y2);
            int maxX = Math.max(x1, x2);
            int maxY = Math.max(y1, y2);
            lis.selectionTrigger(minX, minY, maxX, maxY);
        }
    }

    public void notifyDoubleClickListeners(int x, int y) {

        for (ViewerSelectionListener lis : listeners) {

            lis.doubleClickTrigger(x, y);

        }
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() > 1) {

            notifyDoubleClickListeners(e.getX(), e.getY());

        }
    }

    public void setHighLight(List<PDFWord> list) {
        System.out.println("Set " + x1 + " " + y1 + " " + x2 + " " + y2);
        panel.setHightlightArea(list);
        panel.repaint();
    }

    public void mousePressed(MouseEvent e) {

        x1 = e.getX();
        y1 = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        if (x1 != x2 || y1 != y2) {
            notifySelectionListeners();
        }

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        if (x1 != x2 || y1 != y2) {
            notifySelectionListeners();
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    /**
     * Inteface, allow this panel to trigger action when some actions are done
     * on the page.
     */
    public static interface ViewerSelectionListener {

        public void selectionTrigger(int x1, int y1, int x2, int y2);

        public void doubleClickTrigger(int x, int y);
    }
}
