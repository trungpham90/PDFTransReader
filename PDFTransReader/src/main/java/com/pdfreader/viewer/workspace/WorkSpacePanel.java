/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

import com.pdfreader.data.PDFReaderWorkSpace;
import com.pdfreader.data.PDFReaderWorkSpace.PDFSentenceNode;
import com.pdfreader.data.PDFReaderWorkSpace.PDFUnprocessText;
import java.util.HashSet;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Trung Pham
 */
public class WorkSpacePanel extends javax.swing.JPanel implements IWorkSpacePanelSubject, ISummaryPanelListener , UnprocessedMapPanel.UnprocessedMapPanelListener {
    
    private HashSet<IWorkSpacePanelListener> listeners = new HashSet();
    private DefaultListModel<String> model;
    private final int pageNum;
    
    public WorkSpacePanel(int pageNum) {
        this.pageNum = pageNum;
        initComponents();
        init();
    }
    
    private void init() {
        pDFMapPanel.getSummaryPanel().setPageNum(pageNum);
        pDFMapPanel.getUnprocessedMapPanel().setPage(pageNum);
        pDFMapPanel.getSummaryPanel().addListener(this);
        pDFMapPanel.getUnprocessedMapPanel().addListener(this);
    }
    
    public void addUnprocessText(PDFReaderWorkSpace.PDFUnprocessText node) {
        pDFMapPanel.getUnprocessedMapPanel().addUnprocessedText(node);
    }
    
    public void removeUnprocessText(PDFReaderWorkSpace.PDFUnprocessText node) throws BadLocationException{
        pDFMapPanel.getUnprocessedMapPanel().removeUnprocssedText(node);
    }
    
    public void addVertex(PDFReaderWorkSpace.PDFSentenceNode node){
        pDFMapPanel.getSummaryPanel().addVertex(node);
    }
    
    public void addVertex(PDFReaderWorkSpace.PDFSentenceNode node, int x, int y) {
        pDFMapPanel.getSummaryPanel().addVertex(node, x, y);
    }
    
    public void addEdge(PDFReaderWorkSpace.PDFSentenceEdge edge, PDFReaderWorkSpace.PDFSentenceNode source, PDFReaderWorkSpace.PDFSentenceNode target) {
        pDFMapPanel.getSummaryPanel().addEdge(edge, source, target);
    }
    
    public void populateListContent(List<String> content) {
        model = new DefaultListModel();
        for (String word : content) {
            model.addElement(word);
        }
        wordList.setModel(model);
        wordList.revalidate();
    }
    
    public void addWord(String word) {
        model.addElement(word);
        wordList.setModel(model);
        wordList.revalidate();
    }
    
    public void removeWord(String word) {
        model.removeElement(word);
        wordList.setModel(model);
        wordList.revalidate();
    }
    
    public String getSelectedWord() {
        if (wordList.getSelectedValue() == null) {
            return null;
        }
        return wordList.getSelectedValue().toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        wordList = new javax.swing.JList();
        pDFMapPanel = new com.pdfreader.viewer.workspace.PDFMapPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        wordList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        wordList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wordListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(wordList);

        jTabbedPane1.addTab("Word List", jScrollPane2);
        jTabbedPane1.addTab("Summary", pDFMapPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 948, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void wordListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wordListMouseClicked
        notifyListener();
    }//GEN-LAST:event_wordListMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.pdfreader.viewer.workspace.PDFMapPanel pDFMapPanel;
    private javax.swing.JList wordList;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addListener(IWorkSpacePanelListener lis) {
        listeners.add(lis);
    }
    
    @Override
    public void removeListener(IWorkSpacePanelListener lis) {
        listeners.remove(lis);
    }
    
    @Override
    public void notifyListener() {
        for (IWorkSpacePanelListener list : listeners) {
            list.mouseClick();
        }
    }
    
    @Override
    public void edgeCreated(PDFSentenceNode source, PDFSentenceNode target) {
        notifyEdgeCreated(source, target);
    }
    
    @Override
    public void notifyEdgeCreated(PDFSentenceNode source, PDFSentenceNode target) {
        for (IWorkSpacePanelListener lis : listeners) {
            lis.edgeCreated(source, target);
        }
    }
    
    @Override
    public void notifyVertexCreated(String content, int x, int y , int page) {
        for (IWorkSpacePanelListener lis : listeners) {
            lis.vertexCreated(content, x, y , page);
        }
    }
    
    @Override
    public void vertexCreated(String content, int x, int y , int page) {
        notifyVertexCreated(content, x, y, page);
    }

    @Override
    public void notifyVertexRemove(String id) {
        for(IWorkSpacePanelListener lis : listeners){
            lis.vertexRemoved(id);
        }
    }

    @Override
    public void vertexRemove(String id) {
        notifyVertexRemove(id);
    }

    @Override
    public void edgeRemove(String id) {
        notifyVertexRemove(id);
    }

    @Override
    public void notifyEdgeReomve(String id) {
       for(IWorkSpacePanelListener lis : listeners){
            lis.edgeRemoved(id);
        }
    }

    @Override
    public void addToWorkSpace(PDFUnprocessText txt) {
       notifyUnprocessTextAdded(txt);
    }

    @Override
    public void removeFromWorkSpace(PDFUnprocessText txt) {
        notifyUnprocessTextRemove(txt);
    }

    @Override
    public void notifyUnprocessTextAdded(PDFUnprocessText text) {
        for(IWorkSpacePanelListener lis : listeners){
            lis.unprocessedTextAdd(text);
        }
    }

    @Override
    public void notifyUnprocessTextRemove(PDFUnprocessText text) {
        for(IWorkSpacePanelListener lis : listeners){
            lis.unprocessedTextRemove(text);
        }
    }
}
