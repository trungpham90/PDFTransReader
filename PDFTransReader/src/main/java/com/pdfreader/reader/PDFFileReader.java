package com.pdfreader.reader;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.PDAdditionalActions;
import org.apache.pdfbox.pdmodel.interactive.action.PDPageAdditionalActions;
import org.apache.pdfbox.pdmodel.interactive.action.type.PDActionGoTo;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;

/**
 * Process a PDF file
 *
 * @author Trung Pham
 */
public class PDFFileReader {

    private File file;
    private PDDocument doc;
    private PDFPageProcessor processor = null;
    private int zoomRate = -1;//Default size;    

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

    public int getNumPages() {
        return doc.getNumberOfPages();
    }

    public PDPage getPage(int num) throws IOException {
        PDPage page = (PDPage) doc.getDocumentCatalog().getAllPages().get(num);

        processor = new PDFPageProcessor(num, doc);
        return page;
    }

    /**
     * Get list of word at specified position
     *
     * @param page
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @throws IOException
     */
    public List<PDFWord> getStringAt(int page, int x1, int y1, int x2, int y2) throws IOException {
        List<PDFWord> selectedList = processor.getStringAt(x1, y1, x2, y2);
        return selectedList;
    }

    /**
     * Get word a specified position
     *
     * @param page
     * @param x
     * @param y
     * @return
     * @throws IOException
     */
    public PDFWord getWordAt(int page, int x, int y) throws IOException {
        PDFWord word = processor.getWordAt(x, y);
        System.out.println("Word " + word);
        return word;
    }

    /**
     * Get all words in this page, matching a specific word
     *
     * @param word
     * @return
     */
    public List<PDFWord> getMatchingWord(String word) {
        return processor.getMatchingWord(word);
    }
}
