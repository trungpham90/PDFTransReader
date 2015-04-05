/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

import java.util.HashSet;
import java.util.List;
import javax.swing.DefaultListModel;

/**
 *
 * @author Trung Pham
 */
public class WorkSpacePanel extends javax.swing.JPanel implements IWorkSpacePanelSubject {

    private HashSet<IWorkSpacePanelListener> listeners = new HashSet();
    private DefaultListModel<String> model;

    public WorkSpacePanel() {
        initComponents();
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        wordList = new javax.swing.JList();
        summaryPanel = new javax.swing.JPanel();

        wordList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        wordList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wordListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(wordList);

        jTabbedPane1.addTab("Word List", jScrollPane2);

        javax.swing.GroupLayout summaryPanelLayout = new javax.swing.GroupLayout(summaryPanel);
        summaryPanel.setLayout(summaryPanelLayout);
        summaryPanelLayout.setHorizontalGroup(
            summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );
        summaryPanelLayout.setVerticalGroup(
            summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 444, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Summary", summaryPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void wordListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_wordListMouseClicked
        notifyListener();
    }//GEN-LAST:event_wordListMouseClicked
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel summaryPanel;
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
}