/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pdfreader.viewer.workspace;

import java.awt.Component;
import javax.swing.JLabel;
import org.jgraph.JGraph;
import org.jgraph.graph.CellView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

/**
 *
 * @author Trung Pham
 */
public class MyVertexRenderer extends VertexRenderer {

    @Override
    public Component getRendererComponent(JGraph graph, CellView view, boolean sel, boolean focus, boolean preview) {
        gridColor = graph.getGridColor();
        highlightColor = graph.getHighlightColor();
        lockedHandleColor = graph.getLockedHandleColor();
        isDoubleBuffered = graph.isDoubleBuffered();
        if (view instanceof VertexView) {
            this.view = (VertexView) view;
            setComponentOrientation(graph.getComponentOrientation());
            if (graph.getEditingCell() != view.getCell()) {
                
                Object label = graph.convertValueToString(view);
                if (label != null) {
                    System.out.println(label.toString() + " " + view.getClass());
                    setText(label.toString());
                } else {
                    setText(null);
                }
            } else {
                setText(null);
            }
            this.hasFocus = focus;
            this.childrenSelected = graph.getSelectionModel()
                    .isChildrenSelected(view.getCell());
            this.selected = sel;
            this.preview = preview;
            if (this.view.isLeaf()
                    || GraphConstants.isGroupOpaque(view.getAllAttributes())) {
                installAttributes(view);
            } else {
                resetAttributes();
            }
            return this;
        }
        return null;
    }
}
