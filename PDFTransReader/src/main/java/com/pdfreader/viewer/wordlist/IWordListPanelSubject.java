/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.wordlist;

/**
 *
 * @author Trung Pham
 */
public interface IWordListPanelSubject {

    public void addListener(IWordListPanelListener lis);

    public void removeListener(IWordListPanelListener lis);

    public void notifyListener();
}
