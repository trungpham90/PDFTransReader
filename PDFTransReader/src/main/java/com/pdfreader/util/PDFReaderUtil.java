/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.util;

import com.pdfreader.reader.PDFWord;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.util.TextPosition;
import org.jsoup.Jsoup;

/**
 *
 * @author Trung Pham
 */
public class PDFReaderUtil {

    public static float getWidth(TextPosition pos) throws IOException {

        float width = Math.min(pos.getXScale(), pos.getWidth());
        return width;
    }

    public static float getHeight(TextPosition pos) throws IOException {

        float height = Math.min(pos.getYScale(), pos.getHeight());
        return height;
    }

    /**
     * Check if a word is inside a area defined by (x1,y1) (x2,y2)
     *
     * @param word
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static boolean isContains(PDFWord word, float x1, float y1, float x2, float y2) {
        boolean first = word.getY() <= y1 && y1 <= word.getY() + word.getHeight();
        boolean second = word.getY() <= y2 && y2 <= word.getY() + word.getHeight();
        if (first && second) {
            if (word.getX() <= x1 && word.getX() + word.getWidth() >= x1) {
                return true;
            } else if (word.getX() <= x2 && word.getX() + word.getWidth() >= x2) {
                return true;
            } else if (x1 <= word.getX() && word.getX() + word.getWidth() <= x2) {
                return true;
            }
        } else if (first) {
            if (word.getX() + word.getWidth() >= x1) {
                return true;
            }
        } else if (second) {
            if (word.getX() <= x2) {
                return true;
            }
        } else if (y1 < word.getY() && word.getY() < y2) {
            return true;
        }
        return false;
    }

    /**
     * Check if a word is exactly at this point
     *
     * @param word
     * @param x
     * @param y
     * @return
     */
    public static boolean isCorrectWord(PDFWord word, float x, float y) {
        if (word.getX() <= x && word.getX() + word.getWidth() >= x) {
            return word.getY() <= y && word.getY() + word.getHeight() >= y;
        }
        return false;
    }

    /**
     * Convert list of PDFWord into text
     *
     * @param list
     * @return C
     */
    public static String getTextFromPDFList(List<PDFWord> list) {
        StringBuilder builder = new StringBuilder();
        float lastY = -1;
        for (PDFWord word : list) {
            if (lastY != -1 && lastY != word.getY()) {
                builder.append("\n");
            } else {
                builder.append(" ");
            }
            builder.append(word.getWord());
        }
        System.out.println("STRING " + builder.toString());
        return builder.toString();
    }

    public static String getTextWithoutHTMLTag(String text) {
        return Jsoup.parse(text).text();
    }
}
