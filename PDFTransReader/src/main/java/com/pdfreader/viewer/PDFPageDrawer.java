/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer;

import com.pdfreader.util.MatchedCharacterUtil;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdfviewer.PageDrawer;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.TextPosition;

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
    private ArrayList<Rectangle> list = new ArrayList();

    public PDFPageDrawer() throws IOException {
        super();
    }

    public void setHighlight(int x1, int y1, int x2, int y2) {
        process = true;
        firstX = -1;
        firstY = -1;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        list.clear();
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
            for (Rectangle rect : list) {
                //System.out.println("Rect " + rect);
                graphics.fillRect(rect.x, rect.y, rect.width, rect.height);
            }
            graphics.setColor(origin);
        }
        super.drawPage(g, p, pageDimension);
        if (process) {//Auto call, after the list of matched characters is updated.
            drawPage(g, p, pageDimension);
        }
    }

    @Override
    public void dispose() {
        graphics = null;
        super.dispose();
    }

    @Override
    protected void processTextPosition(TextPosition text) {
        if (text.getX() == firstX && text.getY() == firstY) {
            process = false;
        }

        if (process) {
            try {
                if (firstX == -1) {
                    firstX = text.getX();
                    firstY = text.getY();
                }
                boolean matched = MatchedCharacterUtil.isCharacterMatched(x1, y1, x2, y2, text);
                //TO DO: create a covenient class for matching text -> should be a strategy pattern
                if (!start) {

                    if (matched) {
                        start = true;
                    }
                } else {
                    if (!matched) {
                        start = false;
                    }
                }
                if (start) {

                    list.add(new Rectangle((int) (text.getX()), (int) (text.getY() - MatchedCharacterUtil.getHeight(text)), (int) Math.ceil(MatchedCharacterUtil.getWidth(text)), (int) Math.ceil(MatchedCharacterUtil.getHeight(text))));

                }
            } catch (IOException ex) {
                Logger.getLogger(PDFPageDrawer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        super.processTextPosition(text);

    }
}
