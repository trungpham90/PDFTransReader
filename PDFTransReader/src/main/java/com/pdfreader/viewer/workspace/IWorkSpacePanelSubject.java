/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

import com.pdfreader.data.PDFReaderWorkSpace;

/**
 *
 * @author Trung Pham
 */
public interface IWorkSpacePanelSubject {

    public void addListener(IWorkSpacePanelListener lis);

    public void removeListener(IWorkSpacePanelListener lis);

    public void notifyListener();
    
    public void notifyEdgeCreated(PDFReaderWorkSpace.PDFSentenceNode source, PDFReaderWorkSpace.PDFSentenceNode target);
    
    public void notifyVertexCreated(String content, int x, int y , int page);
    
    public void notifyVertexRemove(String id);
    
    public void notifyEdgeReomve(String id);
}
