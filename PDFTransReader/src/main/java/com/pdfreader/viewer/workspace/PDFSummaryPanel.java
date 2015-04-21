/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

import com.pdfreader.data.PDFReaderWorkSpace;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.jgraph.JGraph;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ListenableDirectedGraph;

/**
 *
 * @author Trung Pham
 */
public class PDFSummaryPanel extends javax.swing.JPanel implements ISummaryPanelSubject {

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
    private static final Color DEFAULT_NODE_COLOR = new Color(0x01A9DB);
    private static final Color DEFAULT_SOURCE_NODE_COLOR = new Color(0xCD5C5C);
    private static final Color DEFAULT_TARGET_NODE_COLOR = new Color(0xFFA500);
    private DefaultGraphCell source, target;
    private JGraphModelAdapter graphAdapter;
    private JGraph graphGraphics;
    private ListenableGraph<PDFReaderWorkSpace.PDFSentenceNode, PDFReaderWorkSpace.PDFSentenceEdge> graph;
    private static final int startX = 50, startY = 50;
    private static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 12);
    private HashSet<ISummaryPanelListener> listeners = new HashSet();

    /**
     * Creates new form PDFSummaryPanel
     */
    public PDFSummaryPanel() {
        super();
        initComponents();
        init();
    }

    private void init() {
        // create a JGraphT graph
        graph = new ListenableDirectedGraph(DefaultEdge.class);
        // create a visualization using JGraph, via an adapter
        graphAdapter = new JGraphModelAdapter(graph);
        graphAdapter.addGraphModelListener(new GraphModelListener() {
            @Override
            public void graphChanged(GraphModelEvent e) {
            }
        });

        graphGraphics = new JGraph(graphAdapter);

        // jgraph.setPreferredSize(DEFAULT_SIZE);
        graphGraphics.setBackground(Color.WHITE);

        add(graphGraphics, BorderLayout.CENTER);
        graphGraphics.addGraphSelectionListener(new GraphSelectionListener() {
            @Override
            public void valueChanged(GraphSelectionEvent e) {
                DefaultGraphCell cell = (DefaultGraphCell) e.getCell();

                AttributeMap map = cell.getAttributes();
                Rectangle2D b = GraphConstants.getBounds(map);

            }
        });

        graphGraphics.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent ex) {
                DefaultGraphCell cell = (DefaultGraphCell) graphGraphics.getFirstCellForLocation(ex.getX(), ex.getY());                 
                if(SwingUtilities.isRightMouseButton(ex)){
                      
                     JPopupMenu menu = new JPopupMenu();
                     JMenuItem item = new JMenuItem("Create new vertex");
                     item.addActionListener(new ActionListener() {

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            
                            notifyVertexCreated("", ex.getX(), ex.getY(), -1);
                            
                            
                        }
                    });
                     
                     menu.add(item);
                     menu.show(PDFSummaryPanel.this, ex.getX(), ex.getY());
                     
                }else if(ex.getClickCount() > 1){
                    if(cell != null){
                        Object o = cell.getUserObject();
                        if(o instanceof PDFReaderWorkSpace.PDFSentenceNode){
                            PDFReaderWorkSpace.PDFSentenceNode node = (PDFReaderWorkSpace.PDFSentenceNode) o;
                            VertexChangeDialog dialog = new VertexChangeDialog(node);
                            dialog.setLocationRelativeTo(PDFSummaryPanel.this);
                            dialog.setVisible(true);
                            graphGraphics.refresh();
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                graphGraphics.setDragEnabled(true);
                graphGraphics.setMoveable(true);
                if (source != null && target != null) {
                    notifyEdgeCreated();
                }
                if (source != null) {
                    setVertexAttribute(source, DEFAULT_NODE_COLOR, false, 0, 0, 0, 0);
                }
                source = null;
                if (target != null) {
                    setVertexAttribute(target, DEFAULT_NODE_COLOR, false, 0, 0, 0, 0);
                }
                target = null;
            }
        });
        graphGraphics.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
                    DefaultGraphCell cell = (DefaultGraphCell) graphGraphics.getFirstCellForLocation(e.getX(), e.getY());
                    if (cell == null) {
                        return;
                    }

                    if (source == null) {
                        source = cell;
                        graphGraphics.setDragEnabled(false);
                        graphGraphics.setMoveable(false);
                        setVertexAttribute(source, DEFAULT_SOURCE_NODE_COLOR, false, 0, 0, 0, 0);

                    } else if (!isTwoCellEqual(cell, source)) {
                        if (target != null) {
                            setVertexAttribute(target, DEFAULT_NODE_COLOR, false, 0, 0, 0, 0);
                        }
                        target = cell;
                        if (target != null) {
                            setVertexAttribute(target, DEFAULT_TARGET_NODE_COLOR, false, 0, 0, 0, 0);
                        }
                    }
                }
            }
        });


        graphGraphics.setConnectable(true);
        graphGraphics.revalidate();
        revalidate();

        // that's all there is to it!...
    }

    private boolean isTwoCellEqual(DefaultGraphCell a, DefaultGraphCell b) {
        Object first = a.getUserObject();
        Object second = b.getUserObject();
        if (first instanceof PDFReaderWorkSpace.PDFSentenceNode) {
            if (second instanceof PDFReaderWorkSpace.PDFSentenceNode) {
                PDFReaderWorkSpace.PDFSentenceNode nodeA = (PDFReaderWorkSpace.PDFSentenceNode) first;
                PDFReaderWorkSpace.PDFSentenceNode nodeB = (PDFReaderWorkSpace.PDFSentenceNode) second;
                return nodeA.getId().equals(nodeB.getId());
            }
        } else if (first instanceof PDFReaderWorkSpace.PDFSentenceEdge) {
            if (second instanceof PDFReaderWorkSpace.PDFSentenceEdge) {
                PDFReaderWorkSpace.PDFSentenceEdge edgeA = (PDFReaderWorkSpace.PDFSentenceEdge) first;
                PDFReaderWorkSpace.PDFSentenceEdge edgeB = (PDFReaderWorkSpace.PDFSentenceEdge) second;
                return edgeA.getId().equals(edgeB.getId());
            }
        }
        return false;
    }
    
    public void addEdge(PDFReaderWorkSpace.PDFSentenceEdge edge,PDFReaderWorkSpace.PDFSentenceNode source,PDFReaderWorkSpace.PDFSentenceNode target){
        graph.addEdge(source, target, edge);
    }

    public void addVertex(PDFReaderWorkSpace.PDFSentenceNode node, int x, int y){
        graph.addVertex(node);
        positionVertexAt(node, DEFAULT_NODE_COLOR, x, y);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                graphGraphics.refresh();
            }
        });
    }
    
    
    public void addVertex(PDFReaderWorkSpace.PDFSentenceNode node) {
        addVertex(node, startX, startY);

    }

    private void positionVertexAt(PDFReaderWorkSpace.PDFSentenceNode vertex, Color backGround, int x, int y) {
        DefaultGraphCell cell = graphAdapter.getVertexCell(vertex);

        setVertexAttribute(cell, backGround, true, x, y, getEstimatedLength(vertex.toString()), getEstimatedHeight(vertex.toString()));
    }

    /**
     * This method return the expected width for a cell, with the given content.
     *
     * @param line
     * @return
     */
    private int getEstimatedHeight(String line) {
        return 90;
    }

    /**
     * This method return the expected length for a cell, with the given
     * content.
     *
     * @param line
     * @return
     */
    private int getEstimatedLength(String line) {
        int length = (line.length() / 3) * 5;
        return Math.max(length, 200);
    }

    private void setVertexAttribute(DefaultGraphCell cell, Color backGround, boolean setPosition, int x, int y, int w, int h) {
        Map attr = cell.getAttributes();
        GraphConstants.setFont(attr, DEFAULT_FONT);
        if (setPosition) {
            GraphConstants.setBounds(attr, new Rectangle(x, y, w, h));
        }
        GraphConstants.setEditable(attr, false);
        GraphConstants.setBorder(attr, BorderFactory.createLineBorder(Color.CYAN.darker().darker(), 2, true));
        GraphConstants.setGradientColor(attr, backGround);
        GraphConstants.setBackground(attr, backGround);
        GraphConstants.setForeground(attr, Color.BLACK);
        Map cellAttr = new HashMap();
        cellAttr.put(cell, attr);

        graphAdapter.edit(cellAttr, null, null, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        jLabel1.setText("Tip: Hold Cltr and Drag your mouse to draw an edge!");
        add(jLabel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addListener(ISummaryPanelListener lis) {
        listeners.add(lis);
    }

    @Override
    public void removeListener(ISummaryPanelListener lis) {
        listeners.remove(lis);
    }

    @Override
    public void notifyEdgeCreated() {
        for (ISummaryPanelListener lis : listeners) {
            lis.edgeCreated((PDFReaderWorkSpace.PDFSentenceNode) source.getUserObject(), (PDFReaderWorkSpace.PDFSentenceNode) target.getUserObject());
        }
    }

    @Override
    public void notifyVertexCreated(String content , int x, int y, int page) {
        for(ISummaryPanelListener lis : listeners){
            lis.vertexCreated(content, x, y , page);
        }
    }
}
