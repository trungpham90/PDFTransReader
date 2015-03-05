package com.trungpham90.pdftransreader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;


/**
 * Process a PDF file
 * @author Trung Pham
 */
public class PDFFileReader {

    private File file;
    private PDDocument doc;
    public PDFFileReader(String location) throws IOException {

        this(new File(location));
    }

    public PDFFileReader(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new InvalidParameterException("File not found!");
        }
        this.file = file;
        doc = PDDocument.load(file);
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
    
    public int getNumPages(){
        return doc.getNumberOfPages();
    }

    public PDPage getPage(int num){
        return (PDPage) doc.getDocumentCatalog().getAllPages().get(num);
    }
}
