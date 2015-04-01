/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Trung Pham
 */
public class PDFReaderWorkSpace {

    private HashSet<String> dic = new HashSet();
    private ArrayList<PDFSentence> summary = new ArrayList();

    public void addWordToDic(String word) {
        dic.add(word);
    }

    public void removeWordFromDic(String word) {
        dic.remove(word);
    }
    
    public boolean containsWord(String word){
        return dic.contains(word);
    }

    public void addSentenceToSummary(String sentence, int page) {
        summary.add(new PDFSentence(sentence, page));
    }

    public static class PDFSentence {

        private String content;
        private int page;

        public PDFSentence(String content, int page) {
            this.content = content;
            this.page = page;
        }
    }
    
    public List<String> getWordList(){
        List<String> result = new ArrayList();//Defensive copy, avoid modify word list content outside of this class
        result.addAll(dic);
        return result;
    }
}
