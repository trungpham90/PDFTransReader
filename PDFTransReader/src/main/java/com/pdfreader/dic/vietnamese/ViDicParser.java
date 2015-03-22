/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.dic.vietnamese;

import com.pdfreader.dic.DicVO;
import com.pdfreader.dic.IDic;
import com.pdfreader.dic.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Trung Pham
 */
public class ViDicParser implements IDic {

    private static Node root;
    private static boolean initCall = false;
    private static ViDicParser instance = new ViDicParser();

    private ViDicParser() {
        
        init();
    }

    public static ViDicParser getInstance() {
        return instance;
    }

    private void init() {

        if (!initCall) {
            try {
                process();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ViDicParser.class.getName()).log(Level.SEVERE, null, ex);
            }
            initCall = true;
        }
    }

    private void process() throws FileNotFoundException {
        Scanner scanner = new Scanner("/Vi.txt");
        String line = scanner.nextLine();
        String cur = null;
        root = new Node((char) 0);
        Node node = null;
        while (line != null) {

            cur = line.split(" ")[0].toUpperCase();

            char c = cur.charAt(0);
            if (!root.nxt.containsKey(c)) {
                root.nxt.put(c, new Node(c));
            }
            node = buildTree(cur, 0, root.nxt.get(c));
            String[]tmp = line.split(" ");
            for(String word : tmp){
                boolean twoLine = word.endsWith("-");
                boolean nxtLine = word.endsWith("+");
                if(twoLine){
                    word = word.substring(0, word.length() - 1);
                }
                node.definition.append(word).append(" ");
                if(twoLine){
                    node.definition.append("\n").append("\n");
                }else if(nxtLine){
                    node.definition.append("\n");
                }
                
            }
            line = scanner.nextLine();
        }
    }

    private Node buildTree(String word, int index, Node node) {
        if (index + 1 == word.length()) {
            node.word = word;

            return node;
        } else {
            char c = word.charAt(index + 1);
            if (!node.nxt.containsKey(c)) {
                node.nxt.put(c, new Node(c));
            }
            return buildTree(word, index + 1, node.nxt.get(c));
        }
    }

    @Override
    public DicVO getWordDefinition(String word) {
        if (word == null || word.isEmpty()) {
            return null;
        }
        word = word.toUpperCase();
        char c = word.charAt(0);
        if (root.nxt.containsKey(c)) {
            return getDefinition(word, 0, root.nxt.get(c));
        }
        return null;
    }

    private DicVO getDefinition(String word, int index, Node node) {

        DicVO result = null;
        if (node.word != null) {
            result = new DicVO(node.word, node.definition.toString());
        }
        if (index + 1 == word.length()) {
            return result;
        }
        char c = word.charAt(index + 1);
        if (node.nxt.containsKey(c)) {
            DicVO tmp = getDefinition(word, index + 1, node.nxt.get(c));
            if (tmp != null) {
                return tmp;
            }
        } else {//Look ahead one character, may not be correct!
            for (char nxt : node.nxt.keySet()) {
                if (node.nxt.get(nxt).word != null) {
                    result = new DicVO(node.nxt.get(nxt).word, node.nxt.get(nxt).definition.toString());
                    break;
                }
            }
        }
        return result;

    }

    private boolean isAllCap(String word) {
        if (word.isEmpty()) {
            return false;
        }
        if (Character.isDigit(word.charAt(0))) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            if (Character.isAlphabetic(word.charAt(i))) {
                if (Character.isLowerCase(word.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    //Building a tree to fasten search for word
    static class Node {

        char cur;
        String word = null;
        StringBuilder definition = new StringBuilder();
        HashMap<Character, Node> nxt = new HashMap();

        Node(char cur) {
            this.cur = cur;
        }
    }
}
