/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.dic;

/**
 *
 * @author Trung Pham
 */
public class DicVO {

    String word;
    String content;

    public DicVO(String word, String content) {
        this.word = word;
        this.content = content;
    }

    public String getWord() {
        return word;
    }

    public String getContent() {
        return content;
    }
}
