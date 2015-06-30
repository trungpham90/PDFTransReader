/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

/**
 *
 * @author Trung Pham
 */
public interface ISummaryPanelSubject {
     public void addListener(ISummaryPanelListener lis);

    public void removeListener(ISummaryPanelListener lis);

    public void notifyEdgeCreated();
    
    public void notifyVertexCreated(String content, int x, int y, int page);
    
    public void notifyVertexRemove(String id);
    
    public void notifyEdgeRemove(String id);
    
    public void notifyGoToPage(int page);
}
