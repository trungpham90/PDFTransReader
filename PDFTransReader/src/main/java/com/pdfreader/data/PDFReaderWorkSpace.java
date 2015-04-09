/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Trung Pham
 */
public class PDFReaderWorkSpace {

    private HashSet<String> dic = new HashSet();
    private HashMap<Integer, PDFSentenceNode> nodeList = new HashMap();

    public void addWordToDic(String word) {
        dic.add(word);
    }

    public void removeWordFromDic(String word) {
        dic.remove(word);
    }

    public boolean containsWord(String word) {
        return dic.contains(word);
    }

    public List<String> getWordList() {
        List<String> result = new ArrayList();//Defensive copy, avoid modify word list content outside of this class
        result.addAll(dic);
        return result;
    }

    public static class PDFSentenceNode {

        private String content;
        private int id;
        private int page;
        private HashMap<Integer, PDFSentenceEdge> edgeList = new HashMap();

        public PDFSentenceNode(String content, int id, int page) {
            this.content = content;
            this.id = id;
        }
    }

    public static class PDFSentenceEdge {

        private int startID;
        private int endID;
        private int id;
        private String conent;

        public PDFSentenceEdge(int id, int startID, int endID, String conent) {
            this.id = id;
            this.startID = startID;
            this.endID = endID;
            this.conent = conent;
        }
    }
}
