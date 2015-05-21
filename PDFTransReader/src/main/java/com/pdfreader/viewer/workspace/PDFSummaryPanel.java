/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

import com.pdfreader.data.PDFReaderWorkSpace;
import com.pdfreader.util.ColorIcon;
import com.pdfreader.util.PDFReaderUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.jgraph.JGraph;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.ListenableDirectedGraph;

/**
 *
 * @author Trung Pham
 */
public class PDFSummaryPanel extends javax.swing.JPanel implements ISummaryPanelSubject {

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);
    private static final Color BLUE = new Color(0x66CCFF);
    private static final Color GREEN = new Color(0x00FF99);
    private static final Color PINK = new Color(0xFF99FF);
    private static final Color PURPLE = new Color(0xCC99FF);
    private static final Color WHITE = new Color(0xFFFFFF);
    private static final Color YELLOW = new Color(0xFFFF99);

    static enum BackgroundColor {

        Blue("Blue", BLUE),
        Green("Green", GREEN),
        Pink("Pink", PINK),
        Purple("Purple", PURPLE),
        White("White", WHITE),
        Yellow("Yellow", YELLOW);
        private String name;
        private Color color;

        private BackgroundColor(String name, Color color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public Color getColor() {
            return color;
        }
    }
    private static final Color DEFAULT_NODE_COLOR = YELLOW;
    private static final Color DEFAULT_SOURCE_NODE_COLOR = new Color(0xADD8E6);
    private static final Color DEFAULT_TARGET_NODE_COLOR = new Color(0x669900);
    private DefaultGraphCell source, target;
    private JGraphModelAdapter graphAdapter;
    private JGraph graphGraphics;
    private ListenableGraph<PDFReaderWorkSpace.PDFSentenceNode, PDFReaderWorkSpace.PDFSentenceEdge> graph;
    private static final int startX = 50, startY = 50;
    private static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 12);
    private HashSet<ISummaryPanelListener> listeners = new HashSet();
    private int pageNum;
    private int lastX = 0, lastY = 0;
    private static final int COLLAPSE_SIZE_X = 30, COLLAPSE_SIZE_Y = 30;
    private static boolean isSourceHide = false, isTargetHide = false;
    private Timer timer;

    /**
     * Creates new form PDFSummaryPanel
     */
    public PDFSummaryPanel() {
        super();
        initComponents();
        init();
    }

    public void setPageNum(int page) {
        this.pageNum = page;
    }

    private void init() {

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                DefaultGraphCell cell = (DefaultGraphCell) graphGraphics.getFirstCellForLocation(lastX, lastY);
                if (cell == null || !(cell.getUserObject() instanceof PDFReaderWorkSpace.PDFSentenceNode)) {
                    return;
                }
                PDFReaderWorkSpace.PDFSentenceNode node = (PDFReaderWorkSpace.PDFSentenceNode) cell.getUserObject();
                JPopupMenu menu = new JPopupMenu();
                JLabel label = new JLabel("<html>" + node.getContent() + "</html>");
                menu.add(label);
                menu.setPopupSize(getEstimatedLength(PDFReaderUtil.getTextWithoutHTMLTag(node.getContent())), getEstimatedHeight(PDFReaderUtil.getTextWithoutHTMLTag(node.getContent())));
                menu.show(PDFSummaryPanel.this, lastX, lastY);

            }
        });
        // create a JGraphT graph
        graph = new ListenableDirectedGraph(DefaultEdge.class);
        // create a visualization using JGraph, via an adapter      

        graphAdapter = new JGraphModelAdapter(graph);
        graphAdapter.addGraphModelListener(new GraphModelListener() {
            @Override
            public void graphChanged(GraphModelEvent e) {
            }
        });
        GraphLayoutCache cache = new GraphLayoutCache(graphAdapter, new DefaultCellViewFactory());
        graphGraphics = new JGraph(graphAdapter, cache);
        
        // jgraph.setPreferredSize(DEFAULT_SIZE);
        graphGraphics.setBackground(Color.WHITE);

        add(graphGraphics, BorderLayout.CENTER);
        
        
        
        graphGraphics.addGraphSelectionListener(new GraphSelectionListener() {
            @Override
            public void valueChanged(GraphSelectionEvent e) {

            }
        });

        graphGraphics.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent ex) {
                final DefaultGraphCell cell = (DefaultGraphCell) graphGraphics.getFirstCellForLocation(ex.getX(), ex.getY());
                //   VertexView.renderer = new MyVertexRenderer();
                if (SwingUtilities.isRightMouseButton(ex)) {
                    timer.stop();
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem item = new JMenuItem("Create new vertex");
                    item.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            notifyVertexCreated("", ex.getX(), ex.getY(), -1);
                        }
                    });
                    menu.add(item);                    
                    if (graphGraphics.getSelectionCells().length > 1) {


                        JMenuItem collapseGroup = new JMenuItem("Collapse selected nodes");
                        collapseGroup.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ArrayList<PDFReaderWorkSpace.PDFSentenceNode> list = new ArrayList();
                                for (Object o : graphGraphics.getSelectionCells()) {
                                    if (o instanceof DefaultGraphCell) {
                                        DefaultGraphCell tmp = (DefaultGraphCell) o;
                                        if (tmp.getUserObject() instanceof PDFReaderWorkSpace.PDFSentenceNode) {
                                            PDFReaderWorkSpace.PDFSentenceNode node = (PDFReaderWorkSpace.PDFSentenceNode) tmp.getUserObject();
                                            if (!node.isHide()) {
                                                collapseNode(node);
                                            }
                                        }
                                    }
                                }
                            }
                        });
                        menu.add(collapseGroup);

                    }

                    if (cell != null) {
                        final Object o = cell.getUserObject();
                        if (o instanceof PDFReaderWorkSpace.PDFSentenceNode) {
                            final PDFReaderWorkSpace.PDFSentenceNode node = (PDFReaderWorkSpace.PDFSentenceNode) o;
                            JMenuItem remove = new JMenuItem("Remove this node");
                            remove.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    PDFReaderWorkSpace.PDFSentenceNode node = (PDFReaderWorkSpace.PDFSentenceNode) o;
                                    notifyVertexRemove(node.getId());
                                    removeVertex(node);
                                }
                            });
                            menu.add(remove);

                            if (node.isHide()) {
                                JMenuItem unHide = new JMenuItem("Expand");
                                unHide.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        expandNode(node);
                                    }
                                });
                                menu.add(unHide);
                            } else {
                                JMenuItem hide = new JMenuItem("Collapse");
                                hide.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        collapseNode(node);
                                    }
                                });
                                menu.add(hide);
                            }

                            //Add button to change color for this node
                            menu.add(new JSeparator());
                            for (final BackgroundColor color : BackgroundColor.values()) {
                                JMenuItem c = new JMenuItem(color.getName());
                                c.setIcon(new ColorIcon(15, 15, color.getColor()));
                                c.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        PDFReaderWorkSpace.PDFSentenceNode node = (PDFReaderWorkSpace.PDFSentenceNode) o;
                                        node.setColor(color.getColor());
                                        setVertexAttribute(cell, color.getColor(), false, -1, -1, -1, -1);
                                    }
                                });
                                menu.add(c);
                            }

                        } else if (o instanceof PDFReaderWorkSpace.PDFSentenceEdge) {
                            JMenuItem remove = new JMenuItem("Remove this edge");
                            remove.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    PDFReaderWorkSpace.PDFSentenceEdge edge = (PDFReaderWorkSpace.PDFSentenceEdge) o;
                                    removeEdge(edge);
                                }
                            });
                            menu.add(remove);
                        }
                    } else {
                        JMenuItem collapse = new JMenuItem("Collapse all nodes");
                        collapse.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                collapseAll();
                            }
                        });
                        menu.add(collapse);
                    }


                    menu.show(PDFSummaryPanel.this, ex.getX(), ex.getY());

                } else if (ex.getClickCount() > 1) {
                    if (cell != null) {
                        timer.stop();
                        final Object o = cell.getUserObject();
                        if (o instanceof PDFReaderWorkSpace.PDFSentenceNode) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    PDFReaderWorkSpace.PDFSentenceNode node = (PDFReaderWorkSpace.PDFSentenceNode) o;
                                    VertexChangeDialog dialog = new VertexChangeDialog(node, pageNum);
                                    dialog.pack();
                                    dialog.setModal(true);
                                    dialog.setLocationRelativeTo(PDFSummaryPanel.this);
                                    dialog.setVisible(true);



                                    graphGraphics.getSelectionModel().clearSelection();

                                    graphGraphics.refresh();
                                }
                            });
                        } else if (o instanceof PDFReaderWorkSpace.PDFSentenceEdge) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    PDFReaderWorkSpace.PDFSentenceEdge edge = (PDFReaderWorkSpace.PDFSentenceEdge) o;
                                    EdgeChangeLabelDialog dialog = new EdgeChangeLabelDialog(edge);
                                    dialog.pack();
                                    dialog.setModal(true);
                                    dialog.setLocationRelativeTo(PDFSummaryPanel.this);
                                    dialog.setVisible(true);
                                    Map attr = cell.getAttributes();
                                    Map map = new HashMap();
                                    map.put(cell, attr);
                                    graphAdapter.edit(map, null, null, null);
                                    graphGraphics.getSelectionModel().clearSelection();
                                    graphGraphics.refresh();
                                }
                            });
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
                    PDFReaderWorkSpace.PDFSentenceNode node = (PDFReaderWorkSpace.PDFSentenceNode) source.getUserObject();
                    setVertexAttribute(source, node.getColor(), false, 0, 0, 0, 0);
                    if (isSourceHide) {
                        collapseNode(node);
                        isSourceHide = false;
                    }
                }
                source = null;
                if (target != null) {
                    PDFReaderWorkSpace.PDFSentenceNode node = (PDFReaderWorkSpace.PDFSentenceNode) target.getUserObject();
                    setVertexAttribute(target, node.getColor(), false, 0, 0, 0, 0);
                    if (isTargetHide) {
                        collapseNode(node);
                        isTargetHide = false;
                    }
                }
                target = null;
            }
        });
        graphGraphics.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                timer.stop();
                if ((e.getModifiers() & InputEvent.CTRL_MASK) == InputEvent.CTRL_MASK) {
                    DefaultGraphCell cell = (DefaultGraphCell) graphGraphics.getFirstCellForLocation(e.getX(), e.getY());
                    if (cell == null) {
                        return;
                    }
                    if (!(cell.getUserObject() instanceof PDFReaderWorkSpace.PDFSentenceNode)) {
                        return;
                    }
                    PDFReaderWorkSpace.PDFSentenceNode curNode = (PDFReaderWorkSpace.PDFSentenceNode) cell.getUserObject();
                    if (source == null) {
                        source = cell;
                        graphGraphics.setDragEnabled(false);
                        graphGraphics.setMoveable(false);
                        setVertexAttribute(source, DEFAULT_SOURCE_NODE_COLOR, false, 0, 0, 0, 0);
                        if (curNode.isHide()) {
                            isSourceHide = true;
                            expandNode(curNode);
                        }

                    } else if (!isTwoCellEqual(cell, source)) {
                        if (target != null) {
                            PDFReaderWorkSpace.PDFSentenceNode node = (PDFReaderWorkSpace.PDFSentenceNode) target.getUserObject();
                            setVertexAttribute(target, node.getColor(), false, 0, 0, 0, 0);
                            if (isTargetHide) {
                                collapseNode(node);
                            }
                        }
                        target = cell;
                        if (target != null) {
                            setVertexAttribute(target, DEFAULT_TARGET_NODE_COLOR, false, 0, 0, 0, 0);
                            if (curNode.isHide()) {
                                isTargetHide = true;
                                expandNode(curNode);
                            }
                        }
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

                lastX = e.getX();
                lastY = e.getY();
                timer.restart();
            }
        });
        graphGraphics.setAntiAliased(true);
        graphGraphics.setBendable(true);
        graphGraphics.setConnectable(true);
        graphGraphics.revalidate();


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

    public void addEdge(PDFReaderWorkSpace.PDFSentenceEdge edge, PDFReaderWorkSpace.PDFSentenceNode source, PDFReaderWorkSpace.PDFSentenceNode target) {
        graph.addEdge(source, target, edge);
        DefaultGraphCell cell = graphAdapter.getEdgeCell(edge);
        Map attr = cell.getAttributes();
        GraphConstants.setLineWidth(attr, 5);
        GraphConstants.setEditable(attr, false);
        GraphConstants.setLineStyle(attr, GraphConstants.STYLE_SPLINE);
        GraphConstants.setRouting(attr, GraphConstants.getROUTING_SIMPLE());
        Map cellAttr = new HashMap();
        cellAttr.put(cell, attr);

        graphAdapter.edit(cellAttr, null, null, null);
    }

    public void addVertex(PDFReaderWorkSpace.PDFSentenceNode node, int x, int y) {
        graph.addVertex(node);
        if (node.getColor() == null) {
            node.setColor(DEFAULT_NODE_COLOR);
        }
        positionVertexAt(node, x, y);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                graphGraphics.refresh();
            }
        });
    }

    public void collapseAll() {
        for (PDFReaderWorkSpace.PDFSentenceNode node : graph.vertexSet()) {
            if (node.isHide()) {
                continue;
            }
            collapseNode(node);
        }
    }

    private void collapseNode(PDFReaderWorkSpace.PDFSentenceNode node) {
        DefaultGraphCell cell = graphAdapter.getVertexCell(node);
        node.setHide(true);
        Map attr = cell.getAttributes();
        Rectangle2D bound = GraphConstants.getBounds(attr);
        setVertexAttribute(cell, GraphConstants.getBackground(attr), true, (int) bound.getX(), (int) bound.getY(), COLLAPSE_SIZE_X, COLLAPSE_SIZE_Y);
        graphGraphics.refresh();
    }

    private void expandNode(PDFReaderWorkSpace.PDFSentenceNode node) {
        DefaultGraphCell cell = graphAdapter.getVertexCell(node);
        node.setHide(false);
        Map attr = cell.getAttributes();
        Rectangle2D bound = GraphConstants.getBounds(attr);
        setVertexAttribute(cell, GraphConstants.getBackground(attr), true, (int) bound.getX(), (int) bound.getY(), getEstimatedLength(PDFReaderUtil.getTextWithoutHTMLTag(node.getContent())), getEstimatedHeight(PDFReaderUtil.getTextWithoutHTMLTag(node.getContent())));
        graphGraphics.refresh();
    }

    public void removeVertex(PDFReaderWorkSpace.PDFSentenceNode node) {
        graph.removeVertex(node);
        for (PDFReaderWorkSpace.PDFSentenceEdge edge : node.getEdgeMap().values()) {
            removeEdge(edge);
        }
    }

    public void removeEdge(PDFReaderWorkSpace.PDFSentenceEdge edge) {
        graph.removeEdge(edge);
    }

    public void addVertex(PDFReaderWorkSpace.PDFSentenceNode node) {
        addVertex(node, startX, startY);

    }

    private void positionVertexAt(PDFReaderWorkSpace.PDFSentenceNode vertex, int x, int y) {
        DefaultGraphCell cell = graphAdapter.getVertexCell(vertex);

        setVertexAttribute(cell, vertex.getColor(), true, x, y, getEstimatedLength(vertex.toString()), getEstimatedHeight(vertex.toString()));
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
    public void notifyVertexCreated(String content, int x, int y, int page) {
        for (ISummaryPanelListener lis : listeners) {
            lis.vertexCreated(content, x, y, page);
        }
    }

    @Override
    public void notifyVertexRemove(String id) {
        for (ISummaryPanelListener lis : listeners) {
            lis.vertexRemove(id);
        }
    }

    @Override
    public void notifyEdgeRemove(String id) {
        for (ISummaryPanelListener lis : listeners) {
            lis.edgeRemove(id);
        }
    }
}
