/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.reader;

import com.pdfreader.util.MatchedCharacterUtil;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

/**
 *
 * @author Trung Pham
 */
class PDFPageProcessor {

    public PDFPageProcessor(int page, PDDocument doc) throws IOException {
        processPage(page, doc);
    }
    TreeMap<Float, TreeMap<Float, PDFWord>> map = new TreeMap();//Sorted word based on position

    private void processPage(int page, PDDocument doc) throws IOException {
        PDFStripper stripper = new PDFStripper();

        stripper.setStartPage(page);
        stripper.setEndPage(page);

        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        stripper.writeText(doc, writer);
        stripper.finish();
    }

    public List<PDFWord> getStringAt(float x1, float y1, float x2, float y2) {
        List<PDFWord> list = new ArrayList();
        float a = map.floorKey(x1);

        return list;

    }

    private class PDFStripper extends PDFTextStripper {

        private int x1, y1, x2, y2;
        private boolean start = false;
        private float lastY = -1, lastX = -1, startX = -1, startY = -1, width, height, lastSize = -1;
        private StringBuilder processedString = null;
        private StringBuilder word = new StringBuilder();

        public PDFStripper() throws IOException {
        }

        /**
         * This method will cover the corner case, adding the final word.
         */
        public void finish() {
            if (word.length() > 0) {
                if (!map.containsKey(startY)) {
                    map.put(startY, new TreeMap());
                }
                map.get(startY).put(startX, new PDFWord(startX, startY, width, height, word.toString()));
            }
        }

        @Override
        protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
            for (TextPosition pos : textPositions) {
                String str = pos.getCharacter();
                if (Character.isSpaceChar(str.charAt(0)) || (!Character.isLetter(str.charAt(0)) && !Character.isDigit(str.charAt(0)))) {
                    if (word.length() > 0) {
                        if (!map.containsKey(startY)) {
                            map.put(startY, new TreeMap());
                        }
                        map.get(startY).put(startX, new PDFWord(startX, startY, width, height, word.toString()));
                    }
                    startX = -1;
                    word = new StringBuilder();
                } else if (lastY >= 0 && lastY != pos.getY()) {
                    if (word.length() > 0) {
                        if (!map.containsKey(startY)) {
                            map.put(startY, new TreeMap());
                        }
                        map.get(startY).put(startX, new PDFWord(startX, startY, width, height, word.toString()));
                    }
                    startX = pos.getX();
                    startY = pos.getY();
                    width = MatchedCharacterUtil.getWidth(pos);
                    height = MatchedCharacterUtil.getHeight(pos);

                    word = new StringBuilder();
                    word.append(str);

                } else {
                    if (startX == -1) {
                        startX = pos.getX();
                        startY = pos.getY();
                        width = MatchedCharacterUtil.getWidth(pos);
                        height = MatchedCharacterUtil.getHeight(pos);
                    } else {
                        width += MatchedCharacterUtil.getWidth(pos);
                        height = Math.max(height, MatchedCharacterUtil.getHeight(pos));
                    }
                    word.append(str);
                }
                lastX = pos.getX();
                lastY = pos.getY();

            }

        }
    }
}
