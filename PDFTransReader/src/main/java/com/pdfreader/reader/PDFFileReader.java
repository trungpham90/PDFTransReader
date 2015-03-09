package com.pdfreader.reader;

import com.pdfreader.util.MatchedCharacterUtil;
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
    private float startX, startY, endX, endY;

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

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getEndX() {
        return endX;
    }

    public float getEndY() {
        return endY;
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

    public String getWordAt(int page, int x, int y) throws IOException {
        PDFStripper stripper = new PDFStripper();
        stripper.setDoubleClickPosition(x, y);
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        stripper.writeText(doc, writer);
        startX = stripper.resultStartX;
        startY = stripper.resultStartY;
        endX = stripper.resultEndX;
        endY = stripper.resultEndY;
        return stripper.getProcessedString();
    }

    /**
     *
     */
    private static class PDFStripper extends PDFTextStripper {

        static enum Action {

            Null,
            Selection,
            DoubleClick;
        };
        private int x1, y1, x2, y2;
        private Action process = Action.Null;
        private boolean start = false;
        private float lastY = -1, lastX = -1, startX = -1, startY = -1, resultStartX = -1, resultStartY = -1, resultEndX = -1, resultEndY = -1, lastSize = -1;
        private StringBuilder processedString = null;
        private StringBuilder word = null;

        public PDFStripper() throws IOException {
        }

        public void setProcessPosition(int x1, int y1, int x2, int y2) {
            process = Action.Selection;
            processedString = new StringBuilder();
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public void setDoubleClickPosition(int x, int y) {
            process = Action.DoubleClick;
            word = new StringBuilder();
            x1 = x;
            y1 = y;
            startX = -1;
            startY = -1;
            resultStartX = -1;
            resultEndX = -1;
            resultStartY = -1;
            resultEndY = -1;
            lastSize = -1;
        }

        public String getProcessedString() {
            if (process == Action.Null) {
                return null;
            }
            //Special case, end of page!
            if (processedString == null && start) {
                processedString = word;
            }
            return processedString.toString();
        }

        protected void writeString(String text, List<TextPosition> textPositions) throws IOException {

            String prevBaseFont = "";
            StringBuilder builder = new StringBuilder();

            for (TextPosition pos : textPositions) {
                if (process == Action.Selection) {
                    // System.out.println(position.getX() + " " + position.getY() + " " + position.getCharacter() + " " + position.getWidthOfSpace());
                    boolean matched = MatchedCharacterUtil.isCharacterMatched(x1, y1, x2, y2, pos);
                    if (!start) {
                        if (matched) {
                            start = true;
                            lastY = pos.getY();
                            lastX = pos.getX();
                        }
                    } else {
                        if (!matched) {
                            start = false;
                        }
                    }
                    if (start) {
                        if (lastY < pos.getY()) {
                            processedString.append("\n");
                        } else if (lastY == pos.getY()) {
                            float g = pos.getX() - lastX;
                        }
                        String str = pos.getCharacter();

                        if (Character.isSpaceChar(str.charAt(0))) {
                            processedString.append(" ");
                        } else {
                            processedString.append(str);
                        }
                        lastY = pos.getY();
                        lastX = pos.getX();
                    }

                } else if (process == Action.DoubleClick) {
                    String str = pos.getCharacter();
                    if (x1 <= pos.getX() && y1 <= pos.getY() && processedString == null) {
                        start = true;
                    }
                    if (Character.isSpaceChar(str.charAt(0)) || (!Character.isLetter(str.charAt(0)) && !Character.isDigit(str.charAt(0)))) {
                        if (start) {
                            start = false;
                            processedString = word;
                            resultStartX = startX;
                            resultStartY = startY;
                            resultEndX = lastX;
                            resultEndY = lastY;
                        }
                        startX = -1;
                        word = new StringBuilder();
                    } else if (lastY >= 0 && lastY != pos.getY()) {
                        if (start) {
                            start = false;
                            if (word.length() > 0) {
                                processedString = word;
                                resultStartX = startX;
                                resultStartY = startY;
                                resultEndX = lastX ;
                                resultEndY = lastY;
                            }

                        }
                        startX = pos.getX();
                        startY = pos.getY();
                        word = new StringBuilder();
                        word.append(str);

                    } else {
                        if (startX == -1) {
                            startX = pos.getX();
                            startY = pos.getY();
                        }
                        word.append(str);
                    }
                    lastX = pos.getX();
                    lastY = pos.getY();
                    lastSize = MatchedCharacterUtil.getWidth(pos);
                }
//                String baseFont = pos.getFont().getBaseFont();
//                if (baseFont != null && !baseFont.equals(prevBaseFont)) {
//                    builder.append('[').append(baseFont).append(']');
//                    prevBaseFont = baseFont;
//                }
//
//                builder.append(pos.getCharacter());
            }

            //   writeString(builder.toString());
        }
    }
}
