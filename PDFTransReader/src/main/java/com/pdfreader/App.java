package com.pdfreader;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Hello world!
 *
 */
public class App {

    static int page = 0;
    private static final int OFFSET_X = 5;
    private static final int OFFSET_Y = 5;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                final JFrame frame = new JFrame();

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                DisplayDicPanel panel = new DisplayDicPanel(frame);
                frame.add(panel, BorderLayout.CENTER);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
