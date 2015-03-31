/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer;

/**
 *
 * @author Trung Pham
 */
public interface IDicDialogSubject {

    public void addListener(IDicDialogListener lis);

    public void removeListener(IDicDialogListener lis);

    public void notifyAddButtonListener();
}
