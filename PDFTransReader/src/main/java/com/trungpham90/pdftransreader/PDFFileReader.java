package com.trungpham90.pdftransreader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

/**
 * Process a PDF file
 *
 * @author Trung Pham
 */
public class PDFFileReader {

    private File file;
    private PDDocument doc;

    public PDFFileReader(String location) throws IOException {

        this(new File(location));
    }

    public PDFFileReader(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new InvalidParameterException("File not found!");
        }
        this.file = file;
        init();
    }

    private void init() throws IOException {
        doc = PDDocument.load(file);

    }

    public String getContent(int pageFrom, int pageTo) throws IOException {
        PDDocument pd = PDDocument.load(file);
        if (pageFrom < 0 || pageTo > pd.getNumberOfPages()) {
            throw new InvalidParameterException("Invalid page number!");
        }


        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(pageFrom);
        stripper.setEndPage(pageTo);
        stripper.writeText(pd, writer);

        String result = stringWriter.toString();

        if (pd != null) {
            pd.close();
        }
        writer.close();
        return result;
    }

    public int getNumPages() {
        return doc.getNumberOfPages();
    }

    public PDPage getPage(int num) {
        return (PDPage) doc.getDocumentCatalog().getAllPages().get(num);
    }

    /**
     * Get String at specified position
     *
     * @param page
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @throws IOException
     */
    public String getStringAt(int page, int x1, int y1, int x2, int y2) throws IOException {
        PDFStripper stripper = new PDFStripper();
        stripper.setProcessPosition(x1, y1, x2, y2);
        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
        stripper.setStartPage(page);
        stripper.setEndPage(page);

        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        stripper.writeText(doc, writer);
        return stripper.getProcessedString();
    }

    private class PDFStripper extends PDFTextStripper {

        private int x1, y1, x2, y2;
        private boolean process = false;
        private boolean start = false;
        private float lastY = -1, lastX = -1;
        private StringBuilder processedString;

        public PDFStripper() throws IOException {
        }

        public void setProcessPosition(int x1, int y1, int x2, int y2) {
            process = true;
            processedString = new StringBuilder();
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public String getProcessedString() {
            if (!process) {
                return null;
            }
            return processedString.toString();
        }

        protected void writeString(String text, List<TextPosition> textPositions) throws IOException {

            String prevBaseFont = "";
            StringBuilder builder = new StringBuilder();

            for (TextPosition position : textPositions) {
                if (process) {
                    // System.out.println(position.getX() + " " + position.getY() + " " + position.getCharacter() + " " + position.getWidthOfSpace());
                    if (!start) {
                        if (x1 <= position.getX() + position.getWidth() && y1 <= position.getY() + position.getHeight() && x2 >= position.getX() && y2 >= position.getY()) {
                            start = true;
                            lastY = position.getY();
                            lastX = position.getX();
                        }
                    } else {
                        if (x2 < position.getX() && y2 < position.getY()) {
                            start = false;
                        }
                    }
                    if (start) {
                        if (lastY < position.getY()) {
                            processedString.append("\n");
                        } else if (lastY == position.getY()) {
                            float g = position.getX() - lastX;
                        }                        
                        String str = position.getCharacter();

                        if (Character.isSpaceChar(str.charAt(0))) {
                            processedString.append(" ");
                        } else {
                            processedString.append(str);
                        }
                        lastY = position.getY();
                        lastX = position.getX();
                    }

                }
                String baseFont = position.getFont().getBaseFont();
                if (baseFont != null && !baseFont.equals(prevBaseFont)) {
                    builder.append('[').append(baseFont).append(']');
                    prevBaseFont = baseFont;
                }

                builder.append(position.getCharacter());
            }

            writeString(builder.toString());
        }
    }
}
