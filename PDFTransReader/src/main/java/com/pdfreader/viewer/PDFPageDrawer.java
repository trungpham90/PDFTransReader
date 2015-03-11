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
    private Graphics graphics;
    private Color highlightColor = Color.YELLOW;
    private boolean start = false, process = false;
    private List<PDFWord> list = new ArrayList();

    public PDFPageDrawer() throws IOException {
        super();
    }

    public void setHighlight(List<PDFWord> list) {
        process = true;
        this.list = new ArrayList(list);

        System.out.println("Set Hight light");

    }

    public void isHighLight(boolean on) {
        list.clear();
        process = on;
    }

    @Override
    public void drawPage(Graphics g, PDPage p, Dimension pageDimension) throws IOException {

        graphics = g;
        //System.out.println(process + " " + list.size());
        if (!list.isEmpty()) {
            Color origin = graphics.getColor();
            graphics.setColor(highlightColor);
            for (PDFWord rect : list) {
                //System.out.println("Rect " + rect);
                graphics.fillRect((int) rect.getX(), (int) rect.getY(), (int) Math.ceil(rect.getWidth()), (int) Math.ceil(rect.getHeight()));
            }
            graphics.setColor(origin);
        }
        super.drawPage(g, p, pageDimension);
    }
}
