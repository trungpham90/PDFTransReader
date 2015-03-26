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
    TreeMap<Float, ArrayList<PDFWord>> map = new TreeMap();//Sorted word based on position

    private void processPage(int page, PDDocument doc) throws IOException {
        PDFStripper stripper = new PDFStripper();

        stripper.setStartPage(page + 1);
        stripper.setEndPage(page + 1);

        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        stripper.writeText(doc, writer);
        stripper.finish();
    }

    public List<PDFWord> getStringAt(float x1, float y1, float x2, float y2) {
        List<PDFWord> list = new ArrayList();
        Float start = null;
        for (int i = 10; i >= -10 && start == null; i--) {
            start = map.ceilingKey(y1 - i);
        }
        if (start == null) {
            return list;
        }
        for (Float a : map.tailMap(start, true).keySet()) {
            if (a > y2) {
                break;
            }
            for (PDFWord word : map.get(a)) {
                if (MatchedCharacterUtil.isContains(word, x1, y1, x2, y2)) {
                    list.add(word);
                }
            }
        }

        return list;

    }

    public PDFWord getWordAt(float x, float y) {
        Float start = null;
        for (int i = 10; i >= -10 && start == null; i--) {
            start = map.ceilingKey(y - i);
        }
        if (start != null) {
            for (Float a : map.tailMap(start, true).keySet()) {
                for (PDFWord word : map.get(a)) {                 
                    if (MatchedCharacterUtil.isCorrectWord(word, x, y)) {
                        return word;
                    }
                }
            }
        }
        return null;
    }

    private class PDFStripper extends PDFTextStripper {

        private int x1, y1, x2, y2;
        private boolean start = false;
        private float lastY = -1, lastX = -1, startX = -1, startY = -1, width, height, lastSize = -1;
        private StringBuilder processedString = null;
        private StringBuilder word = new StringBuilder();

        public PDFStripper() throws IOException {
            super();
        }

        /**
         * This method will cover the corner case, adding the final word.
         */
        public void finish() {
            if (word.length() > 0) {
                if (!map.containsKey(startY)) {
                    map.put(startY, new ArrayList());
                }
                //System.out.println(startY + " " + word.toString());
                map.get(startY).add(new PDFWord(startX, startY, width, height, word.toString()));
            }
        }

        @Override
        protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
            for (TextPosition pos : textPositions) {
                String str = pos.getCharacter();
                boolean space = false;
                if (lastY == pos.getY()) {
                    space = (pos.getX() - lastX - lastSize) >= getAverageCharTolerance();
                }
                //System.out.println(str + " " + pos.getWidthOfSpace() + " " + getSpacingTolerance());
                if (Character.isSpaceChar(str.charAt(0)) || (!Character.isLetter(str.charAt(0)) && !Character.isDigit(str.charAt(0)))) {
                    if (word.length() > 0) {
                        if (!map.containsKey(startY)) {
                            map.put(startY, new ArrayList());
                        }
                        //System.out.println(startY + " " + word.toString());
                        map.get(startY).add(new PDFWord(startX, startY, width, height, word.toString()));
                    }
                    startX = -1;
                    word = new StringBuilder();
                } else if (space || (lastY >= 0 && lastY != pos.getY())) {
                    if (word.length() > 0) {
                        if (!map.containsKey(startY)) {
                            map.put(startY, new ArrayList());
                        }
                        //System.out.println(startY + " " + word.toString() );
                        map.get(startY).add(new PDFWord(startX, startY, width, height, word.toString()));
                    }
                    startX = pos.getX() - MatchedCharacterUtil.getWidth(pos);
                    startY = pos.getY() - MatchedCharacterUtil.getHeight(pos);
                    width = MatchedCharacterUtil.getWidth(pos);
                    height = MatchedCharacterUtil.getHeight(pos);

                    word = new StringBuilder();
                    word.append(str);

                } else {
                    if (startX == -1) {
                        startX = pos.getX() - MatchedCharacterUtil.getWidth(pos);
                        startY = pos.getY() - MatchedCharacterUtil.getHeight(pos);
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
                lastSize = MatchedCharacterUtil.getWidth(pos);

            }

        }
    }
}
