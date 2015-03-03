package com.trungpham90.pdftransreader;


import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Trung Pham
 */
public class PDFFileReader {

    private File file;

    public PDFFileReader(String location) {

        this(new File(location));
    }

    public PDFFileReader(File file) {
        if (file == null || !file.exists()) {
            throw new InvalidParameterException("File not found!");
        }
        this.file = file;
    }

    public String getContent(int pageFrom, int pageTo) throws IOException {
        PDDocument pd = PDDocument.load(file);
        if (pageFrom < 0 || pageTo > pd.getNumberOfPages()) {
            throw new InvalidParameterException("Invalid page number!");
        }
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);
        PDFTextStripper stripper = new PDFTextStripper();

        stripper.setStartPage(pageFrom);
        stripper.setEndPage(pageTo);
        stripper.writeText(pd, writer);
        
        String result = stringWriter.toString();
        
        if (pd != null) {
            pd.close();
        }
        writer.close();
        return result;
    }

    public static void main(String[] args) throws IOException {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                JFileChooser chooser = new JFileChooser();
                int open = chooser.showOpenDialog(null);
                if (open == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    PDFFileReader reader = new PDFFileReader(file);
                    String v;
                    try {
                        v = reader.getContent(1, 10);
                        System.out.println(v);
                    } catch (IOException ex) {
                        Logger.getLogger(PDFFileReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }

            }
        });
    }
}
