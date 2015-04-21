/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader;

import com.pdfreader.data.PDFReaderWorkSpace;
import com.pdfreader.data.PDFReaderWorkSpace.PDFSentenceNode;
import com.pdfreader.dic.DicManager;
import com.pdfreader.dic.DicVO;
import com.pdfreader.dic.IDic;
import com.pdfreader.reader.PDFFileReader;
import com.pdfreader.reader.PDFWord;
import com.pdfreader.util.PDFReaderUtil;
import com.pdfreader.viewer.IDicDialogListener;
import com.pdfreader.viewer.PDFDicDialog;
import com.pdfreader.viewer.PDFViewerPanel;
import com.pdfreader.viewer.workspace.IWorkSpacePanelListener;
import com.pdfreader.viewer.workspace.WorkSpacePanel;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import org.apache.pdfbox.util.ExtensionFileFilter;

/**
 *
 * @author Trung Pham
 */
public class DisplayDicPanel extends javax.swing.JPanel {

    private PDFDicDialog dicDialog;
    private PDFFileReader reader;
    private PDFViewerPanel viewPanel;
    private PDFReaderWorkSpace workspace;
    private WorkSpacePanel listPanel;
    private int page = 0;
    private int totalPage = 0;
    private IDic dic;
    private Map<String, List<PDFWord>> wordMap;
    private List<PDFWord> selectedList = null;

    /**
     * Creates new form DisplayDicPanel
     */
    public DisplayDicPanel(java.awt.Frame parent) {
        dicDialog = new PDFDicDialog(parent, false);
        initComponents();
        init();
    }

    private void init() {
        languageComboBox.setModel(new DefaultComboBoxModel(DicManager.Language.values()));
        languageComboBox.setSelectedItem(DicManager.Language.English);
        languageComboBox.revalidate();
        listPanel = new WorkSpacePanel();
        listPanel.addListener(new IWorkSpacePanelListener() {
            @Override
            public void mouseClick() {
                String word = listPanel.getSelectedWord();
                if (word != null) {
                    showDicDialog(word);
                    viewPanel.setHighLight(wordMap.get(word));
                }
            }

            @Override
            public void edgeCreated(PDFSentenceNode source, PDFSentenceNode target) {
                PDFReaderWorkSpace.PDFSentenceEdge edge = workspace.createSentenceEdge(source, target, "");
                listPanel.addEdge(edge, source, target);
            }

            @Override
            public void vertexCreated(String content, int x, int y , int p) {
                if(p == -1){
                    p = page;
                }
                PDFReaderWorkSpace.PDFSentenceNode node = workspace.createSentenceNode(content, p);
                listPanel.addVertex(node, x, y);
            }
        });

        rightPanel.add(listPanel, BorderLayout.CENTER);
        dic = DicManager.getDictionary(DicManager.Language.English);
        dicDialog.addListener(new IDicDialogListener() {
            @Override
            public void addButtonClick() {
                if (workspace != null) {
                    String word = dicDialog.getWord();
                    if (workspace.containsWord(word)) {
                        wordMap.remove(word);
                        workspace.removeWordFromDic(word);
                        listPanel.removeWord(word);
                    } else {
                        List<PDFWord> list = reader.getMatchingWord(word);
                        if (!list.isEmpty()) {
                            wordMap.put(word, list);
                        }
                        workspace.addWordToDic(word);
                        listPanel.addWord(word);
                    }
                }
            }
        });

    }

    private void showDicDialog(String word) {
        DicVO content = dic.getWordDefinition(word);
        if (content != null) {
            if (workspace.containsWord(content.getWord())) {
                dicDialog.setAddButtonTitle(PDFDicDialog.REMOVE_FROM_WORD_LIST);
            } else {
                dicDialog.setAddButtonTitle(PDFDicDialog.ADD_TO_WORD_LIST);
            }
            dicDialog.setContent(content);

            dicDialog.setLocation(MouseInfo.getPointerInfo().getLocation());
            dicDialog.setVisible(true);
        }
    }

    private void setPage(int num) {
        try {
            page = num;
            viewPanel.setPage(reader.getPage(page));
            pageTextField.setText("" + (page + 1));
            wordMap = new HashMap();
            for (String word : workspace.getWordList()) {
                List<PDFWord> list = reader.getMatchingWord(word);
                if (!list.isEmpty()) {
                    wordMap.put(word, list);
                }
            }
            listPanel.populateListContent(new ArrayList(wordMap.keySet()));
        } catch (IOException ex) {
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        splitPane = new javax.swing.JSplitPane();
        leftPanel = new javax.swing.JPanel();
        rightPanel = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        languageComboBox = new javax.swing.JComboBox();
        showToggleButton = new javax.swing.JToggleButton();
        openButton = new javax.swing.JButton();
        pageTextField = new javax.swing.JTextField();
        totalPageLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        splitPane.setDividerLocation(800);

        leftPanel.setLayout(new java.awt.BorderLayout());
        splitPane.setLeftComponent(leftPanel);

        rightPanel.setLayout(new java.awt.BorderLayout());
        splitPane.setRightComponent(rightPanel);

        searchTextField.setText(" ");

        languageComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        languageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                languageComboBoxActionPerformed(evt);
            }
        });

        showToggleButton.setSelected(true);
        showToggleButton.setText("Learning Corner");
        showToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showToggleButtonActionPerformed(evt);
            }
        });

        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        pageTextField.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        pageTextField.setText("0");
        pageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pageTextFieldActionPerformed(evt);
            }
        });

        totalPageLabel.setText("/ 0");

        jButton1.setText("<-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("->");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Search:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitPane)
            .addGroup(layout.createSequentialGroup()
                .addComponent(openButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalPageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 355, Short.MAX_VALUE)
                .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(showToggleButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showToggleButton)
                    .addComponent(openButton)
                    .addComponent(pageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalPageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitPane, javax.swing.GroupLayout.DEFAULT_SIZE, 630, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void languageComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_languageComboBoxActionPerformed
        languageComboBox.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(
                new Runnable() {
                    @Override
                    public void run() {
                        dic = DicManager.getDictionary(DicManager.Language.valueOf(languageComboBox.getSelectedItem().toString()));
                    }
                });
        languageComboBox.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_languageComboBoxActionPerformed

    private void showToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showToggleButtonActionPerformed

        if (showToggleButton.isSelected()) {
            splitPane.setDividerLocation(0.75f);

        } else {
            splitPane.setDividerLocation(1.0f);
        }
    }//GEN-LAST:event_showToggleButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed

        JFileChooser chooser = new JFileChooser();
        ExtensionFileFilter pdfFilter = new ExtensionFileFilter(new String[]{"PDF"}, "PDF Files");
        chooser.setFileFilter(pdfFilter);
        int result = chooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File file = chooser.getSelectedFile();
                setCursor(new Cursor(Cursor.WAIT_CURSOR));

                reader = new PDFFileReader(file);
                viewPanel = new PDFViewerPanel();
                workspace = new PDFReaderWorkSpace();
                page = 0;
                totalPage = reader.getNumPages();
                totalPageLabel.setText("/ " + totalPage);
                pageTextField.setText("1");
                viewPanel.addListener(new PDFViewerPanel.ViewerSelectionListener() {
                    @Override
                    public void selectionTrigger(int x1, int y1, int x2, int y2) {
                        try {
                            selectedList = reader.getStringAt(page, x1, y1, x2, y2);
                            viewPanel.setHighLight(selectedList);
                            viewPanel.repaint();
                        } catch (IOException ex) {
                        }
                    }

                    @Override
                    public void clickTrigger(MouseEvent e) {
                        int x = e.getX();
                        int y = e.getY();
                        int count = e.getClickCount();

                        if (!SwingUtilities.isRightMouseButton(e)) {
                            selectedList = null;//Clear previous selection
                            if (count > 1) {
                                try {
                                    PDFWord word = reader.getWordAt(page, x, y);
                                    if (word != null) {
                                        showDicDialog(word.getWord());
                                        List<PDFWord> list = new ArrayList();
                                        list.add(word);
                                        viewPanel.setHighLight(list);
                                        viewPanel.repaint();
                                    }

                                } catch (IOException ex) {
                                }
                            } else {
                                viewPanel.clearHighLight();
                            }
                        } else {
                            if (selectedList != null && !selectedList.isEmpty()) {
                                JPopupMenu popup = new JPopupMenu();
                                JMenuItem item = new JMenuItem("Add selected text to summary");
                                item.addActionListener(new ActionListener() {

                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        String content = PDFReaderUtil.getTextFromPDFList(selectedList);
                                        PDFReaderWorkSpace.PDFSentenceNode node = workspace.createSentenceNode(content, page);
                                        listPanel.addVertex(node);
                                    }
                                });
                                popup.add(item);
                                popup.show(e.getComponent(), x, y);
                            }
                        }
                    }
                });
                setPage(0);
                leftPanel.removeAll();
                JScrollPane scroll = new JScrollPane(viewPanel);
                scroll.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);//Improve the performance significantly!

                leftPanel.add(scroll, BorderLayout.CENTER);
                leftPanel.revalidate();
                leftPanel.repaint();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            } catch (IOException ex) {
            }

        }
    }//GEN-LAST:event_openButtonActionPerformed

    private void pageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pageTextFieldActionPerformed
        String text = pageTextField.getText();
        try {
            int tmp = Integer.parseInt(text);
            if (tmp <= totalPage && tmp > 0) {
                setPage(tmp - 1);
            } else {
                pageTextField.setText("" + (page + 1));
            }
        } catch (NumberFormatException ex) {
            pageTextField.setText("" + (page + 1));
        }

    }//GEN-LAST:event_pageTextFieldActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (page > 0) {
            setPage(page - 1);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        if (page + 1 < totalPage) {
            setPage(page + 1);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox languageComboBox;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton openButton;
    private javax.swing.JTextField pageTextField;
    private javax.swing.JPanel rightPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JToggleButton showToggleButton;
    private javax.swing.JSplitPane splitPane;
    private javax.swing.JLabel totalPageLabel;
    // End of variables declaration//GEN-END:variables
}
