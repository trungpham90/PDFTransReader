/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Trung Pham
 */
public class PDFReaderWorkSpace {

    private HashSet<String> dic = new HashSet();
    private HashMap<String, PDFSentenceNode> nodeList = new HashMap();
    private ArrayList<PDFSentenceEdge> edgeList = new ArrayList();

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

    public PDFSentenceNode createSentenceNode(String content, int page) {
        UUID id = UUID.randomUUID();
        PDFSentenceNode node = new PDFSentenceNode(content, id.toString(), page);
        nodeList.put(id.toString(), node);
        return node;
    }

    public PDFSentenceEdge createSentenceEdge(PDFSentenceNode start, PDFSentenceNode end, String content) {
        UUID id = UUID.randomUUID();
        PDFSentenceEdge edge = new PDFSentenceEdge(id.toString(), start.id, end.id, content);
        edgeList.add(edge);
        start.edgeMap.put(id.toString(), edge);
        end.edgeMap.put(id.toString(), edge);
        return edge;
    }

    public static class PDFSentenceNode {

        private String content;
        private String id;
        private int page;
        private HashMap<String, PDFSentenceEdge> edgeMap = new HashMap();

        private PDFSentenceNode(String content, String id, int page) {
            this.content = content;
            this.id = id;
        }

        @Override
        public String toString() {
            return "<html>" + content + "</html>";
        }
    }

    public static class PDFSentenceEdge {

        private String startID;
        private String endID;
        private String id;
        private String content;

        private PDFSentenceEdge(String id, String startID, String endID, String content) {
            this.id = id;
            this.startID = startID;
            this.endID = endID;
            this.content = content;
        }

        @Override
        public String toString() {
            return "<html>" + content + "</html>";
        }
    }
}
