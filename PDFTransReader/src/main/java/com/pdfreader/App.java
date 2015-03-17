package com.pdfreader;

import com.pdfreader.dic.DicParser;
import com.pdfreader.dic.DicVO;
import com.pdfreader.viewer.PDFViewerPanel;
import com.pdfreader.reader.PDFFileReader;
import com.pdfreader.reader.PDFWord;
import com.pdfreader.viewer.PDFDicDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private static final int OFFSET_X = 5;
    private static final int OFFSET_Y = 5;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {


                JFileChooser chooser = new JFileChooser();
                ExtensionFileFilter pdfFilter = new ExtensionFileFilter(new String[]{"PDF"}, "PDF Files");
                chooser.setFileFilter(pdfFilter);
                int result = chooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    DicParser.init();
                    try {
                        File file = chooser.getSelectedFile();
                        final JFrame frame = new JFrame();
                        final PDFDicDialog dialog = new PDFDicDialog(frame, false);
                        final PDFFileReader reader = new PDFFileReader(file);
                        final PDFViewerPanel panel = new PDFViewerPanel();
                        panel.addListener(new PDFViewerPanel.ViewerSelectionListener() {
                            public void selectionTrigger(int x1, int y1, int x2, int y2) {
                                try {
                                    List<PDFWord> list = reader.getStringAt(page, x1, y1, x2, y2);
                                    panel.setHighLight(list);
                                    panel.repaint();
                                } catch (IOException ex) {
                                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            public void doubleClickTrigger(int x, int y) {
                                try {
                                    PDFWord word = reader.getWordAt(page, x, y);
                                    if (word != null) {
                                        DicVO content = DicParser.getWordDefinition(word.getWord());
                                        if (content != null) {
                                            dialog.setContent(content);
                                            dialog.setLocationRelativeTo(frame);
                                            dialog.setVisible(true);
                                        }
                                        List<PDFWord> list = new ArrayList();
                                        list.add(word);
                                        panel.setHighLight(list);
                                        panel.repaint();
                                    }

                                } catch (IOException ex) {
                                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });


                        final JPanel wrapper = new JPanel(new BorderLayout());
                        wrapper.add(panel, BorderLayout.CENTER);
                        JButton button = new JButton("Next");
                        JPanel tmp = new JPanel(new FlowLayout(FlowLayout.CENTER));
                        tmp.add(button);
                        wrapper.add(tmp, BorderLayout.SOUTH);
                        panel.setPage(reader.getPage(page++));


                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setLayout(new BorderLayout());


                        frame.add(wrapper, BorderLayout.CENTER);
                        button.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                try {
                                    panel.setPage(reader.getPage(page++));
                                } catch (IOException ex) {
                                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                                }
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
