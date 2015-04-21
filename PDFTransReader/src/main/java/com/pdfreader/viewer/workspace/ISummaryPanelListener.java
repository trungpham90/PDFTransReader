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
public interface ISummaryPanelListener {
    public void edgeCreated(PDFReaderWorkSpace.PDFSentenceNode source, PDFReaderWorkSpace.PDFSentenceNode target);
    
    public void vertexCreated(String content , int x, int y, int page);
}
