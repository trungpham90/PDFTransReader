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

    private static Node root;
    private static boolean initCall = false;

    public static void init() {
        if (!initCall) {
            try {
                process();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(DicParser.class.getName()).log(Level.SEVERE, null, ex);
            }
            initCall = true;
        }
    }

    private static void process() throws FileNotFoundException {
        Scanner scanner = new Scanner();
        String line = scanner.nextLine();
        String cur = null;
        root = new Node((char) 0);
        Node node = null;
        while (line != null) {

            String word = line.split(" ")[0];
            if (isAllCap(word)) {
                cur = word;
                char c = cur.charAt(0);
                if (!root.nxt.containsKey(c)) {
                    root.nxt.put(c, new Node(c));
                }
                node = buildTree(cur, 0, root.nxt.get(c));
            }
            if (node != null) {
                node.definition.append(line).append("\n");
            }
            line = scanner.nextLine();
        }
    }

    private static Node buildTree(String word, int index, Node node) {
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

    public static DicVO getWordDefinition(String word) {
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

    private static DicVO getDefinition(String word, int index, Node node) {

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
        String word = null;
        StringBuilder definition = new StringBuilder();
        HashMap<Character, Node> nxt = new HashMap();

        Node(char cur) {
            this.cur = cur;
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
