/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.reader;

import com.pdfreader.util.PDFReaderUtil;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.TextPosition;

/**
 * This class processes whole page, identifies and stores each word in this page
 * into binary search tree and special prefix tree, which allow to advance
 * operation.
 *
 * @author Trung Pham
 */
class PDFPageProcessor {

    private TreeMap<Float, ArrayList<PDFWord>> map = new TreeMap();//Sorted word based on position
    private CharacterNode root = new CharacterNode((char) 0);//Root of the prefix tree.
    private static final int THREADS_HOLD = 4;

    public PDFPageProcessor(int page, PDDocument doc) throws IOException {
        processPage(page, doc);
    }

    private void processPage(int page, PDDocument doc) throws IOException {
        PDFStripper stripper = new PDFStripper();

        stripper.setStartPage(page + 1);
        stripper.setEndPage(page + 1);

        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        stripper.writeText(doc, writer);
        stripper.finish();
    }
    /**
     * Get the list of word in a specific area defined by two poins (x1,y1) (x2,y2)
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 
     */
    public List<PDFWord> getStringAt(float x1, float y1, float x2, float y2) {
        List<PDFWord> list = new ArrayList();
        Float start = null;
        for (int i = 10; i >= -10 && start == null; i--) {
            start = map.ceilingKey(y1 - i);
        }
        if (start == null) {
            return list;
        }
        boolean outOfRange = false;
        for (Float a : map.tailMap(start, true).keySet()) {
            for (PDFWord word : map.get(a)) {
                if (PDFReaderUtil.isContains(word, x1, y1, x2, y2)) {
                    list.add(word);
                } else {
                    if (word.getY() > y2) {
                        outOfRange = true;
                        break;
                    }
                }
            }
            if (outOfRange) {
                break;
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
                    if (PDFReaderUtil.isCorrectWord(word, x, y)) {
                        return word;
                    }
                }
            }
        }
        return null;
    }

    public List<PDFWord> getMatchingWord(String word) {
        List<PDFWord> result = new ArrayList();
        word = word.toUpperCase();//Converse to Uppercase
        char start = word.charAt(0);
        if (root.nxt.containsKey(start)) {
            getMatchingWord(result, 0, 0, word, root.nxt.get(start));
        }
        return result;
    }

    private void getMatchingWord(List<PDFWord> list, int index, int dif, String word, CharacterNode node) {
        if (dif > THREADS_HOLD) {
            return;
        }
        if (dif > 0 || index + 1 >= word.length()) {
            list.addAll(node.list);
            for (char c : node.nxt.keySet()) {
                getMatchingWord(list, index, dif + 1, word, node.nxt.get(c));
            }
        } else {

            char c = word.charAt(index + 1);
            if (node.nxt.containsKey(c)) {
                getMatchingWord(list, index + 1, 0, word, node.nxt.get(c));
            }
            //If the last character is different, it is acceptable!
            if (index + 2 == word.length()) {
                for (char child : node.nxt.keySet()) {
                    if (child != c) {//Need to handle this case specifically, because it has been added before
                        getMatchingWord(list, index + 1, 1, word, node.nxt.get(child));
                    }
                }
            }
        }

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
                if (!map.containsKey(lastY)) {
                    map.put(lastY, new ArrayList());
                }
                width += lastX - startX;
                PDFWord w = new PDFWord(startX, startY, width, height, word.toString());
                map.get(lastY).add(w);
                buildTree(w);
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
                if (Character.isSpaceChar(str.charAt(0))) {
                    if (word.length() > 0) {
                        if (!map.containsKey(lastY)) {
                            map.put(lastY, new ArrayList());
                        }
                        width += lastX - startX;
                        PDFWord w = new PDFWord(startX, startY, width, height, word.toString());
                        map.get(lastY).add(w);
                        buildTree(w);
                    }
                    startX = -1;
                    word = new StringBuilder();
                } else if (space || (lastY >= 0 && lastY != pos.getY())) {
                    if (word.length() > 0) {
                        if (!map.containsKey(lastY)) {
                            map.put(lastY, new ArrayList());
                        }
                        width += lastX - startX;
                        PDFWord w = new PDFWord(startX, startY, width, height, word.toString());
                        map.get(lastY).add(w);
                        buildTree(w);
                    }
                    startX = pos.getX();
                    startY = pos.getY() - PDFReaderUtil.getHeight(pos);
                    width = PDFReaderUtil.getWidth(pos);
                    height = PDFReaderUtil.getHeight(pos);

                    word = new StringBuilder();
                    word.append(str);

                } else {
                    if (startX == -1) {
                        startX = pos.getX();
                        startY = pos.getY() - PDFReaderUtil.getHeight(pos);
                        width = PDFReaderUtil.getWidth(pos);
                        height = PDFReaderUtil.getHeight(pos);
                    } else {
                        width = PDFReaderUtil.getWidth(pos);
                        height = Math.max(height, PDFReaderUtil.getHeight(pos));
                    }

                    word.append(str);
                }
                lastX = pos.getX();
                lastY = pos.getY();
                lastSize = PDFReaderUtil.getWidth(pos);

            }

        }

        private void buildTree(PDFWord word) {
            String content = word.getWord().toUpperCase();
            char c = content.charAt(0);
            if (!root.nxt.containsKey(c)) {
                root.nxt.put(c, new CharacterNode(c));
            }
            buildTree(0, content, word, root.nxt.get(c));
        }

        private void buildTree(int index, String content, PDFWord word, CharacterNode node) {
            if (index + 1 >= content.length()) {
                node.list.add(word);
                return;
            }
            char c = content.charAt(index + 1);
            if (!node.nxt.containsKey(c)) {
                node.nxt.put(c, new CharacterNode(c));
            }
            buildTree(index + 1, content, word, node.nxt.get(c));
        }
    }

    static class CharacterNode {

        char content;
        HashMap<Character, CharacterNode> nxt = new HashMap();
        ArrayList<PDFWord> list = new ArrayList();

        CharacterNode(char c) {
            content = c;
        }
    }
}
