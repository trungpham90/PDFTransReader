package com.trungpham90.pdftransreader;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.apache.pdfbox.util.ExtensionFileFilter;

/**
 * Hello world!
 *
 */
public class App {

    static int page = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFileChooser chooser = new JFileChooser();
                ExtensionFileFilter pdfFilter = new ExtensionFileFilter(new String[]{"PDF"}, "PDF Files");
                chooser.setFileFilter(pdfFilter);
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = chooser.getSelectedFile();
                        final PDFFileReader reader = new PDFFileReader(file);
                        final PDFViewerPanel panel = new PDFViewerPanel();

                        final JPanel wrapper = new JPanel(new BorderLayout());
                        wrapper.add(panel, BorderLayout.CENTER);
                        JButton button = new JButton("Next");
                        JPanel tmp = new JPanel(new FlowLayout(FlowLayout.CENTER));
                        tmp.add(button);
                        wrapper.add(tmp, BorderLayout.SOUTH);
                        panel.setPage(reader.getPage(page++));

                        final JFrame frame = new JFrame();
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
                        frame.add(wrapper);
                        button.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                panel.setPage(reader.getPage(page++));
                                wrapper.validate();
                                wrapper.repaint();
                                frame.revalidate();
                                frame.repaint();
                            }
                        });

                        frame.pack();
                        frame.setVisible(true);


                    } catch (IOException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }


            }
        });
    }
}
