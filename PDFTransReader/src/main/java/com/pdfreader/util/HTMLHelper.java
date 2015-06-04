/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.util;

import java.awt.Color;
import java.util.Arrays;
import java.util.Stack;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Trung Pham
 */
public class HTMLHelper {

    public static enum HTMLTag {

        BOLD("<b>", "</b>"),
        ITALIC("<i>", "</i>"),
        UNDERLINE("<u>", "</u>"),
        FONT("<font>", "</font>");
        private String start, end;

        HTMLTag(String start, String end) {
            this.start = start;
            this.end = end;
        }

        public static HTMLTag getTag(String text) {
            for (HTMLTag tag : values()) {
                if (tag.start.equals(text) || tag.end.equals(text)) {
                    return tag;
                }
            }
            return null;
        }

        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }
    }

    public static enum HTMLColor {

        RED("Red", 0xFF0000),
        CYAN("Cyan", 0x00FFFF),
        BLUE("Blue", 0x0000FF),
        DARKBLUE("DarkBlue", 0x0000A0),
        LIGHTBLUE("LightBlue", 0xADD8E6),
        PURPLE("Purple", 0x800080),
        YELLOW("Yellow", 0xFFFF00),
        LIME("Lime", 0x00FF00),
        MAGENTA("Magenta", 0xFF00FF),
        WHITE("White", 0xFFFFFF),
        SILVER("Silver", 0xC0C0C0),
        GRAY("Gray", 0x808080),
        BLACK("Black", 0x000000),
        ORANGE("Orange", 0xFFA500),
        BROWN("Brown", 0xA52A2A),
        MAROON("Maroon", 0x800000),
        GREEN("Green", 0x008000),
        OLIVE("Olive", 0x808000);
        private String name;
        private int number;

        HTMLColor(String name, int number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public int getNumber() {
            return number;
        }

        public static HTMLColor getColor(int number) {
            for (HTMLColor c : values()) {
                if (c.number == number) {
                    return c;
                }
            }
            return null;
        }

        public static HTMLColor getColor(String name) {
            for (HTMLColor c : values()) {
                if (c.name.equals(name)) {
                    return c;
                }
            }
            return null;
        }
    }
    public static final int[] HTML_FONT_SIZE = {8, 9, 10, 11, 12, 14, 18, 24, 30, 36, 48, 60, 72, 96};

    public static void convertHtmlToTextStyle(String text, StyledDocument doc) throws BadLocationException {

        Stack<String> stack = new Stack();
        Stack<Integer> pos = new Stack();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '<') {
                boolean neg = false;
                if (i + 1 < text.length() && text.charAt(i + 1) == '/') {
                    neg = true;
                }
                String cur = "";
                while (text.charAt(i) != '>') {
                    cur += text.charAt(i);
                    i++;
                }
                cur += text.charAt(i);
                if (!neg) {
                    stack.add(cur);
                    pos.add(doc.getLength());
                } else {
                    String tag = stack.pop();
                    //Check tag by using closing tag is more easy.
                    HTMLHelper.HTMLTag t = HTMLHelper.HTMLTag.getTag(cur);
                    int start = pos.pop();
                    if (t != null) {

                        SimpleAttributeSet att = new SimpleAttributeSet();
                        switch (t) {
                            case BOLD:
                                StyleConstants.setBold(att, true);
                                break;
                            case ITALIC:
                                StyleConstants.setItalic(att, true);
                                break;
                            case UNDERLINE:
                                StyleConstants.setUnderline(att, true);
                                break;
                            case FONT:
                                setFont(tag, att);
                                break;
                        }
                        doc.setCharacterAttributes(start, doc.getLength() - start, att, true);

                    }
                }
            } else {
                doc.insertString(doc.getLength(), "" + text.charAt(i), null);
            }
        }
    }

    public static String getTextStyleToHTML(StyledDocument doc) throws BadLocationException {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < doc.getLength(); i++) {
            Element element = doc.getCharacterElement(i);
            StringBuilder tmp = new StringBuilder();
            AttributeSet set = element.getAttributes();
            builder.append(getTextStyleToHTML(doc.getText(i, 1), set));
        }
        return builder.toString();
    }

    private static String getTextStyleToHTML(String text, AttributeSet set) {
        StringBuilder prefix = new StringBuilder();
        Stack<String> stack = new Stack();
        if (StyleConstants.isBold(set)) {
            prefix.append(HTMLHelper.HTMLTag.BOLD.getStart());
            stack.push(HTMLHelper.HTMLTag.BOLD.getEnd());
        }
        if (StyleConstants.isItalic(set)) {
            prefix.append(HTMLHelper.HTMLTag.ITALIC.getStart());
            stack.push(HTMLHelper.HTMLTag.ITALIC.getEnd());
        }
        if (StyleConstants.isUnderline(set)) {
            prefix.append(HTMLHelper.HTMLTag.UNDERLINE.getStart());
            stack.push(HTMLHelper.HTMLTag.UNDERLINE.getEnd());
        }

        prefix.append(getOpenFontTag(set));
        stack.push(HTMLHelper.HTMLTag.FONT.getEnd());

        prefix.append(text);
        while (!stack.isEmpty()) {
            prefix.append(stack.pop());
        }
        return prefix.toString();
    }

    private static String getOpenFontTag(AttributeSet set) {
        String font = StyleConstants.getFontFamily(set);
        double size = StyleConstants.getFontSize(set) * 3f / 11;
        Color c = StyleConstants.getForeground(set);
        HTMLHelper.HTMLColor color = HTMLHelper.HTMLColor.getColor(c.getRGB() ^ 0xff000000);
        String result = "<font";
        if (font != null) {
            result += " face=\"" + font + "\"";
        }
        if (color != null) {
            result += " color=\"" + Integer.toHexString(color.getNumber()) + "\"";
        }
        Color back = (Color) set.getAttribute(StyleConstants.Background);

        if (back != null) {
            HTMLHelper.HTMLColor backColor = HTMLHelper.HTMLColor.getColor(back.getRGB() ^ 0xff000000);
            result += " style=\"BACKGROUND-COLOR:" + Integer.toHexString(backColor.getNumber()) + "\"";
        }
        result += " size=" + size;
        result += ">";
        System.out.println("RESULT " + result);
        return result;
    }

    private static void setFont(String tag, MutableAttributeSet att) {

        String[] tmp = tag.substring(1, tag.length() - 1).split(" ");
        System.out.println(Arrays.toString(tmp));
        for (String value : tmp) {
            if (value.startsWith("face")) {
                String font = value.split("=")[1];
                font = font.substring(1, font.length() - 1);
                System.out.println(font);
                StyleConstants.setFontFamily(att, font);
            } else if (value.startsWith("color")) {
                String color = value.split("=")[1];
                color = color.substring(1, color.length() - 1);
                int colorValue = Integer.parseInt(color, 16);
                HTMLColor c = HTMLColor.getColor(colorValue);
                StyleConstants.setForeground(att, new Color(c.getNumber()));
            } else if (value.startsWith("size")) {
                String size = value.split("=")[1];

                double s = Double.parseDouble(size);
                //System.out.println(s*11/3);
                StyleConstants.setFontSize(att, (int) (s * 11 / 3));
            } else if (value.startsWith("style")) {
                String s = value.split("=")[1];
                s = s.substring(1, s.length() - 1);
                String color = s.split(":")[1];
                int colorValue = Integer.parseInt(color, 16);
                HTMLColor c = HTMLColor.getColor(colorValue);
                StyleConstants.setBackground(att, new Color(c.getNumber()));
            }
        }

    }
}
