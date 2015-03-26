/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.util;

import com.pdfreader.reader.PDFWord;
import java.io.IOException;
import org.apache.pdfbox.util.TextPosition;

/**
 *
 * @author Trung Pham
 */
public class MatchedCharacterUtil {

    public static float getWidth(TextPosition pos) throws IOException {

        float width = Math.min(pos.getXScale(), pos.getWidth());
        return width;
    }

    public static float getHeight(TextPosition pos) throws IOException {

        float height = Math.min(pos.getYScale(), pos.getHeight());
        return height;
    }

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

    public static boolean isCorrectWord(PDFWord word, float x, float y) {      
        if (word.getX() <= x && word.getX() + word.getWidth() >= x) {
            return word.getY() <= y && word.getY() + word.getHeight() >= y;
        }
        return false;
    }
}
