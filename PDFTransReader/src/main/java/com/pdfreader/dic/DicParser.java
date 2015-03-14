/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.dic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
public class DicParser {

    private static HashMap<String, StringBuilder> map = new HashMap();
    private static Node root;
    static {
        try {
            process();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DicParser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void process() throws FileNotFoundException {
        Scanner scanner = new Scanner();
        String line = scanner.nextLine();
        String cur = null;
        while (line != null) {

            String word = line.split(" ")[0];
            if (isAllCap(word)) {
                cur = word;
                if (!map.containsKey(word)) {
                    map.put(word, new StringBuilder());
                }
            }
            map.get(cur).append(line).append("\n");
            line = scanner.nextLine();
        }
        root = new Node((char)0);
        for(String word : map.keySet()){
            char c = word.charAt(0);
            if(!root.nxt.containsKey(c)){
                root.nxt.put(c, new Node(c));
            }
            buildTree(word, 0, root.nxt.get(c));
        }

    }
    
    
    private static void buildTree(String word, int index, Node node){
        if(index + 1 == word.length()){           
            node.definition = map.get(word).toString();
        }else{
            char c = word.charAt(index + 1);
            if(!node.nxt.containsKey(c)){
                node.nxt.put(c, new Node(c));
            }
            buildTree(word, index + 1, node.nxt.get(c));
        }
    }

    public static String getWordDefinition(String word) {
       if(word == null || word.isEmpty()){
           return null;
       }
       word = word.toUpperCase();
       char c = word.charAt(0);
       if(root.nxt.containsKey(c)){
           return getDefinition(word, 0, root.nxt.get(c));
       }
       return null;
    }
    
    private static String getDefinition(String word, int index, Node node){
        
        String result = null;
        if(node.definition != null){
            result = node.definition;
        }
        if(index + 1 == word.length()){
            return result;
        }
        char c = word.charAt(index + 1);
        if(node.nxt.containsKey(c)){
            String tmp = getDefinition(word, index + 1, node.nxt.get(c));
            if(tmp != null){
                return tmp;
            }
        }
        return result;
        
    }

    private static boolean isAllCap(String word) {
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
        String definition = null;
        HashMap<Character, Node> nxt = new HashMap();

        Node(char cur) {
            this.cur = cur;
        }

        Node(char cur, String definition) {
            this(cur);
            this.definition = definition;
        }
    }

    static class Scanner {

        BufferedReader br;
        StringTokenizer st;

        public Scanner() throws FileNotFoundException {
            // System.setOut(new PrintStream(new BufferedOutputStream(System.out), true));
            //br = new BufferedReader(new InputStreamReader(System.in));

            br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dic.txt")));
        }

        public String next() {

            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    throw new RuntimeException();
                }
            }
            return st.nextToken();
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public String nextLine() {
            st = null;
            try {
                return br.readLine();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }

        public boolean endLine() {
            try {
                String next = br.readLine();
                while (next != null && next.trim().isEmpty()) {
                    next = br.readLine();
                }
                if (next == null) {
                    return true;
                }
                st = new StringTokenizer(next);
                return st.hasMoreTokens();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }
}
