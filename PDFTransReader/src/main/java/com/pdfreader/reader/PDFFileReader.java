package com.pdfreader.reader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFTextStripper;

/**
 * Process a PDF file
 *
 * @author Trung Pham
 */
public class PDFFileReader {

    private File file;
    private PDDocument doc;
    private PDFPageProcessor processor = null;

    public PDFFileReader(String location) throws IOException {

        this(new File(location));
    }

    public PDFFileReader(File file) throws IOException {
        if (file == null || !file.exists()) {
            throw new InvalidParameterException("File not found!");
        }
        this.file = file;
        init();
    }

    private void init() throws IOException {
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

    public int getNumPages() {
        return doc.getNumberOfPages();
    }

    public PDPage getPage(int num) throws IOException {
        PDPage page = (PDPage) doc.getDocumentCatalog().getAllPages().get(num);
        processor = new PDFPageProcessor(num, doc);
        System.out.println(num);
        return page;
    }

    /**
     * Get String at specified position
     *
     * @param page
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @throws IOException
     */
    public List<PDFWord> getStringAt(int page, int x1, int y1, int x2, int y2) throws IOException {

        List<PDFWord> list = processor.getStringAt(x1, y1, x2, y2);
        StringBuilder builder = new StringBuilder();
        float lastY = -1;
        for (PDFWord word : list) {
            if (lastY != -1 && lastY != word.getY()) {
                builder.append("\n");
            } else {
                builder.append(" ");
            }
            builder.append(word.getWord());
        }
        System.out.println("STRING " + builder.toString());
        return list;
    }

    public PDFWord getWordAt(int page, int x, int y) throws IOException {
        PDFWord word = processor.getWordAt(x, y);
        if (word != null) {
            System.out.println("WORD " + word);
        }
        return word;
    }
}
