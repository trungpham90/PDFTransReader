/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.reader;

/**
 *
 * @author Trung Pham
 */
public class PDFWord {

    private float x, y, width, height;
    private String word;

    public PDFWord(float x, float y, float width, float height, String word) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.word = word;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return "PDFWord{" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", word=" + word + '}';
    }
}
