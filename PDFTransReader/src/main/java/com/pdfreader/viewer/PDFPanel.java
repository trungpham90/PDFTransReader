/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer;

import com.pdfreader.reader.PDFWord;
import com.pdfreader.viewer.PDFPageDrawer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.List;
import javax.swing.JPanel;
import org.apache.pdfbox.pdfviewer.PDFPagePanel;
import org.apache.pdfbox.pdfviewer.PageDrawer;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

/**
 *
 * @author Trung Pham
 */
public class PDFPanel extends JPanel {

    private static final long serialVersionUID = -4629033339560890669L;
    private PDPage page;
    private PDFPageDrawer drawer = null;
    private Dimension pageDimension = null;
    private Dimension drawDimension = null;

    /**
     * Constructor.
     *
     * @throws IOException If there is an error creating the Page drawing
     * objects.
     */
    public PDFPanel() throws IOException {
        drawer = new PDFPageDrawer();
    }
    public void setHightlightArea(List<PDFWord> list){
        drawer.setHighlight(list);
    }
    
    public void clearHighLight(){
        drawer.clearHighLight();
    }
       
    
    /**
     * This will set the page that should be displayed in this panel.
     *
     * @param pdfPage The page to draw.
     */
    public void setPage(PDPage pdfPage) {
        page = pdfPage;
        drawer.clearHighLight();//Turn off highlight when turn page
        PDRectangle cropBox = page.findCropBox();
        drawDimension = cropBox.createDimension();
        int rotation = page.findRotation();
        if (rotation == 90 || rotation == 270) {
            pageDimension = new Dimension(drawDimension.height, drawDimension.width);
        } else {
            pageDimension = drawDimension;
        }
        setSize(pageDimension);
        setBackground(java.awt.Color.white);
    }

    
    
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void paint(Graphics g) {
        try {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());

            int rotation = page.findRotation();
            if (rotation == 90 || rotation == 270) {
                Graphics2D g2D = (Graphics2D) g;
                g2D.translate(pageDimension.getWidth(), 0.0f);
                g2D.rotate(Math.toRadians(rotation));
            } else if (rotation == 180) {
                Graphics2D g2D = (Graphics2D) g;
                g2D.translate(pageDimension.getWidth(), pageDimension.getHeight());
                g2D.rotate(Math.toRadians(rotation));
            }

            drawer.drawPage(g, page, drawDimension);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
