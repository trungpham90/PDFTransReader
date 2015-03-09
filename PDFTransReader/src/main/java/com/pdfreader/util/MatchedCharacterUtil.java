/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.util;

import java.io.IOException;
import org.apache.pdfbox.util.TextPosition;

/**
 *
 * @author Trung Pham
 */
public class MatchedCharacterUtil {

    public static boolean isCharacterMatched(int x1, int y1, int x2, int y2, TextPosition pos) throws IOException {

        float height = getHeight(pos);
        float width = getWidth(pos);

        if (x1 <= pos.getX() && y1 <= pos.getY() && x2 >= pos.getX() && y2 >= pos.getY() - height / 3) {
            // System.out.println(pos.getX() + " " + pos.getY() + " " + pos.getCharacter() + " " + height);
            return true;
        } else if (y1 < pos.getY() && y2 > pos.getY()) {
            // System.out.println(pos.getX() + " " + pos.getY() + " " + pos.getCharacter() + " " + height + " " + width);
            return true;
        }
        return false;
    }

    public static float getWidth(TextPosition pos) throws IOException {

        float width = pos.getXScale();
        return width;
    }

    public static float getHeight(TextPosition pos) throws IOException {

        float height = pos.getYScale();
        return height;
    }
}
