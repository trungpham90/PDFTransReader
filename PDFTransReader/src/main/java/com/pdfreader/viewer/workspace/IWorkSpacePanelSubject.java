/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

/**
 *
 * @author Trung Pham
 */
public interface IWorkSpacePanelSubject {

    public void addListener(IWorkSpacePanelListener lis);

    public void removeListener(IWorkSpacePanelListener lis);

    public void notifyListener();
}
