/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
        if(start.isConnectTo(end.getId())){
            return null;
        }
        UUID id = UUID.randomUUID();
        
        PDFSentenceEdge edge = new PDFSentenceEdge(id.toString(), start.id, end.id, content);
        edgeList.add(edge);
        start.edgeMap.put(id.toString(), edge);
        end.edgeMap.put(id.toString(), edge);
        return edge;
    }
    
    public PDFSentenceNode removeNode(String id){
        PDFSentenceNode node = nodeList.remove(id);
        for(String edge : node.edgeMap.keySet()){
            removeEdge(edge);
        }
        return node;
    }
    
    public PDFSentenceEdge removeEdge(String id){
        for(PDFSentenceEdge edge : edgeList){
            if(edge.id.equals(id)){
                edgeList.remove(edge);
                return edge;                
            }
        }
        return null;
    }

    public static class PDFSentenceNode {

        private String content;
        private String id;
        private Color color;
        private int page;
        private boolean hide;
        private HashMap<String, PDFSentenceEdge> edgeMap = new HashMap();

        private PDFSentenceNode(String content, String id, int page) {
            this.content = content;
            this.id = id;
            this.color = null;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPage() {
            return page;
        }

        public String getId() {
            return id;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
        
        public boolean isConnectTo(String vertexID){
            if(vertexID == null){
                throw new NullPointerException("Vertex ID is null!");
            }
            if(id.equals(vertexID)){
                return true;
            }
            for(String edge : edgeMap.keySet()){
                PDFSentenceEdge e = edgeMap.get(edge);
                if(e.endID.equals(vertexID)){
                    return true;
                }
            }
            return false;
        }

        public boolean isHide() {
            return hide;
        }

        public void setHide(boolean hide) {
            this.hide = hide;
        }

        @Override
        public String toString() {
            if(hide){
                return "";
            }
            return "<html>" + content + "</html>";
        }
        
        public Map<String, PDFSentenceEdge> getEdgeMap(){
            return new HashMap(edgeMap);
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
