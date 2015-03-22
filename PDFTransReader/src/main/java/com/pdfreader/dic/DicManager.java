/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.dic;

import com.pdfreader.dic.english.EnDicParser;
import com.pdfreader.dic.vietnamese.ViDicParser;

/**
 *
 * @author Trung Pham
 */
public class DicManager {
    
    public static enum Language {
        
        Vietnamese,
        English;
    }
    
    public static IDic getDictionary(Language lang) {
        switch (lang) {
            case Vietnamese:
                return ViDicParser.getInstance();
            case English:
                return EnDicParser.getInstance();            
            default:
                return EnDicParser.getInstance();            
        }
    }
}
