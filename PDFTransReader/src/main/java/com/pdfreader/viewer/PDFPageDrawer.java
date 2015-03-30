/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer;

import com.pdfreader.reader.PDFWord;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdfviewer.PageDrawer;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 *
 * @author Trung Pham
 */
public class PDFPageDrawer extends PageDrawer {

    private int x1, y1, x2, y2;
    private float firstX = -1, firstY = -1;
    private Color highlightColor = Color.YELLOW;
    private boolean start = false;
    private List<PDFWord> list = new ArrayList();

    public PDFPageDrawer() throws IOException {
        super();
    }

    public void setHighlight(List<PDFWord> list) {

        this.list = new ArrayList(list);
    }

    public void clearHighLight() {
        list.clear();
    }

    @Override
    public void drawPage(Graphics g, PDPage p, Dimension pageDimension) throws IOException {
        if (!list.isEmpty()) {
            Color origin = g.getColor();
            g.setColor(highlightColor);
            for (PDFWord rect : list) {
                g.fillRect((int) rect.getX(), (int) rect.getY(), (int) Math.ceil(rect.getWidth()), (int) Math.ceil(rect.getHeight()));
            }
            g.setColor(origin);
        }
        super.drawPage(g, p, pageDimension);
    }
}
